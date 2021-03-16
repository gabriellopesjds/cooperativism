package com.gabriellopesjds.cooperativism.associated.domain.service;

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
public class DeleteAssociatedService {

    private final AssociatedRepository associatedRepository;
    private final FinderAssociatedService finderAssociatedService;

    @Transactional
    public void delete(UUID id) {
        log.info("Deleting associated with id: {}.", id);
        associatedRepository.delete(finderAssociatedService.findById(id));
    }

    @Transactional
    public void delete(String cpf){
        log.info("Deleting associated with cpf: {}.", cpf);
        associatedRepository.delete(finderAssociatedService.findAssociatedByCpf(cpf));
    }

}
