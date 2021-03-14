package com.gabriellopesjds.cooperativism.associated.application.service;

import com.gabriellopesjds.api.model.AssociatedPageableResponseDTO;
import com.gabriellopesjds.api.model.AssociatedRequestDTO;
import com.gabriellopesjds.api.model.AssociatedResponseDTO;
import com.gabriellopesjds.api.model.AssociatedUpdateRequestDTO;
import com.gabriellopesjds.cooperativism.associated.domain.model.Associated;
import com.gabriellopesjds.cooperativism.associated.domain.service.DeleteAssociatedService;
import com.gabriellopesjds.cooperativism.associated.domain.service.FinderAssociatedService;
import com.gabriellopesjds.cooperativism.associated.domain.service.RegisterAssociatedService;
import com.gabriellopesjds.cooperativism.associated.domain.service.UpdateAssociatedService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AssociatedApplicationService {

    private final RegisterAssociatedService registerAssociatedService;
    private final UpdateAssociatedService updateAssociatedService;
    private final FinderAssociatedService finderAssociatedService;
    private final DeleteAssociatedService deleteAssociatedService;
    private final AssociatedFactory associatedFactory;

    public AssociatedResponseDTO registerAssociated(AssociatedRequestDTO AssociatedRequestDTO) {
        Associated Associated = registerAssociatedService.registerAssociated(associatedFactory.fromValue(AssociatedRequestDTO));
        return associatedFactory.buildResponse(Associated);
    }

    public AssociatedPageableResponseDTO finderAllAssociated(Integer pageSize,
                                                             Integer pageNumber,
                                                             String sortDirection) {
        Page<Associated> page = finderAssociatedService.finderAllAssociated(pageSize, pageNumber, sortDirection);
        return associatedFactory.buildResponse(page);
    }

    public AssociatedResponseDTO findAssociated(UUID id) {
        return associatedFactory.buildResponse(finderAssociatedService.findById(id));
    }

    public AssociatedResponseDTO findAssociatedByCpf(String cpf) {
        return associatedFactory.buildResponse(finderAssociatedService.findAssociatedByCpf(cpf));
    }

    public void deleteAssociated(UUID id) {
        deleteAssociatedService.delete(id);
    }

    public void deleteAssociatedByCpf(String cpf) {
        deleteAssociatedService.delete(cpf);
    }

    public AssociatedResponseDTO updateAssociated(UUID id, AssociatedUpdateRequestDTO AssociatedUpdateRequestDTO) {
        Associated Associated = updateAssociatedService.updateAssociated(id, associatedFactory.fromValue(AssociatedUpdateRequestDTO));
        return associatedFactory.buildResponse(Associated);
    }
}
