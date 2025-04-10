package demo.springframework.spring6beer.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.springframework.spring6beer.models.BeerDTO;
import demo.springframework.spring6beer.requests.BeerRequestDTO;
import demo.springframework.spring6beer.responses.BeerResponseDTO;
import demo.springframework.spring6beer.services.BeerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    BeerService beerService;

    @Test
    void testPatchBeer() throws Exception {
       BeerDTO beer = BeerDTO.builder()
               .id(1L)
               .beerName("Old Name")
               .build();

        Map<String, Object> beerMap = Collections.singletonMap("beerName", "New Name");

        mockMvc.perform(patch(BeerController.BEER_PATH_ID, beer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isNoContent());
    }
    /*
    @Test
    void testDeleteBeer() throws Exception {
        when(beerService.deleteById(any())).thenReturn(true);

        mockMvc.perform(delete(BeerController.BEER_PATH_ID, 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }
    */
    @Test
    void testUpdateBeer() throws Exception {
        BeerResponseDTO beer = BeerResponseDTO.builder()
                .id(1L)
                .beerName("Updated Beer")
                .build();

        given(beerService.getBeerById(any())).willReturn(Optional.of(beer));

        mockMvc.perform(put(BeerController.BEER_PATH_ID, beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isNoContent());

        verify(beerService).getBeerById(any(Long.class));
    }

    @Test
    void testCreateNewBeer() throws Exception {

        BeerResponseDTO beer = BeerResponseDTO.builder()
                .beerName("New Beer")
                .build();

        given(beerService.saveNewBeer(any(BeerRequestDTO.class))).willReturn(BeerResponseDTO.builder()
                .id(2L)
                .beerName("New Beer")
                .build());

        mockMvc.perform(post(BeerController.BEER_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void testListBeers() throws Exception {
        given(beerService.listBeers()).willReturn(Collections.singletonList(BeerResponseDTO.builder()
                        .id(1L)
                        .beerName("Beer 1")
                        .build()
                )

        );

        mockMvc.perform(get(BeerController.BEER_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(1)));
    }

    @Test
    void getBeerByIdNotFound() throws Exception {

        given(beerService.getBeerById(any(Long.class))).willReturn(Optional.empty());

        mockMvc.perform(get(BeerController.BEER_PATH_ID, ThreadLocalRandom.current().nextLong()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getBeerById() throws Exception {

        when(beerService.getBeerById(any())).thenReturn(
                Optional.ofNullable(BeerResponseDTO.builder()
                        .id(1L)
                        .beerName("Test Beer")
                        .build())
        );

        mockMvc.perform(get(BeerController.BEER_PATH + "/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.beerName", is("Test Beer")));
    }
}
