package com.gabriellopesjds.cooperativism.stave.application.service;

import com.gabriellopesjds.api.model.AssemblyBaseResponseDTO;
import com.gabriellopesjds.api.model.StaveBaseRequestDTO;
import com.gabriellopesjds.api.model.StaveBaseResponseDTO;
import com.gabriellopesjds.api.model.StavePageableResponseDTO;
import com.gabriellopesjds.api.model.StaveRequestDTO;
import com.gabriellopesjds.api.model.StaveResponseDTO;
import com.gabriellopesjds.api.model.StaveUpdateRequestDTO;
import com.gabriellopesjds.cooperativism.assembly.domain.model.Assembly;
import com.gabriellopesjds.cooperativism.stave.domain.model.Stave;
import com.gabriellopesjds.cooperativism.utils.PageResultFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StaveFactory {

    private final PageResultFactory pageResultFactory;

    public Stave fromValue(StaveRequestDTO staveRequestDTO) {
        Assembly assembly = new Assembly();
        assembly.setId(staveRequestDTO.getIdAssembly());

        Stave stave = new Stave();
        stave.setTheme(staveRequestDTO.getTheme());
        stave.setDescription(staveRequestDTO.getDescription());
        stave.setAssembly(assembly);
        return stave;
    }

    public Stave fromValue(StaveUpdateRequestDTO staveUpdateRequestDTO){
        Stave stave = new Stave();
        stave.setTheme(staveUpdateRequestDTO.getTheme());
        stave.setDescription(staveUpdateRequestDTO.getDescription());
        return stave;
    }

    public Stave fromValue(StaveBaseRequestDTO staveBaseRequestDTO){
        Stave stave = new Stave();
        stave.setTheme(staveBaseRequestDTO.getTheme());
        stave.setDescription(staveBaseRequestDTO.getDescription());
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

    public StavePageableResponseDTO buildResponse(Page<Stave> page) {
        List<StaveResponseDTO> staveResponseDTOList = page.getContent().stream()
            .map(this::buildResponse)
            .collect(Collectors.toList());

        return new StavePageableResponseDTO()
            .staves(staveResponseDTOList)
            .pageResult(pageResultFactory.createPageFrom(page));
    }

    public StaveBaseResponseDTO buildStaveBaseResponse(Stave stave){
        return new StaveBaseResponseDTO()
            .id(stave.getId())
            .description(stave.getDescription())
            .theme(stave.getTheme());
    }

}
