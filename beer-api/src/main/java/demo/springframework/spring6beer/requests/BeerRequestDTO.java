package demo.springframework.spring6beer.requests;

import demo.springframework.spring6beer.models.BeerStyle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BeerRequestDTO {
    private Long id;

    private String beerName;

    private BeerStyle beerStyle;

    private Boolean sponsored;

    private Boolean bestSeller;

    private Date featuredFrom;

    private Date featuredTo;

    private Double price;

    private String description;

    private String imageUrl;

    private Integer quantityOnHand;

    private String upc;
}

