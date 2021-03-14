package com.gabriellopesjds.cooperativism.associated.application.service;

import com.gabriellopesjds.api.model.AssociatedPageableResponseDTO;
import com.gabriellopesjds.api.model.AssociatedRequestDTO;
import com.gabriellopesjds.api.model.AssociatedResponseDTO;
import com.gabriellopesjds.api.model.AssociatedUpdateRequestDTO;
import com.gabriellopesjds.cooperativism.associated.domain.model.Associated;
import com.gabriellopesjds.cooperativism.utils.PageResultFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AssociatedFactory {

    private final PageResultFactory pageResultFactory;

    public Associated fromValue(AssociatedRequestDTO associatedRequestDTO) {
        Associated associated = new Associated();
        associated.setName(associatedRequestDTO.getName());
        associated.setCpf(associatedRequestDTO.getCpf());

        return associated;
    }

    public Associated fromValue(AssociatedUpdateRequestDTO associatedUpdateRequestDTO){
        Associated associated = new Associated();
        associated.setName(associatedUpdateRequestDTO.getName());
        return associated;
    }

    public AssociatedResponseDTO buildResponse(Associated associated) {
        return new AssociatedResponseDTO()
            .id(associated.getId())
            .name(associated.getName())
            .cpf(associated.getCpf());
    }

    public AssociatedPageableResponseDTO buildResponse(Page<Associated> page) {
        List<AssociatedResponseDTO> associatedResponseDTOList = page.getContent().stream()
            .map(this::buildResponse)
            .collect(Collectors.toList());

        return new AssociatedPageableResponseDTO()
            .associateds(associatedResponseDTOList)
            .pageResult(pageResultFactory.createPageFrom(page));
    }

}