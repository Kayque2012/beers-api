package fiap.com.br.beersapi.controller;

import fiap.com.br.beersapi.model.Brewery;
import fiap.com.br.beersapi.service.BreweryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/breweries")
@Tag(name = "Cervejarias", description = "Operações de cervejarias")
public class BreweryController {
    private final BreweryService service;
    public BreweryController(BreweryService service) { this.service = service; }

    @GetMapping @Operation(summary = "Lista todas as cervejarias")
    public List<Brewery> findAll() { return service.findAll(); }

    @GetMapping("/{id}") @Operation(summary = "Busca uma cervejaria por ID")
    public Brewery findById(@PathVariable Long id) { return service.findById(id); }

    @PostMapping @ResponseStatus(HttpStatus.CREATED) @Operation(summary = "Cadastra uma cervejaria")
    public Brewery create(@RequestBody Brewery brewery) { return service.create(brewery); }

    @PutMapping("/{id}") @Operation(summary = "Atualiza uma cervejaria")
    public Brewery update(@PathVariable Long id, @RequestBody Brewery brewery) { return service.update(id, brewery); }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Operation(summary = "Exclui uma cervejaria")
    public void delete(@PathVariable Long id) { service.delete(id); }
}
