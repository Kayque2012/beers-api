package fiap.com.br.beersapi.specification;

import fiap.com.br.beersapi.model.Beer;
import org.springframework.data.jpa.domain.Specification;

public class BeerSpecification {

    public static Specification<Beer> nameContains(String name) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Beer> hasStyle(String style) {
        return (root, query, cb) -> cb.equal(cb.lower(root.get("style")), style.toLowerCase());
    }

    public static Specification<Beer> hasBrewery(String brewery) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("brewery").get("name")), "%" + brewery.toLowerCase() + "%");
    }

    public static Specification<Beer> abvBetween(Double min, Double max) {
        return (root, query, cb) -> cb.between(root.get("abv"), min, max);
    }

    public static Specification<Beer> abvFrom(Double min) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("abv"), min);
    }

    public static Specification<Beer> abvTo(Double max) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("abv"), max);
    }
}
