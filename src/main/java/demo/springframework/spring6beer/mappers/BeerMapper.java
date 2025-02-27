package demo.springframework.spring6beer.mappers;

import demo.springframework.spring6beer.entities.Beer;
import demo.springframework.spring6beer.models.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO dto);

    BeerDTO beerToBeerDTO(Beer beer);


}
