package demo.springframework.spring6beer.repositories;

import demo.springframework.spring6beer.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BeerRepository extends JpaRepository<Beer, Long> {
    List<Beer> findByFeaturedFromIsNotNullAndFeaturedToIsNotNull();
    List<Beer> findByBestSellerTrue();
    List<Beer> findBySponsoredTrue();
}
