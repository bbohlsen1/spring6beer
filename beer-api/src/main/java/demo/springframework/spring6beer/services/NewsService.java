package demo.springframework.spring6beer.services;

import demo.springframework.spring6beer.requests.NewsRequestDTO;
import demo.springframework.spring6beer.responses.NewsResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface NewsService {
    Optional<NewsResponseDTO> getNews(Long id);

    List<NewsResponseDTO> listNews();

    NewsResponseDTO createNews(NewsRequestDTO news);

    Optional<NewsResponseDTO> updateNews(Long id, NewsRequestDTO news);

    Optional<NewsResponseDTO> deleteNews(Long id);

    Optional<NewsResponseDTO> patchNews(Long id, NewsRequestDTO news);
}
