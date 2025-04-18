package demo.springframework.spring6beer.mappers;

import demo.springframework.spring6beer.entities.Beer;
import demo.springframework.spring6beer.requests.BeerRequestDTO;
import demo.springframework.spring6beer.responses.BeerResponseDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-15T14:25:03-0500",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 21.0.5 (Amazon.com Inc.)"
)
@Component
public class BeerMapperImpl implements BeerMapper {

    @Override
    public Beer beerRequestDtoToBeer(BeerRequestDTO requestDTO) {
        if ( requestDTO == null ) {
            return null;
        }

        Beer.BeerBuilder beer = Beer.builder();

        beer.beerName( requestDTO.getBeerName() );
        beer.beerStyle( requestDTO.getBeerStyle() );
        beer.upc( requestDTO.getUpc() );
        beer.quantityOnHand( requestDTO.getQuantityOnHand() );
        if ( requestDTO.getPrice() != null ) {
            beer.price( requestDTO.getPrice() );
        }

        return beer.build();
    }

    @Override
    public BeerResponseDTO beerToBeerResponseDTO(Beer beer) {
        if ( beer == null ) {
            return null;
        }

        BeerResponseDTO.BeerResponseDTOBuilder beerResponseDTO = BeerResponseDTO.builder();

        beerResponseDTO.id( beer.getId() );
        beerResponseDTO.beerName( beer.getBeerName() );
        beerResponseDTO.beerStyle( beer.getBeerStyle() );
        beerResponseDTO.price( beer.getPrice() );
        beerResponseDTO.quantityOnHand( beer.getQuantityOnHand() );
        beerResponseDTO.upc( beer.getUpc() );
        beerResponseDTO.createdDate( beer.getCreatedDate() );
        beerResponseDTO.updateDate( beer.getUpdateDate() );

        return beerResponseDTO.build();
    }
}
