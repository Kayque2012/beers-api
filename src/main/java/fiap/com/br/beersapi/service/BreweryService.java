package fiap.com.br.beersapi.service;

import fiap.com.br.beersapi.model.Brewery;
import fiap.com.br.beersapi.repository.BreweryRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BreweryService {
    private final BreweryRepository repository;

    public BreweryService(BreweryRepository repository) { this.repository = repository; }

    @Cacheable("breweries")
    public List<Brewery> findAll() { return repository.findAll(); }

    @Cacheable(value = "brewery", key = "#id")
    public Brewery findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cervejaria não encontrada: " + id));
    }

    @CacheEvict(value = {"breweries", "brewery"}, allEntries = true)
    public Brewery create(Brewery brewery) { brewery.setId(null); return repository.save(brewery); }

    @CachePut(value = "brewery", key = "#id")
    @CacheEvict(value = "breweries", allEntries = true)
    public Brewery update(Long id, Brewery data) {
        Brewery brewery = findById(id);
        brewery.setName(data.getName());
        brewery.setCountry(data.getCountry());
        return repository.save(brewery);
    }

    @CacheEvict(value = {"breweries", "brewery", "beers", "beer", "beersByBrewery", "topBeers"}, allEntries = true)
    public void delete(Long id) { repository.deleteById(id); }
}
