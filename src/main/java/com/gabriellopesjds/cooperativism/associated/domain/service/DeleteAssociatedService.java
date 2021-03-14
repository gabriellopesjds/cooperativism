package com.gabriellopesjds.cooperativism.associated.domain.service;

import com.gabriellopesjds.cooperativism.associated.repository.AssociatedRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DeleteAssociatedService {

    private final AssociatedRepository associatedRepository;
    private final FinderAssociatedService finderAssociatedService;

    @Transactional
    public void delete(UUID id) {
        associatedRepository.delete(finderAssociatedService.findById(id));
    }

    @Transactional
    public void delete(String cpf){
        associatedRepository.delete(finderAssociatedService.findAssociatedByCpf(cpf));
    }

}
