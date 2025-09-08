package demo.springframework.spring6beer.services;

import demo.springframework.spring6beer.controllers.NotFoundException;
import demo.springframework.spring6beer.mappers.BeerMapper;
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

        return beerRepository.findById(id).map(existing -> {
            existing.setBeerName(beer.getBeerName());
            existing.setBeerStyle(beer.getBeerStyle());
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
            if (beer.getUpc() != null) {
                existing.setUpc(beer.getUpc());
            }
            return beerMapper.beerToBeerResponseDTO(beerRepository.save(existing));
        });
    }
}

