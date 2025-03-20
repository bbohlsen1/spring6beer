package demo.springframework.spring6beer.responses;

import demo.springframework.spring6beer.models.BeerStyle;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BeerResponseDTO {
    private Long id;
    private String beerName;
    private BeerStyle beerStyle;
    private Double price;
    private int quantityOnHand;
    private String upc;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
