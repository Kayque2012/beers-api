package fiap.com.br.beersapi.controller;

import fiap.com.br.beersapi.model.Beer;
import fiap.com.br.beersapi.service.BeerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/beers")
@Tag(name = "Cervejas", description = "Operações para consulta e manutenção de cervejas")
public class BeerController {

    private final BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GetMapping
    @Operation(summary = "Lista cervejas", description = "Permite combinar filtros de estilo, cervejaria e teor alcoólico.")
    public Page<Beer> findAll(@RequestParam(required = false) String style,
                              @RequestParam(required = false) String brewery,
                              @RequestParam(required = false) Double abvMin,
                              @RequestParam(required = false) Double abvMax,
                              Pageable pageable) {
        return beerService.findAll(style, brewery, abvMin, abvMax, pageable);
    }

    @GetMapping("/search")
    @Operation(summary = "Busca cervejas pelo nome")
    public Page<Beer> search(@RequestParam String name, Pageable pageable) {
        return beerService.searchByName(name, pageable);
    }

    @GetMapping("/brewery/{breweryId}")
    @Operation(summary = "Lista cervejas por cervejaria", description = "Usa a query findByBreweryId e armazena o resultado em cache.")
    public List<Beer> findByBreweryId(@PathVariable Long breweryId) {
        return beerService.findByBreweryId(breweryId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma cerveja por ID", description = "O resultado desta consulta é armazenado em cache.")
    public Beer findById(@PathVariable Long id) {
        return beerService.findById(id);
    }

    @GetMapping("/top5")
    @Operation(summary = "Retorna as 5 cervejas com maior teor alcoólico", description = "O resultado é armazenado em cache.")
    public List<Beer> top5() {
        return beerService.top5ByAbv();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra uma nova cerveja")
    public Beer create(@RequestBody Beer beer) {
        return beerService.create(beer);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma cerveja")
    public Beer update(@PathVariable Long id, @RequestBody Beer beer) {
        return beerService.update(id, beer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Exclui uma cerveja")
    public void delete(@PathVariable Long id) {
        beerService.delete(id);
    }
}
