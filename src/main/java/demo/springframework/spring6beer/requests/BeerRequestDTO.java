package demo.springframework.spring6beer.requests;

import demo.springframework.spring6beer.models.BeerStyle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BeerRequestDTO {
    private String beerName;

    private BeerStyle beerStyle;

    private Double price;

    private int quantityOnHand;

    private String upc;
}

