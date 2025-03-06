package demo.springframework.spring6beer.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;




@Data
@Builder
@AllArgsConstructor
public class BeerDTO {
    private Long id;
    private Integer version;
    private String beerName;
    private BeerStyle beerStyle;
    private String upc;
    private int quantityOnHand;
    private double price;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
