package demo.springframework.spring6beer.mappers;

import demo.springframework.spring6beer.entities.Recent;
import demo.springframework.spring6beer.requests.RecentRequestDTO;
import demo.springframework.spring6beer.responses.RecentResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    Recent recentRequestDtoToRecent(RecentRequestDTO requestDTO);

    RecentResponseDTO recentToRecentResponseDTO(Recent recent);
}
