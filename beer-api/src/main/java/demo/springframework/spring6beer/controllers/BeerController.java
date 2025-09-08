package demo.springframework.spring6beer.controllers;

import demo.springframework.spring6beer.requests.BeerRequestDTO;
import demo.springframework.spring6beer.responses.BeerResponseDTO;
import demo.springframework.spring6beer.services.BeerServiceJPA;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@AllArgsConstructor
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

    private final BeerServiceJPA beerService;

    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity<BeerResponseDTO> patchById(@PathVariable("beerId") Long beerId,
                                    @RequestBody BeerRequestDTO beer) {
        Optional<BeerResponseDTO> patchedBeer = beerService.patchBeer(beerId, beer);

        if (patchedBeer.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(patchedBeer.get(), HttpStatus.OK);
    }

    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity<BeerResponseDTO> deleteById(@PathVariable("beerId") Long beerId) {
        Optional<BeerResponseDTO> deletedBeer = beerService.deleteBeer(beerId);

        if (deletedBeer.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(deletedBeer.get(), HttpStatus.OK);
    }

    @PutMapping(BEER_PATH_ID)
    public ResponseEntity<BeerResponseDTO> updateById(@PathVariable("beerId") Long beerId,
                                     @RequestBody BeerRequestDTO beer) {
        Optional<BeerResponseDTO> updatedBeer = beerService.updateBeer(beerId, beer);

        if (updatedBeer.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(updatedBeer.get(), HttpStatus.OK);
    }

    @PostMapping(BEER_PATH)
    public ResponseEntity<BeerResponseDTO> handlePost(@RequestBody BeerRequestDTO beer) {
        BeerResponseDTO savedBeer = beerService.createBeer(beer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", BEER_PATH + "/" + savedBeer.getId());

        return new ResponseEntity<>(savedBeer, headers, HttpStatus.CREATED);
    }

    @GetMapping(BEER_PATH)
    public List<BeerResponseDTO> listBeers() {
        return beerService.listBeers();
    }

    @GetMapping(BEER_PATH_ID)
    public BeerResponseDTO getBeerById(@PathVariable("beerId") Long beerId) {
        return beerService.getBeer(beerId).orElseThrow(NotFoundException::new);
    }
}
