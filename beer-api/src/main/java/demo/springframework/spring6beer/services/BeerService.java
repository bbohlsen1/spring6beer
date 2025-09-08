package demo.springframework.spring6beer.services;

import demo.springframework.spring6beer.requests.BeerRequestDTO;
import demo.springframework.spring6beer.responses.BeerResponseDTO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public interface BeerService {
    Optional<BeerResponseDTO> getBeer(Long id);

    List<BeerResponseDTO> listBeers();

    BeerResponseDTO createBeer(BeerRequestDTO beer);

    Optional<BeerResponseDTO> updateBeer(Long id, BeerRequestDTO beer);

    Optional<BeerResponseDTO> deleteBeer(Long id);

    Optional<BeerResponseDTO> patchBeer(Long id, BeerRequestDTO beer);
}

