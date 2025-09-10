package demo.springframework.spring6beer.controllers;

import demo.springframework.spring6beer.requests.RecentRequestDTO;
import demo.springframework.spring6beer.responses.RecentResponseDTO;
import demo.springframework.spring6beer.services.RecentServiceJPA;
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
public class RecentController {

    public static final String RECENT_PATH = "/api/v1/recent";
    public static final String RECENT_PATH_ID = RECENT_PATH + "/{recentId}";

    private final RecentServiceJPA recentService;

    @PatchMapping(RECENT_PATH_ID)
    public ResponseEntity<RecentResponseDTO> patchById(@PathVariable("recentId") Long recentId,
                                                      @RequestBody RecentRequestDTO recent) {
        Optional<RecentResponseDTO> patchedRecent = recentService.patchRecent(recentId, recent);

        if (patchedRecent.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(patchedRecent.get(), HttpStatus.OK);
    }

    @DeleteMapping(RECENT_PATH_ID)
    public ResponseEntity<RecentResponseDTO> deleteById(@PathVariable("recentId") Long recentId) {
        Optional<RecentResponseDTO> deletedRecent = recentService.deleteRecent(recentId);

        if (deletedRecent.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(deletedRecent.get(), HttpStatus.OK);
    }

    @PutMapping(RECENT_PATH_ID)
    public ResponseEntity<RecentResponseDTO> updateById(@PathVariable("recentId") Long recentId,
                                                       @RequestBody RecentRequestDTO recent) {
        Optional<RecentResponseDTO> updatedRecent = recentService.updateRecent(recentId, recent);

        if (updatedRecent.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(updatedRecent.get(), HttpStatus.OK);
    }

    @PostMapping(RECENT_PATH)
    public ResponseEntity<RecentResponseDTO> createRecent(@RequestBody RecentRequestDTO recent) {
        RecentResponseDTO createdRecent = recentService.createRecent(recent);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", RECENT_PATH + "/" + createdRecent.getId());

        return new ResponseEntity<>(createdRecent, HttpStatus.CREATED);
    }

    @GetMapping(RECENT_PATH)
    public ResponseEntity<Iterable<RecentResponseDTO>> listRecents() {
        return new ResponseEntity<>(recentService.listRecents(), HttpStatus.OK);
    }

    @GetMapping(RECENT_PATH_ID)
    public ResponseEntity<RecentResponseDTO> getRecentById(@PathVariable("recentId") Long recentId) {
        Optional<RecentResponseDTO> recentOptional = recentService.getRecent(recentId);

        if (recentOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(recentOptional.get(), HttpStatus.OK);
    }
}
