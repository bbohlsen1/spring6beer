package demo.springframework.spring6beer.services;

import demo.springframework.spring6beer.controllers.NotFoundException;
import demo.springframework.spring6beer.entities.News;
import demo.springframework.spring6beer.mappers.NewsMapper;
import demo.springframework.spring6beer.repositories.NewsRepository;
import demo.springframework.spring6beer.requests.NewsRequestDTO;
import demo.springframework.spring6beer.responses.NewsResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Primary
public class NewsServiceJPA implements NewsService {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    @Override
    public Optional<NewsResponseDTO> getNews(Long id) {
        return newsRepository.findById(id)
                .map(newsMapper::newsToNewsResponseDTO);
    }

    @Override
    public List<NewsResponseDTO> listNews() {
        return newsRepository.findAll()
                .stream()
                .map(newsMapper::newsToNewsResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public NewsResponseDTO createNews(NewsRequestDTO newsRequestDTO) {
        return newsMapper.newsToNewsResponseDTO(
                newsRepository.save(
                        newsMapper.newsRequestDtoToNews(newsRequestDTO)
                )
        );
    }

    @Override
    public Optional<NewsResponseDTO> updateNews(Long id, NewsRequestDTO news) {
        if (!newsRepository.existsById(id)) {
            throw new NotFoundException("News with ID " + id + " not found");
        }

        return newsRepository.findById(id).map(existing -> {
            existing.setTitle(news.getTitle());
            existing.setContent(news.getContent());
            existing.setAuthor(news.getAuthor());
            existing.setPublishedDate(news.getPublishedDate());
            existing.setImageUrl(news.getImageUrl());
            return newsMapper.newsToNewsResponseDTO(newsRepository.save(existing));
        });
    }

    @Override
    public Optional<NewsResponseDTO> deleteNews(Long id) {
        Optional<News> newsOptional = newsRepository.findById(id);
        if (newsOptional.isPresent()) {
            newsRepository.deleteById(id);
            return newsOptional.map(newsMapper::newsToNewsResponseDTO);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<NewsResponseDTO> patchNews(Long id, NewsRequestDTO news) {
        return newsRepository.findById(id).map(existing -> {
            boolean updated = false;

            if (news.getTitle() != null && !news.getTitle().isBlank()) {
                existing.setTitle(news.getTitle());
                updated = true;
            }
            if (news.getContent() != null && !news.getContent().isBlank()) {
                existing.setContent(news.getContent());
                updated = true;
            }
            if (news.getAuthor() != null && !news.getAuthor().isBlank()) {
                existing.setAuthor(news.getAuthor());
                updated = true;
            }

            if (news.getPublishedDate() != null && !news.getPublishedDate().isBlank()) {
                existing.setPublishedDate(news.getPublishedDate());
                updated = true;
            }
            if (news.getImageUrl() != null && !news.getImageUrl().isBlank()) {
                existing.setImageUrl(news.getImageUrl());
                updated = true;
            }

            if (updated) {
                return newsMapper.newsToNewsResponseDTO(newsRepository.save(existing));
            } else {
                return newsMapper.newsToNewsResponseDTO(existing);
            }
        });
    }
}
