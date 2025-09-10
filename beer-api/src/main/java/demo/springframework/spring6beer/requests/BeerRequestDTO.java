package demo.springframework.spring6beer.requests;

import demo.springframework.spring6beer.models.BeerDisplayType;
import demo.springframework.spring6beer.models.BeerStyle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BeerRequestDTO {
    private Long id;

    private String beerName;

    private BeerStyle beerStyle;

    private BeerDisplayType displayType;

    private Integer displayOrder;

    private Double price;

    private int quantityOnHand;

    private String upc;
}

