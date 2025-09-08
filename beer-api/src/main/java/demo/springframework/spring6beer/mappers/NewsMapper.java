package demo.springframework.spring6beer.mappers;

import demo.springframework.spring6beer.entities.News;
import demo.springframework.spring6beer.requests.NewsRequestDTO;
import demo.springframework.spring6beer.responses.NewsResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NewsMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    News newsRequestDtoToNews(NewsRequestDTO requestDTO);

    NewsResponseDTO newsToNewsResponseDTO(News news);
}
