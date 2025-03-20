package demo.springframework.spring6beer.services;

import demo.springframework.spring6beer.entities.Beer;
import demo.springframework.spring6beer.mappers.BeerMapper;
import demo.springframework.spring6beer.models.BeerDTO;
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

@Slf4j
@Service
@RequiredArgsConstructor
@Primary
public abstract class BeerService {

    private final BeerMapper beerMapper;

    private Map<Long, BeerDTO> beerMap;

    @Autowired
    BeerRepository beerRepository;

    public List<BeerResponseDTO> listBeers() {
        List<Beer> beers = beerRepository.findAll();

        return beers
                .stream()
                .map(beerMapper::beerToBeerResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<BeerDTO> getBeerById(Long id, BeerResponseDTO beer) {

        log.debug("Get Beer ID - in service. ID: {}", id.toString());

        return Optional.of(beerMap.get(id));
    }

    public BeerDTO saveNewBeer(BeerDTO beer) {

        BeerDTO savedBeer = BeerDTO.builder()
                .id(ThreadLocalRandom.current().nextLong())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .quantityOnHand(beer.getQuantityOnHand())
                .upc(beer.getUpc())
                .price(beer.getPrice())
                .build();

        beerMap.put(savedBeer.getId(), savedBeer);

        return savedBeer;
    }

    public Optional<BeerDTO> getBeerById(Long beerId, BeerDTO beer) {
        BeerDTO existing = beerMap.get(beerId);
        existing.setBeerName(beer.getBeerName());
        existing.setBeerStyle(beer.getBeerStyle());
        existing.setQuantityOnHand(beer.getQuantityOnHand());
        existing.setUpc(beer.getUpc());
        existing.setPrice(beer.getPrice());

        beerMap.put(existing.getId(), existing);

        return Optional.of(existing);
    }

    public abstract BeerResponseDTO saveNewBeer(BeerRequestDTO beerRequest);

    public Boolean deleteById(Long beerId) {
        beerMap.remove(beerId);

        return true;
    }

    public void patchBeerById(Long beerId, BeerDTO beer) {
        BeerDTO existing = beerMap.get(beerId);

        if (StringUtils.hasText(beer.getBeerName())) {
            existing.setBeerName(beer.getBeerName());
        }
        if (beer.getBeerStyle() != null) {
            existing.setBeerStyle(beer.getBeerStyle());
        }
        if (beer.getPrice() > 0) {
            existing.setPrice(beer.getPrice());
        }
        if (beer.getQuantityOnHand() > 0) {
            existing.setQuantityOnHand(beer.getQuantityOnHand());
        }
        if (StringUtils.hasText(beer.getUpc())) {
            existing.setUpc(beer.getUpc());
        }
    }
}
