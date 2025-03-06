package demo.springframework.spring6beer.repositories;

import demo.springframework.spring6beer.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BeerRepository extends JpaRepository<Beer, Long> {
}
