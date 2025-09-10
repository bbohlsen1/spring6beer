package demo.springframework.spring6beer.services;

import demo.springframework.spring6beer.requests.RecentRequestDTO;
import demo.springframework.spring6beer.responses.RecentResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RecentService {
    Optional<RecentResponseDTO> getRecent(Long id);

    List<RecentResponseDTO> listRecents();

    RecentResponseDTO createRecent(RecentRequestDTO recent);

    Optional<RecentResponseDTO> updateRecent(Long id, RecentRequestDTO recent);

    Optional<RecentResponseDTO> deleteRecent(Long id);

    Optional<RecentResponseDTO> patchRecent(Long id, RecentRequestDTO recent);
}
