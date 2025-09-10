package demo.springframework.spring6beer.repositories;

import demo.springframework.spring6beer.entities.Recent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecentRepository extends JpaRepository<Recent, Long> {
}
