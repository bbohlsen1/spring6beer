package demo.springframework.spring6beer.services;

import demo.springframework.spring6beer.controllers.NotFoundException;
import demo.springframework.spring6beer.mappers.BeerMapper;
import demo.springframework.spring6beer.models.BeerDisplayType;
import demo.springframework.spring6beer.repositories.BeerRepository;
import demo.springframework.spring6beer.requests.BeerRequestDTO;
import demo.springframework.spring6beer.responses.BeerResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Primary
public class BeerServiceJPA implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    private void validateDisplayTypeAndOrder(BeerDisplayType displayType, Integer displayOrder) {
        if (displayType == null || displayOrder == null) {
            return;
        }

        long featuredCount = beerRepository.countByDisplayType(BeerDisplayType.FEATURED);
        long bestSellingCount = beerRepository.countByDisplayType(BeerDisplayType.BEST_SELLING);
        long sponsoredCount = beerRepository.countByDisplayType(BeerDisplayType.SPONSORED);

        switch (displayType) {
            case FEATURED:
                if (featuredCount >= 3) {
                    throw new IllegalArgumentException("Cannot have more than 3 FEATURED beers.");
                }
                if (displayOrder < 1 || displayOrder > 3) {
                    throw new IllegalArgumentException("FEATURED display order must be between 1 and 3.");
                }
                if (beerRepository.existsByDisplayTypeAndDisplayOrder(BeerDisplayType.FEATURED, displayOrder)) {
                    throw new IllegalArgumentException("FEATURED position " + displayOrder + " is already taken.");
                }
                break;
            case BEST_SELLING:
                if (bestSellingCount >= 1) {
                    throw new IllegalArgumentException("Cannot have more than 1 BEST_SELLING beer.");
                }
                if (displayOrder != 1) {
                    throw new IllegalArgumentException("BEST_SELLING display order must be 1.");
                }
                break;
            case SPONSORED:
                if (sponsoredCount >= 3) {
                    throw new IllegalArgumentException("Cannot have more than 3 SPONSORED beers.");
                }
                if (displayOrder < 1 || displayOrder > 3) {
                    throw new IllegalArgumentException("SPONSORED display order must be between 1 and 3.");
                }
                if (beerRepository.existsByDisplayTypeAndDisplayOrder(BeerDisplayType.SPONSORED, displayOrder)) {
                    throw new IllegalArgumentException("SPONSORED position " + displayOrder + " is already taken.");
                }
                break;
            default:
                break;
        }

        if (displayType == BeerDisplayType.REGULAR) {
            long remainingFeatured = beerRepository.countByDisplayType(BeerDisplayType.FEATURED);
            long remainingSponsored = beerRepository.countByDisplayType(BeerDisplayType.SPONSORED);

            if (remainingFeatured < 1) {
                throw new IllegalArgumentException("Must maintain at least 1 FEATURED beer.");
            }
            if (remainingSponsored < 1) {
                throw new IllegalArgumentException("Must maintain at least 1 SPONSORED beer.");
            }
        }
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
        validateDisplayTypeAndOrder(beerRequestDTO.getDisplayType(), beerRequestDTO.getDisplayOrder());
        return beerMapper.beerToBeerResponseDTO(
                beerRepository.save(
                        beerMapper.beerRequestDtoToBeer(beerRequestDTO)
                )
        );
    }

    @Override
    public Optional<BeerResponseDTO> updateBeer(Long id, BeerRequestDTO beer) {
        if (!beerRepository.existsById(id)) {
            throw new NotFoundException("Beer with ID " + id + " not found");
        }

        validateDisplayTypeAndOrder(beer.getDisplayType(), beer.getDisplayOrder());

        return beerRepository.findById(id).map(existing -> {
            existing.setBeerName(beer.getBeerName());
            existing.setBeerStyle(beer.getBeerStyle());
            existing.setDisplayType(beer.getDisplayType());
            existing.setDisplayOrder(beer.getDisplayOrder());
            existing.setPrice(beer.getPrice());
            existing.setQuantityOnHand(beer.getQuantityOnHand());
            existing.setUpc(beer.getUpc());
            return beerMapper.beerToBeerResponseDTO(beerRepository.save(existing));
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
            if (beer.getDisplayType() != null || beer.getDisplayOrder() != null) {
                validateDisplayTypeAndOrder(beer.getDisplayType(), beer.getDisplayOrder());
                if (beer.getDisplayType() != null) {
                    existing.setDisplayType(beer.getDisplayType());
                }
                if (beer.getDisplayOrder() != null) {
                    existing.setDisplayOrder(beer.getDisplayOrder());
                }
            }
            if (beer.getPrice() != null) {
                existing.setPrice(beer.getPrice());
            }
            if (beer.getUpc() != null) {
                existing.setUpc(beer.getUpc());
            }
            return beerMapper.beerToBeerResponseDTO(beerRepository.save(existing));
        });
    }
}

