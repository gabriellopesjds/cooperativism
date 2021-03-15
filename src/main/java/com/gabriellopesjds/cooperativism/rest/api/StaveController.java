package com.gabriellopesjds.cooperativism.rest.api;

import com.gabriellopesjds.api.model.StaveFinderResponseWrapperDTO;
import com.gabriellopesjds.api.model.StavePageableResponseDTO;
import com.gabriellopesjds.api.model.StaveRequestDTO;
import com.gabriellopesjds.api.model.StaveResponseDTO;
import com.gabriellopesjds.api.model.StaveResponseWrapperDTO;
import com.gabriellopesjds.api.model.StaveUpdateRequestDTO;
import com.gabriellopesjds.api.model.VoteRequestDTO;
import com.gabriellopesjds.api.model.VoteResponseDTO;
import com.gabriellopesjds.api.model.VoteResponseWrapperDTO;
import com.gabriellopesjds.api.model.VotingSessionRequestDTO;
import com.gabriellopesjds.api.model.VotingSessionResponseDTO;
import com.gabriellopesjds.api.model.VotingSessionResponseWrapperDTO;
import com.gabriellopesjds.api.model.VotingSessionResultResponseDTO;
import com.gabriellopesjds.api.model.VotingSessionResultResponseWrapperDTO;
import com.gabriellopesjds.cooperativism.stave.application.service.StaveApplicationService;
import com.gabriellopesjds.cooperativism.vote.application.service.VoteApplicationService;
import com.gabriellopesjds.cooperativism.votingsession.application.service.VotingSessionApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("api/v1/stave")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Validated
public class StaveController {

    private final StaveApplicationService staveApplicationService;
    private final VotingSessionApplicationService votingSessionApplicationService;
    private final VoteApplicationService voteApplicationService;

    @PostMapping
    public ResponseEntity<StaveResponseWrapperDTO> registerStave(@Valid @RequestBody StaveRequestDTO staveRequestDTO) {

        StaveResponseDTO responseDTO = staveApplicationService.registerStave(staveRequestDTO);

        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(responseDTO.getId())
            .toUri();

        return ResponseEntity.created(uri)
            .body(new StaveResponseWrapperDTO().data(responseDTO));
    }

    @PutMapping("{id}")
    public ResponseEntity<StaveResponseWrapperDTO> updateStave(@PathVariable UUID id,
                                                               @Valid @RequestBody StaveUpdateRequestDTO staveUpdateRequestDTO) {

        StaveResponseDTO staveResponseDTO =
            staveApplicationService.updateStave(id, staveUpdateRequestDTO);

        return ResponseEntity.accepted().body(new StaveResponseWrapperDTO().data(staveResponseDTO));
    }

    @GetMapping
    public ResponseEntity<StaveFinderResponseWrapperDTO> finderAllStave(@RequestParam(value = "pageSize", required = false, defaultValue = "20") @Valid @Min(1) Integer pageSize,
                                                                        @RequestParam(value = "pageNumber", required = false, defaultValue = "0") @Min(0) @Valid Integer pageNumber,
                                                                        @RequestParam(value = "sortDirection", required = false, defaultValue = "ASC") @Valid String sortDirection,
                                                                        @RequestParam(value = "sortBy", required = false, defaultValue = "THEME") @Valid String sortBy) {

        StavePageableResponseDTO stavePageableResponseDTO =
            staveApplicationService.finderAllStave(pageSize, pageNumber, sortDirection, sortBy);

        return stavePageableResponseDTO.getStaves().isEmpty()
            ? ResponseEntity.noContent().build()
            : ResponseEntity.ok(new StaveFinderResponseWrapperDTO().data(stavePageableResponseDTO));
    }

    @GetMapping("{id}")
    public ResponseEntity<StaveResponseWrapperDTO> findStave(@PathVariable UUID id) {
        StaveResponseDTO staveResponseDTO = staveApplicationService.findStave(id);

        return ResponseEntity.ok(new StaveResponseWrapperDTO().data(staveResponseDTO));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteStave(@PathVariable UUID id) {
        staveApplicationService.deleteStave(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("{id}/voting-session")
    public ResponseEntity<VotingSessionResponseWrapperDTO> registerVotingSession(@PathVariable UUID id, @Valid @RequestBody VotingSessionRequestDTO votingSessionRequestDTO) {

        VotingSessionResponseDTO responseDTO = votingSessionApplicationService.registerVotingSession(id,votingSessionRequestDTO);

        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(responseDTO.getId())
            .toUri();

        return ResponseEntity.created(uri)
            .body(new VotingSessionResponseWrapperDTO().data(responseDTO));
    }

    @PostMapping("{id}/voting-session/vote")
    public ResponseEntity<VoteResponseWrapperDTO> registerVote(@PathVariable UUID id, @Valid @RequestBody VoteRequestDTO voteRequest) {

        VoteResponseDTO responseDTO = voteApplicationService.registerVote(id, voteRequest);

        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(responseDTO.getId())
            .toUri();

        return ResponseEntity.created(uri).body(new VoteResponseWrapperDTO().data(responseDTO));
    }

    @GetMapping("{id}/voting-session/result")
    public ResponseEntity<VotingSessionResultResponseWrapperDTO> contabilizeResultStave(@PathVariable UUID id) {
        VotingSessionResultResponseDTO staveResponseDTO = staveApplicationService.contabilizeResultStave(id);

        return ResponseEntity.ok(new VotingSessionResultResponseWrapperDTO().data(staveResponseDTO));
    }

}