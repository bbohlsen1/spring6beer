package demo.springframework.spring6beer.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecentRequestDTO {

    private Long id;

    private String title;

    private String content;

}
