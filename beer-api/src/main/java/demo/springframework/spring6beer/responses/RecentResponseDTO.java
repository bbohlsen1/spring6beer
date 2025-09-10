package demo.springframework.spring6beer.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RecentResponseDTO {
    private Long id;
    private String title;
    private String content;
}
