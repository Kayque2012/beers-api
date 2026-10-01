package fiap.com.br.beersapi.repository;

import fiap.com.br.beersapi.model.Brewery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BreweryRepository extends JpaRepository<Brewery, Long> {
}
