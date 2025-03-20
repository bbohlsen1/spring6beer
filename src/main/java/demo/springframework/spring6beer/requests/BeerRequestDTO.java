package demo.springframework.spring6beer.requests;

import demo.springframework.spring6beer.models.BeerStyle;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class BeerRequestDTO {
    private String beerName;

    private BeerStyle beerStyle;

    private Double price;

    private int quantityOnHand;

    private String upc;
}

