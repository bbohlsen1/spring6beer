package demo.springframework.spring6beer.responses;

import demo.springframework.spring6beer.models.BeerDisplayType;
import demo.springframework.spring6beer.models.BeerStyle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class BeerResponseDTO {
    private Long id;
    private String beerName;
    private BeerStyle beerStyle;
    private BeerDisplayType displayType;
    private Integer displayOrder;
    private Double price;
    private int quantityOnHand;
    private String upc;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
