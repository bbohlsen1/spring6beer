package demo.springframework.spring6beer.controllers;

import demo.springframework.spring6beer.entities.Beer;

import demo.springframework.spring6beer.mappers.BeerMapper;
import demo.springframework.spring6beer.repositories.BeerRepository;
import demo.springframework.spring6beer.requests.BeerRequestDTO;
import demo.springframework.spring6beer.responses.BeerResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class BeerControllerIT {

    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;

    @Test
    void testDeleteByIdNotFound() {
        Long nonExistentId = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);

        assertThat(beerRepository.findById(nonExistentId)).isEmpty();

        ResponseEntity<?> responseEntity = beerController.deleteById(nonExistentId);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Transactional
    @Rollback
    @Test
    void deleteByIdFound() {
        Beer beer = beerRepository.findAll().get(0);

        ResponseEntity<?> responseEntity = beerController.deleteById(beer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        assertThat(beerRepository.findById(beer.getId()).isEmpty()).isTrue();
    }

    @Test
    void testUpdateNotFound() {
        Long nonExistentId = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);

        assertThat(beerRepository.findById(nonExistentId)).isEmpty();

        assertThrows(NotFoundException.class, () -> {
            beerController.updateById(nonExistentId, BeerRequestDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void patchBeerByIdIntegrationTest() {
        Beer beer = beerRepository.findAll().get(0);
        final String updatedName = "Patched Beer";

        BeerRequestDTO beerRequestDTO = BeerRequestDTO.builder()
                .beerName(updatedName)
                .build();

        ResponseEntity<?> responseEntity = beerController.patchById(beer.getId(), beerRequestDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Beer updatedBeer = beerRepository.findById(beer.getId()).orElseThrow();
        assertThat(updatedBeer.getBeerName()).isEqualTo(updatedName);
    }

    @Rollback
    @Transactional
    @Test
    void updateExistingBeer() {
        Beer beer = beerRepository.findAll().get(0);
        BeerResponseDTO beerResponseDTO = beerMapper.beerToBeerResponseDTO(beer);
        beerResponseDTO.setId(null);
        final String beerName = "UPDATED";
        beerResponseDTO.setBeerName(beerName);

        BeerRequestDTO beerRequestDTO = BeerRequestDTO.builder()
                .beerName(beerResponseDTO.getBeerName())
                .beerStyle(beerResponseDTO.getBeerStyle())
                .upc(beerResponseDTO.getUpc())
                .build();

        ResponseEntity<?> responseEntity = beerController.updateById(beer.getId(), beerRequestDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.valueOf(204));

        Beer updatedBeer = beerRepository.findById(beer.getId()).get();
        assertThat(updatedBeer.getBeerName()).isEqualTo(beerName);

    }

    @Rollback
    @Transactional
    @Test
    void saveNewBeerTest() {
        BeerRequestDTO beerDTO = BeerRequestDTO.builder()
                .beerName("New Beer")
                .build();

        ResponseEntity<?> responseEntity = beerController.handlePost(beerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationParts = responseEntity.getHeaders().getLocation().getPath().split("/");
        Long savedId = Long.parseLong(locationParts[4]);

        Beer beer = beerRepository.findById(savedId).get();
        assertThat(beer).isNotNull();
    }

    @Test
    void testBeerIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            beerController.getBeerById(ThreadLocalRandom.current().nextLong());
        });
    }

    @Test
    void testGetById() {
        Beer beer = beerRepository.findAll().get(0);

        BeerResponseDTO dto = beerController.getBeerById(beer.getId());

        assertThat(dto).isNotNull();
    }

    @Test
    void testListBeers() {
        List<BeerResponseDTO> dtos = beerController.listBeers();

        assertThat(dtos.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        beerRepository.deleteAll();
        List<BeerResponseDTO> dtos = beerController.listBeers();

        assertThat(dtos.size()).isEqualTo(0);
    }
}
