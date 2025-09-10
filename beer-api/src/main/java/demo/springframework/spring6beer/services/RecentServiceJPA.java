package demo.springframework.spring6beer.services;

import demo.springframework.spring6beer.controllers.NotFoundException;
import demo.springframework.spring6beer.entities.Recent;
import demo.springframework.spring6beer.mappers.RecentMapper;
import demo.springframework.spring6beer.repositories.RecentRepository;
import demo.springframework.spring6beer.requests.RecentRequestDTO;
import demo.springframework.spring6beer.responses.RecentResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Primary
public class RecentServiceJPA implements RecentService {

    private final RecentRepository recentRepository;
    private final RecentMapper recentMapper;

    @Override
    public Optional<RecentResponseDTO> getRecent(Long id) {
        return recentRepository.findById(id)
                .map(recentMapper::recentToRecentResponseDTO);
    }

    @Override
    public List<RecentResponseDTO> listRecents() {
        return recentRepository.findAll()
                .stream()
                .map(recentMapper::recentToRecentResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RecentResponseDTO createRecent(RecentRequestDTO recentRequestDTO) {
        return recentMapper.recentToRecentResponseDTO(
                recentRepository.save(
                        recentMapper.recentRequestDtoToRecent(recentRequestDTO)
                )
        );
    }

    @Override
    public Optional<RecentResponseDTO> updateRecent(Long id, RecentRequestDTO recent) {
        if (!recentRepository.existsById(id)) {
            throw new NotFoundException("News with ID " + id + " not found");
        }

        return recentRepository.findById(id).map(existing -> {
            existing.setTitle(recent.getTitle());
            existing.setContent(recent.getContent());
            return recentMapper.recentToRecentResponseDTO(recentRepository.save(existing));
        });
    }

    @Override
    public Optional<RecentResponseDTO> deleteRecent(Long id) {
        Optional<Recent> recentOptional = recentRepository.findById(id);
        if (recentOptional.isPresent()) {
            recentRepository.deleteById(id);
            return recentOptional.map(recentMapper::recentToRecentResponseDTO);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<RecentResponseDTO> patchRecent(Long id, RecentRequestDTO recent) {
        return recentRepository.findById(id).map(existing -> {
            boolean updated = false;

            if (recent.getTitle() != null && !recent.getTitle().isBlank()) {
                existing.setTitle(recent.getTitle());
                updated = true;
            }
            if (recent.getContent() != null && !recent.getContent().isBlank()) {
                existing.setContent(recent.getContent());
                updated = true;
            }

            if (updated) {
                return recentMapper.recentToRecentResponseDTO(recentRepository.save(existing));
            } else {
                return recentMapper.recentToRecentResponseDTO(existing);
            }
        });
    }

}
