package demo.springframework.spring6beer.repositories;

import demo.springframework.spring6beer.entities.Beer;
import demo.springframework.spring6beer.models.BeerDisplayType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeerRepository extends JpaRepository<Beer, Long> {
    long countByDisplayType(BeerDisplayType displayType);
    boolean existsByDisplayTypeAndDisplayOrder(BeerDisplayType displayType, Integer displayOrder);
}
