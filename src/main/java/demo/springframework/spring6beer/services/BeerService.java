package demo.springframework.spring6beer.services;

import demo.springframework.spring6beer.models.BeerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    List<BeerDTO> listBeers();

    Optional <BeerDTO> getBeerById(UUID id);

    BeerDTO saveNewBeer(BeerDTO beer);

    Optional<BeerDTO> getBeerById(UUID beerId, BeerDTO beer);

    Boolean deleteById(UUID beerId);

    void patchBeerById(UUID beerId, BeerDTO beer);
}
