package demo.springframework.spring6beer.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class NewsResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String author;
    private String publishedDate;
    private String imageUrl;
}
