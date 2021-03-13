package com.gabriellopesjds.cooperativism.rest.api;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.gabriellopesjds.api.model.StaveRequestDTO;
import com.gabriellopesjds.api.model.StaveResponseDTO;
import com.gabriellopesjds.api.model.StaveResponseWrapperDTO;
import com.gabriellopesjds.cooperativism.stave.application.service.StaveApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import javax.validation.Valid;

@RestController
@RequestMapping("stave")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StaveController {

    private final StaveApplicationService staveApplicationService;

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
}
