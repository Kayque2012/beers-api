package fiap.com.br.beersapi.repository;

import fiap.com.br.beersapi.model.Beer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface BeerRepository extends JpaRepository<Beer, Long>, JpaSpecificationExecutor<Beer> {
    List<Beer> findByBreweryId(Long breweryId);
}
