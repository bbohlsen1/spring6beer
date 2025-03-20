package demo.springframework.spring6beer.mappers;

import demo.springframework.spring6beer.entities.Beer;
import demo.springframework.spring6beer.requests.BeerRequestDTO;
import demo.springframework.spring6beer.responses.BeerResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BeerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    Beer beerRequestDtoToBeer(BeerRequestDTO requestDTO);


    BeerResponseDTO beerToBeerResponseDTO(Beer beer);
}
