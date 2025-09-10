package demo.springframework.spring6beer.controllers;

import demo.springframework.spring6beer.requests.NewsRequestDTO;
import demo.springframework.spring6beer.responses.NewsResponseDTO;
import demo.springframework.spring6beer.services.NewsServiceJPA;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@AllArgsConstructor
public class NewsController {

    public static final String NEWS_PATH = "/api/v1/news";
    public static final String NEWS_PATH_ID = NEWS_PATH + "/{newsId}";

    private final NewsServiceJPA newsService;

    @PatchMapping(NEWS_PATH_ID)
    public ResponseEntity<NewsResponseDTO> patchById(@PathVariable("newsId") Long newsId,
                                                     @RequestBody NewsRequestDTO news) {
        Optional<NewsResponseDTO> patchedNews = newsService.patchNews(newsId, news);

        if (patchedNews.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(patchedNews.get(), HttpStatus.OK);

    }

    @DeleteMapping(NEWS_PATH_ID)
    public ResponseEntity<NewsResponseDTO> deleteById(@PathVariable("newsId") Long newsId) {
        Optional<NewsResponseDTO> deletedNews = newsService.deleteNews(newsId);

        if (deletedNews.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(deletedNews.get(), HttpStatus.OK);
    }

    @PutMapping(NEWS_PATH_ID)
    public ResponseEntity<NewsResponseDTO> updateById(@PathVariable("newsId") Long newsId,
                                                      @RequestBody NewsRequestDTO news) {
        Optional<NewsResponseDTO> updatedNews = newsService.updateNews(newsId, news);

        if (updatedNews.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(updatedNews.get(), HttpStatus.OK);
    }

    @PostMapping(NEWS_PATH)
    public ResponseEntity<NewsResponseDTO> createNews(@RequestBody NewsRequestDTO news) {
        NewsResponseDTO createdNews = newsService.createNews(news);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", NEWS_PATH + "/" + createdNews.getId());

        return new ResponseEntity<>(createdNews, HttpStatus.CREATED);
    }

    @GetMapping(NEWS_PATH)
    public ResponseEntity<Iterable<NewsResponseDTO>> listNews() {
        return new ResponseEntity<>(newsService.listNews(), HttpStatus.OK);
    }

    @GetMapping(NEWS_PATH_ID)
    public ResponseEntity<NewsResponseDTO> getNewsById(@PathVariable("newsId") Long newsId) {
        Optional<NewsResponseDTO> news = newsService.getNews(newsId);
        if (news.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(news.get(), HttpStatus.OK);
    }
}




