package com.gabriellopesjds.cooperativism.associated.domain.service;

import com.gabriellopesjds.cooperativism.associated.domain.model.Associated;
import com.gabriellopesjds.cooperativism.associated.repository.AssociatedRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UpdateAssociatedService {

    private final AssociatedRepository associatedRepository;
    private final FinderAssociatedService finderAssociatedService;

    @Transactional
    public Associated updateAssociated(UUID id, Associated associated) {
        log.info("Update associated with cpf: {}", associated.getCpf());
        log.debug("Update associated with body: {}", associated);
        Associated associatedSaved = finderAssociatedService.findById(id);
        associatedSaved.setName(associated.getName());
        return associatedRepository.save(associatedSaved);
    }

}
