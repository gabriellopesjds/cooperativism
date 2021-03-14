package com.gabriellopesjds.cooperativism.rest.api;

import com.gabriellopesjds.api.model.AssociatedFinderResponseWrapperDTO;
import com.gabriellopesjds.api.model.AssociatedPageableResponseDTO;
import com.gabriellopesjds.api.model.AssociatedRequestDTO;
import com.gabriellopesjds.api.model.AssociatedResponseDTO;
import com.gabriellopesjds.api.model.AssociatedResponseWrapperDTO;
import com.gabriellopesjds.api.model.AssociatedUpdateRequestDTO;
import com.gabriellopesjds.cooperativism.associated.application.service.AssociatedApplicationService;
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
@RequestMapping("api/v1/associated")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Validated
public class AssociatedController {

    private final AssociatedApplicationService associatedApplicationService;

    @PostMapping
    public ResponseEntity<AssociatedResponseWrapperDTO> registerAssociated(@Valid @RequestBody AssociatedRequestDTO AssociatedRequestDTO) {

        AssociatedResponseDTO responseDTO = associatedApplicationService.registerAssociated(AssociatedRequestDTO);

        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(responseDTO.getId())
            .toUri();

        return ResponseEntity.created(uri)
            .body(new AssociatedResponseWrapperDTO().data(responseDTO));
    }

    @PutMapping("{id}")
    public ResponseEntity<AssociatedResponseWrapperDTO> updateAssociated(@PathVariable UUID id,
                                                                         @Valid @RequestBody AssociatedUpdateRequestDTO AssociatedUpdateRequestDTO) {

        AssociatedResponseDTO AssociatedResponseDTO =
            associatedApplicationService.updateAssociated(id, AssociatedUpdateRequestDTO);

        return ResponseEntity.accepted().body(new AssociatedResponseWrapperDTO().data(AssociatedResponseDTO));
    }

    @GetMapping
    public ResponseEntity<AssociatedFinderResponseWrapperDTO> finderAllAssociated(@RequestParam(value = "pageSize", required = false, defaultValue = "20") @Valid @Min(1) Integer pageSize,
                                                                                  @RequestParam(value = "pageNumber", required = false, defaultValue = "0") @Min(0) @Valid Integer pageNumber,
                                                                                  @RequestParam(value = "sortDirection", required = false, defaultValue = "ASC") @Valid String sortDirection) {

        AssociatedPageableResponseDTO AssociatedPageableResponseDTO =
            associatedApplicationService.finderAllAssociated(pageSize, pageNumber, sortDirection);

        return AssociatedPageableResponseDTO.getAssociateds().isEmpty()
            ? ResponseEntity.noContent().build()
            : ResponseEntity.ok(new AssociatedFinderResponseWrapperDTO().data(AssociatedPageableResponseDTO));
    }

    @GetMapping("{id}")
    public ResponseEntity<AssociatedResponseWrapperDTO> findAssociated(@PathVariable UUID id) {
        AssociatedResponseDTO AssociatedResponseDTO = associatedApplicationService.findAssociated(id);

        return ResponseEntity.ok(new AssociatedResponseWrapperDTO().data(AssociatedResponseDTO));
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<AssociatedResponseWrapperDTO> findAssociatedByCpf(@PathVariable String cpf) {
        AssociatedResponseDTO AssociatedResponseDTO = associatedApplicationService.findAssociatedByCpf(cpf);

        return ResponseEntity.ok(new AssociatedResponseWrapperDTO().data(AssociatedResponseDTO));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteAssociated(@PathVariable UUID id) {
        associatedApplicationService.deleteAssociated(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("cpf/{cpf}")
    public ResponseEntity<?> deleteAssociatedByCpf(@PathVariable String cpf) {
        associatedApplicationService.deleteAssociatedByCpf(cpf);
        return ResponseEntity.noContent().build();
    }

}