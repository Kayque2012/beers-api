# Beers API

API de cervejas desenvolvida com Spring Boot para a missão de documentação, cache e observabilidade.

## Requisitos implementados
- H2 em memória, populado com 3 cervejarias e 6 cervejas.
- Entidades `Brewery` e `Beer`, com `@ManyToOne` de Beer para Brewery.
- Repositórios de ambas as entidades e `findByBreweryId` em BeerRepository.
- Cache com `@Cacheable`, `@CachePut` e `@CacheEvict`.
- Swagger/OpenAPI com `@Tag` e `@Operation`.
- Spring Boot Actuator com health, info, metrics e caches.

## URLs
- Swagger UI: http://localhost:8080/swagger-ui.html
- H2 Console: http://localhost:8080/h2-console
- Actuator: http://localhost:8080/actuator
- Caches: http://localhost:8080/actuator/caches

No H2 Console use JDBC URL `jdbc:h2:mem:testdb`, usuário `sa` e senha vazia.
