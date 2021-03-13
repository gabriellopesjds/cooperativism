package com.gabriellopesjds.cooperativism.stave.application.service;

import com.gabriellopesjds.api.model.AssemblyBaseResponseDTO;
import com.gabriellopesjds.api.model.StaveRequestDTO;
import com.gabriellopesjds.api.model.StaveResponseDTO;
import com.gabriellopesjds.cooperativism.assembly.domain.model.Assembly;
import com.gabriellopesjds.cooperativism.stave.domain.model.Stave;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StaveFactory {

    public Stave fromValue(StaveRequestDTO staveRequestDTO) {
        Assembly assembly = new Assembly();
        assembly.setId(staveRequestDTO.getIdAssembly());

        Stave stave = new Stave();
        stave.setTheme(staveRequestDTO.getTheme());
        stave.setDescription(staveRequestDTO.getDescription());
        stave.setAssembly(assembly);
        return stave;
    }

    public StaveResponseDTO buildResponse(Stave stave) {
        AssemblyBaseResponseDTO assemblyDTO = new AssemblyBaseResponseDTO()
            .id(stave.getAssembly().getId())
            .description(stave.getAssembly().getDescription())
            .date(stave.getAssembly().getDate())
            .creationDate(stave.getAssembly().getCreationDate());

        return new StaveResponseDTO()
            .id(stave.getId())
            .description(stave.getDescription())
            .theme(stave.getTheme())
            .assembly(assemblyDTO);
    }

}
