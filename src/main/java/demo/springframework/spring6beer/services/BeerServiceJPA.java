package demo.springframework.spring6beer.services;

import demo.springframework.spring6beer.mappers.BeerMapper;
import demo.springframework.spring6beer.repositories.BeerRepository;
import demo.springframework.spring6beer.requests.BeerRequestDTO;
import demo.springframework.spring6beer.responses.BeerResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class BeerServiceJPA extends BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    public BeerServiceJPA(BeerRepository beerRepository, BeerMapper beerMapper) {
        super(beerMapper);
        this.beerRepository = beerRepository;
        this.beerMapper = beerMapper;
    }

    @Override
    public List<BeerResponseDTO> listBeers() {
        return beerRepository.findAll()
                .stream()
                .map(beerMapper::beerToBeerResponseDTO)
                .collect(Collectors.toList());
    }

    /*
    @Override
    public Optional<BeerResponseDTO> getBeerById(Long id) {
        return beerRepository.findById(id)
                .map(beerMapper::beerToBeerResponseDTO);
    }
    */


    @Override
    public BeerResponseDTO saveNewBeer(BeerRequestDTO beerRequest) {
        return beerMapper.beerToBeerResponseDTO(
                beerRepository.save(beerMapper.beerRequestDtoToBeer(beerRequest))
        );
    }

    /*
    @Override
    public Optional<BeerResponseDTO> updateBeerById(Long beerId, BeerRequestDTO beerRequest) {
        AtomicReference<Optional<BeerResponseDTO>> atomicReference = new AtomicReference<>();

        beerRepository.findById(beerId).ifPresentOrElse(existingBeer -> {
            existingBeer.setBeerName(beerRequest.getBeerName());
            existingBeer.setBeerStyle(beerRequest.getBeerStyle());
            existingBeer.setUpc(beerRequest.getUpc());
            existingBeer.setPrice(beerRequest.getPrice());

            atomicReference.set(Optional.of(
                    beerMapper.beerToBeerResponseDTO(beerRepository.save(existingBeer))
            ));
        }, () -> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }
    */


    @Override
    public Boolean deleteById(Long beerId) {
        if (beerRepository.existsById(beerId)) {
            beerRepository.deleteById(beerId);
            return true;
        }
        return false;
    }

    /*
    @Override
    public void patchBeerById(Long beerId, BeerRequestDTO beerRequest) {
        beerRepository.findById(beerId).ifPresent(existingBeer -> {
            if (beerRequest.getBeerName() != null) {
                existingBeer.setBeerName(beerRequest.getBeerName());
            }
            if (beerRequest.getBeerStyle() != null) {
                existingBeer.setBeerStyle(beerRequest.getBeerStyle());
            }
            if (beerRequest.getUpc() != null) {
                existingBeer.setUpc(beerRequest.getUpc());
            }
            if (beerRequest.getPrice() != null) {
                existingBeer.setPrice(beerRequest.getPrice());
            }

            beerRepository.save(existingBeer);
        });
    }
    */

}
