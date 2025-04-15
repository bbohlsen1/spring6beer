package demo.springframework.spring6beer.services;

import demo.springframework.spring6beer.requests.BeerRequestDTO;
import demo.springframework.spring6beer.responses.BeerResponseDTO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public interface BeerService {
    Optional<BeerResponseDTO> getBeerById(Long id);

    List<BeerResponseDTO> listBeers();

    BeerResponseDTO saveNewBeer(BeerRequestDTO beer);

    Optional<BeerResponseDTO> updateBeerById(Long id, BeerRequestDTO beer);

    void deleteById(Long id);

    Optional<BeerResponseDTO> patchBeerById(Long id, BeerRequestDTO beer);
}

