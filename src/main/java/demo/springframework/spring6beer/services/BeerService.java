package demo.springframework.spring6beer.services;

import demo.springframework.spring6beer.entities.Beer;
import demo.springframework.spring6beer.mappers.BeerMapper;
import demo.springframework.spring6beer.repositories.BeerRepository;
import demo.springframework.spring6beer.requests.BeerRequestDTO;
import demo.springframework.spring6beer.responses.BeerResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@Primary
public interface BeerService {
    Optional<BeerResponseDTO> getBeerById(Long id);

    List<BeerResponseDTO> listBeers();

    BeerResponseDTO saveNewBeer(BeerRequestDTO beer);

    Optional<BeerResponseDTO> updateBeerById(Long id, BeerRequestDTO beer);

    void deleteById(Long id);

    Optional<BeerResponseDTO> patchBeerById(Long id, BeerRequestDTO beer);
}

