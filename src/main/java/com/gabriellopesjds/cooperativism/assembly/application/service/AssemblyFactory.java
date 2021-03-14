package com.gabriellopesjds.cooperativism.assembly.application.service;

import com.gabriellopesjds.api.model.AssemblyPageableResponseDTO;
import com.gabriellopesjds.api.model.AssemblyRequestDTO;
import com.gabriellopesjds.api.model.AssemblyResponseDTO;
import com.gabriellopesjds.api.model.AssemblyUpdateRequestDTO;
import com.gabriellopesjds.api.model.StaveBaseResponseDTO;
import com.gabriellopesjds.cooperativism.assembly.domain.model.Assembly;
import com.gabriellopesjds.cooperativism.stave.application.service.StaveFactory;
import com.gabriellopesjds.cooperativism.stave.domain.model.Stave;
import com.gabriellopesjds.cooperativism.utils.PageResultFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AssemblyFactory {

    private final PageResultFactory pageResultFactory;
    private final StaveFactory staveFactory;

    public Assembly fromValue(AssemblyRequestDTO assemblyRequestDTO) {
        Assembly assembly = new Assembly();
        assembly.setDescription(assemblyRequestDTO.getDescription());
        assembly.setDate(assemblyRequestDTO.getDate());
        assembly.setCreationDate(LocalDateTime.now());

        List<Stave> staveList = assemblyRequestDTO.getStaves().stream()
            .map(stave -> {
                Stave staveValue = staveFactory.fromValue(stave);
                staveValue.setAssembly(assembly);
                return staveValue;
            }).collect(Collectors.toList());

        assembly.setStaveList(staveList);

        return assembly;
    }

    public Assembly fromValue(AssemblyUpdateRequestDTO assemblyUpdateRequestDTO){
        Assembly assembly = new Assembly();
        assembly.setDescription(assemblyUpdateRequestDTO.getDescription());
        assembly.setDate(assemblyUpdateRequestDTO.getDate());
        return assembly;
    }

    public AssemblyResponseDTO buildResponse(Assembly assembly) {

        List<StaveBaseResponseDTO> staveResponseDTOList = assembly.getStaveList().stream()
            .map(staveFactory::buildStaveBaseResponse)
            .collect(Collectors.toList());


        return new AssemblyResponseDTO()
            .id(assembly.getId())
            .description(assembly.getDescription())
            .date(assembly.getDate())
            .creationDate(assembly.getCreationDate())
            .staves(staveResponseDTOList);
    }

    public AssemblyPageableResponseDTO buildResponse(Page<Assembly> page) {
        List<AssemblyResponseDTO> assemblyResponseDTOList = page.getContent().stream()
            .map(this::buildResponse)
            .collect(Collectors.toList());

        return new AssemblyPageableResponseDTO()
            .assemblys(assemblyResponseDTOList)
            .pageResult(pageResultFactory.createPageFrom(page));
    }

}