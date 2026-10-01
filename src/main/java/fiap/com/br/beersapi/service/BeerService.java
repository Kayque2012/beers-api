package fiap.com.br.beersapi.service;

import fiap.com.br.beersapi.model.Beer;
import fiap.com.br.beersapi.model.Brewery;
import fiap.com.br.beersapi.repository.BeerRepository;
import fiap.com.br.beersapi.repository.BreweryRepository;
import fiap.com.br.beersapi.specification.BeerSpecification;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BeerService {
    private final BeerRepository beerRepository;
    private final BreweryRepository breweryRepository;

    public BeerService(BeerRepository beerRepository, BreweryRepository breweryRepository) {
        this.beerRepository = beerRepository;
        this.breweryRepository = breweryRepository;
    }

    @Cacheable(value = "beers", key = "#style + '-' + #brewery + '-' + #abvMin + '-' + #abvMax + '-' + #pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<Beer> findAll(String style, String brewery, Double abvMin, Double abvMax, Pageable pageable) {
        Specification<Beer> spec = Specification.where((Specification<Beer>) null);
        if (style != null && !style.isBlank()) spec = spec.and(BeerSpecification.hasStyle(style));
        if (brewery != null && !brewery.isBlank()) spec = spec.and(BeerSpecification.hasBrewery(brewery));
        if (abvMin != null && abvMax != null) spec = spec.and(BeerSpecification.abvBetween(abvMin, abvMax));
        else if (abvMin != null) spec = spec.and(BeerSpecification.abvFrom(abvMin));
        else if (abvMax != null) spec = spec.and(BeerSpecification.abvTo(abvMax));
        return beerRepository.findAll(spec, pageable);
    }

    @Cacheable(value = "beers", key = "'name-' + #name + '-' + #pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<Beer> searchByName(String name, Pageable pageable) { return beerRepository.findAll(BeerSpecification.nameContains(name), pageable); }

    @Cacheable(value = "beer", key = "#id")
    public Beer findById(Long id) { return beerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cerveja não encontrada: " + id)); }

    @Cacheable(value = "beersByBrewery", key = "#breweryId")
    public List<Beer> findByBreweryId(Long breweryId) { return beerRepository.findByBreweryId(breweryId); }

    @CacheEvict(value = {"beers", "beer", "beersByBrewery", "topBeers"}, allEntries = true)
    public Beer create(Beer beer) { beer.setId(null); beer.setBrewery(resolveBrewery(beer)); return beerRepository.save(beer); }

    @CachePut(value = "beer", key = "#id")
    @CacheEvict(value = {"beers", "beersByBrewery", "topBeers"}, allEntries = true)
    public Beer update(Long id, Beer data) {
        Beer beer = findById(id);
        beer.setName(data.getName()); beer.setStyle(data.getStyle()); beer.setAbv(data.getAbv()); beer.setIbu(data.getIbu());
        beer.setBrewery(resolveBrewery(data));
        return beerRepository.save(beer);
    }

    @CacheEvict(value = {"beers", "beer", "beersByBrewery", "topBeers"}, allEntries = true)
    public void delete(Long id) { if (!beerRepository.existsById(id)) throw new IllegalArgumentException("Cerveja não encontrada: " + id); beerRepository.deleteById(id); }

    @Cacheable("topBeers")
    public List<Beer> top5ByAbv() { return beerRepository.findAll(Specification.where((Specification<Beer>) null), PageRequest.of(0, 5, Sort.by("abv").descending())).getContent(); }

    private Brewery resolveBrewery(Beer beer) {
        if (beer.getBrewery() == null || beer.getBrewery().getId() == null) throw new IllegalArgumentException("Informe o id da cervejaria");
        return breweryRepository.findById(beer.getBrewery().getId()).orElseThrow(() -> new IllegalArgumentException("Cervejaria não encontrada: " + beer.getBrewery().getId()));
    }
}
