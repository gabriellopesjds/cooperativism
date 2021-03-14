package com.gabriellopesjds.cooperativism.assembly.application.service;

import com.gabriellopesjds.api.model.AssemblyPageableResponseDTO;
import com.gabriellopesjds.api.model.AssemblyRequestDTO;
import com.gabriellopesjds.api.model.AssemblyResponseDTO;
import com.gabriellopesjds.api.model.AssemblyUpdateRequestDTO;
import com.gabriellopesjds.cooperativism.assembly.domain.model.Assembly;
import com.gabriellopesjds.cooperativism.assembly.domain.service.DeleteAssemblyService;
import com.gabriellopesjds.cooperativism.assembly.domain.service.FinderAssemblyService;
import com.gabriellopesjds.cooperativism.assembly.domain.service.RegisterAssemblyService;
import com.gabriellopesjds.cooperativism.assembly.domain.service.UpdateAssemblyService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AssemblyApplicationService {

    private final RegisterAssemblyService registerAssemblyService;
    private final UpdateAssemblyService updateAssemblyService;
    private final FinderAssemblyService finderAssemblyService;
    private final DeleteAssemblyService deleteAssemblyService;
    private final AssemblyFactory assemblyFactory;

    public AssemblyResponseDTO registerAssembly(AssemblyRequestDTO assemblyRequestDTO) {
        Assembly assembly = registerAssemblyService.registerAssembly(assemblyFactory.fromValue(assemblyRequestDTO));
        return assemblyFactory.buildResponse(assembly);
    }

    public AssemblyPageableResponseDTO finderAllAssembly(Integer pageSize,
                                                         Integer pageNumber,
                                                         String sortDirection) {
        Page<Assembly> page = finderAssemblyService.finderAllAssembly(pageSize, pageNumber, sortDirection);
        return assemblyFactory.buildResponse(page);
    }

    public AssemblyResponseDTO findAssembly(UUID id) {
        return assemblyFactory.buildResponse(finderAssemblyService.findById(id));
    }

    public void deleteAssembly(UUID id) {
        deleteAssemblyService.delete(id);
    }

    public AssemblyResponseDTO updateAssembly(UUID id, AssemblyUpdateRequestDTO assemblyUpdateRequestDTO) {
        Assembly assembly = updateAssemblyService.updateAssembly(id, assemblyFactory.fromValue(assemblyUpdateRequestDTO));
        return assemblyFactory.buildResponse(assembly);
    }
}
