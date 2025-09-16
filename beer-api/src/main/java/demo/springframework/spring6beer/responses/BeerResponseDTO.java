package demo.springframework.spring6beer.responses;

import demo.springframework.spring6beer.models.BeerDisplayType;
import demo.springframework.spring6beer.models.BeerStyle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class BeerResponseDTO {
    private Long id;
    private String beerName;
    private BeerStyle beerStyle;
    private Boolean sponsored;
    private Boolean bestSeller;
    private Date featuredFrom;
    private Date featuredTo;
    private Double price;
    private Integer quantityOnHand;
    private String upc;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
