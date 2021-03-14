package com.gabriellopesjds.cooperativism.rest.api;

import com.gabriellopesjds.api.model.AssemblyFinderResponseWrapperDTO;
import com.gabriellopesjds.api.model.AssemblyPageableResponseDTO;
import com.gabriellopesjds.api.model.AssemblyRequestDTO;
import com.gabriellopesjds.api.model.AssemblyResponseDTO;
import com.gabriellopesjds.api.model.AssemblyResponseWrapperDTO;
import com.gabriellopesjds.api.model.AssemblyUpdateRequestDTO;
import com.gabriellopesjds.cooperativism.assembly.application.service.AssemblyApplicationService;
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
@RequestMapping("api/v1/assembly")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Validated
public class AssemblyController {

    private final AssemblyApplicationService assemblyApplicationService;

    @PostMapping
    public ResponseEntity<AssemblyResponseWrapperDTO> registerAssembly(@Valid @RequestBody AssemblyRequestDTO assemblyRequestDTO) {

        AssemblyResponseDTO responseDTO = assemblyApplicationService.registerAssembly(assemblyRequestDTO);

        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(responseDTO.getId())
            .toUri();

        return ResponseEntity.created(uri)
            .body(new AssemblyResponseWrapperDTO().data(responseDTO));
    }

    @PutMapping("{id}")
    public ResponseEntity<AssemblyResponseWrapperDTO> updateAssembly(@PathVariable UUID id,
                                                                     @Valid @RequestBody AssemblyUpdateRequestDTO assemblyUpdateRequestDTO) {

        AssemblyResponseDTO assemblyResponseDTO =
            assemblyApplicationService.updateAssembly(id, assemblyUpdateRequestDTO);

        return ResponseEntity.accepted().body(new AssemblyResponseWrapperDTO().data(assemblyResponseDTO));
    }

    @GetMapping
    public ResponseEntity<AssemblyFinderResponseWrapperDTO> finderAllAssembly(@RequestParam(value = "pageSize", required = false, defaultValue = "20") @Valid @Min(1) Integer pageSize,
                                                                              @RequestParam(value = "pageNumber", required = false, defaultValue = "0") @Min(0) @Valid Integer pageNumber,
                                                                              @RequestParam(value = "sortDirection", required = false, defaultValue = "ASC") @Valid String sortDirection) {

        AssemblyPageableResponseDTO assemblyPageableResponseDTO =
            assemblyApplicationService.finderAllAssembly(pageSize, pageNumber, sortDirection);

        return assemblyPageableResponseDTO.getAssemblys().isEmpty()
            ? ResponseEntity.noContent().build()
            : ResponseEntity.ok(new AssemblyFinderResponseWrapperDTO().data(assemblyPageableResponseDTO));
    }

    @GetMapping("{id}")
    public ResponseEntity<AssemblyResponseWrapperDTO> findAssembly(@PathVariable UUID id) {
        AssemblyResponseDTO assemblyResponseDTO = assemblyApplicationService.findAssembly(id);

        return ResponseEntity.ok(new AssemblyResponseWrapperDTO().data(assemblyResponseDTO));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteAssembly(@PathVariable UUID id) {
        assemblyApplicationService.deleteAssembly(id);
        return ResponseEntity.noContent().build();
    }

}