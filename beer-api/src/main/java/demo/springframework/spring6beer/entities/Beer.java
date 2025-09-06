package demo.springframework.spring6beer.entities;

import demo.springframework.spring6beer.models.BeerStyle;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "beer_db", schema = "beer_db")
public class Beer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Version
    @Column(name = "version")
    private Integer version;

    @Column(name = "beer_name")
    private String beerName;

    @Column(name = "beer_style")
    private BeerStyle beerStyle;

    @Column(name = "upc")
    private String upc;

    @Column(name = "quantity_on_hand")
    private int quantityOnHand;

    @Column(name = "price")
    private double price;

    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private LocalDateTime updateDate;
}
