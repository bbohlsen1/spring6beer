package demo.springframework.spring6beer.services;

import demo.springframework.spring6beer.controllers.NotFoundException;
import demo.springframework.spring6beer.entities.Beer;
import demo.springframework.spring6beer.mappers.BeerMapper;
import demo.springframework.spring6beer.models.BeerDisplayType;
import demo.springframework.spring6beer.repositories.BeerRepository;
import demo.springframework.spring6beer.requests.BeerRequestDTO;
import demo.springframework.spring6beer.responses.BeerResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Primary
public class BeerServiceJPA implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    public void addFeaturedBeer(Long beerId, Date fromDate, Date toDate) {
        List<Beer> currentFeatured = beerRepository.findByFeaturedFromIsNotNullAndFeaturedToIsNotNull();

        boolean alreadyFeatured = currentFeatured.stream()
                .anyMatch(beer -> beer.getId().equals(beerId));

        if (currentFeatured.size() >= 3 && !alreadyFeatured) {
            throw new RuntimeException("Cannot feature more than 3 beers at a time");
        }

        Beer beer = beerRepository.findById(beerId).orElseThrow();
        beer.setFeaturedFrom(fromDate);
        beer.setFeaturedTo(toDate);
        beerRepository.save(beer);
    }

    public void removeFeatured(Long beerId) {
        Beer beer = beerRepository.findById(beerId).orElseThrow();
        beer.setFeaturedFrom(null);
        beer.setFeaturedTo(null);
        beerRepository.save(beer);
    }

    public void addBestSeller(Long beerId) {
        List<Beer> currentBestSeller = beerRepository.findByBestSellerTrue();

        if (currentBestSeller.size() >= 1) {
            Beer existingBestSeller = currentBestSeller.get(0);
            existingBestSeller.setBestSeller(false);
            beerRepository.save(existingBestSeller);
        }

        System.out.println("Current best sellers: " + currentBestSeller.size());

        Beer beer = beerRepository.findById(beerId).orElseThrow();
        beer.setBestSeller(true);
        beerRepository.save(beer);
    }

    public void removeBestSeller(Long beerId) {
        Beer beer = beerRepository.findById(beerId).orElseThrow();
        beer.setBestSeller(false);
        beerRepository.save(beer);
    }

    public void addSponsored(Long beerId) {
        List<Beer> currentSponsored = beerRepository.findBySponsoredTrue();

        boolean alreadySponsored = currentSponsored.stream()
                .anyMatch(beer -> beer.getId().equals(beerId));

        System.out.println("Current sponsored: " + currentSponsored.size());

        if (currentSponsored.size() >= 3 && !alreadySponsored) {
            throw new RuntimeException("Cannot sponsor more than 3 beers at a time");
        }

        Beer beer = beerRepository.findById(beerId).orElseThrow();
        beer.setSponsored(true);
        beerRepository.save(beer);
    }

    public void removeSponsored(Long beerId) {
        Beer beer = beerRepository.findById(beerId).orElseThrow();
        beer.setSponsored(false);
        beerRepository.save(beer);
    }


    @Override
    public Optional<BeerResponseDTO> getBeer(Long id) {
        return beerRepository.findById(id)
                .map(beerMapper::beerToBeerResponseDTO);
    }

    @Override
    public List<BeerResponseDTO> listBeers() {
        return beerRepository.findAll()
                .stream()
                .map(beerMapper::beerToBeerResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BeerResponseDTO createBeer(BeerRequestDTO beerRequestDTO) {
        Beer newBeer = beerMapper.beerRequestDtoToBeer(beerRequestDTO);
        Beer savedBeer = beerRepository.save(newBeer);

        if (beerRequestDTO.getBestSeller() != null) {
            if (beerRequestDTO.getBestSeller()) {
                addBestSeller(savedBeer.getId());
            }
        }

        if (beerRequestDTO.getSponsored() != null) {
            if (beerRequestDTO.getSponsored()) {
                addSponsored(savedBeer.getId());
            }
        }

        if (beerRequestDTO.getFeaturedFrom() != null && beerRequestDTO.getFeaturedTo() != null) {
            addFeaturedBeer(savedBeer.getId(), beerRequestDTO.getFeaturedFrom(),
                    beerRequestDTO.getFeaturedTo());
        }

        return beerMapper.beerToBeerResponseDTO(savedBeer);
    }


    @Override
    public Optional<BeerResponseDTO> updateBeer(Long id, BeerRequestDTO beer) {
        return beerRepository.findById(id).map(existing -> {
            existing.setBeerName(beer.getBeerName());
            existing.setBeerStyle(beer.getBeerStyle());
            existing.setPrice(beer.getPrice());
            existing.setQuantityOnHand(beer.getQuantityOnHand());
            existing.setUpc(beer.getUpc());

            if (beer.getSponsored() != null) {
                if (beer.getSponsored()) {
                    addSponsored(existing.getId());
                } else {
                    removeSponsored(existing.getId());
                }
            }

            if (beer.getBestSeller() != null) {
                if (beer.getBestSeller()) {
                    addBestSeller(existing.getId());
                } else {
                    removeBestSeller(existing.getId());
                }
            }

            if (beer.getFeaturedFrom() != null && beer.getFeaturedTo() != null) {
                addFeaturedBeer(existing.getId(), beer.getFeaturedFrom(), beer.getFeaturedTo());
            } else if (beer.getFeaturedFrom() == null && beer.getFeaturedTo() == null) {
                removeFeatured(existing.getId());
            }

            Beer savedBeer = beerRepository.findById(id).orElseThrow();
            return beerMapper.beerToBeerResponseDTO(savedBeer);
        }).or(() -> {
            throw new NotFoundException("Beer with ID " + id + " not found");
        });
    }

    @Override
    public Optional<BeerResponseDTO> deleteBeer(Long id) {
        return beerRepository.findById(id)
                .map(beer -> {
                    BeerResponseDTO beerToDelete = beerMapper.beerToBeerResponseDTO(beer);
                    beerRepository.deleteById(id);
                    return beerToDelete;
                });
    }

    @Override
    public Optional<BeerResponseDTO> patchBeer(Long id, BeerRequestDTO beer) {
        return beerRepository.findById(id).map(existing -> {
            if (beer.getBeerName() != null) {
                existing.setBeerName(beer.getBeerName());
            }
            if (beer.getBeerStyle() != null) {
                existing.setBeerStyle(beer.getBeerStyle());
            }
            if (beer.getPrice() != null) {
                existing.setPrice(beer.getPrice());
            }
            if (beer.getQuantityOnHand() != null) {
                existing.setQuantityOnHand(beer.getQuantityOnHand());
            }
            if (beer.getUpc() != null) {
                existing.setUpc(beer.getUpc());
            }
            if (beer.getSponsored() != null) {
                if (beer.getSponsored()) {
                    addSponsored(existing.getId());
                } else {
                    removeSponsored(existing.getId());
                }
            }
            if (beer.getBestSeller() != null) {
                if (beer.getBestSeller()) {
                    addBestSeller(existing.getId());
                } else {
                    removeBestSeller(existing.getId());
                }
            }

            if (beer.getFeaturedFrom() != null && beer.getFeaturedTo() != null) {
                addFeaturedBeer(existing.getId(), beer.getFeaturedFrom(), beer.getFeaturedTo());
            } else if (beer.getFeaturedFrom() == null && beer.getFeaturedTo() == null) {
                removeFeatured(existing.getId());
            }

            Beer savedBeer = beerRepository.findById(id).orElseThrow();
            return beerMapper.beerToBeerResponseDTO(savedBeer);
        });
    }
}

