package com.gabriellopesjds.cooperativism.associated.domain.service;

import com.gabriellopesjds.cooperativism.associated.domain.model.Associated;
import com.gabriellopesjds.cooperativism.associated.repository.AssociatedRepository;
import com.gabriellopesjds.cooperativism.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class RegisterAssociatedService {

    private final AssociatedRepository associatedRepository;
    private final FinderAssociatedService finderAssociatedService;

    @Transactional
    public Associated registerAssociated(Associated associated) {
        log.info("Register associated with cpf: {}", associated.getCpf());
        log.debug("Register associated with body: {}", associated);
        validateAssociatedDuplicate(associated);
        return associatedRepository.save(associated);
    }

    private void validateAssociatedDuplicate(Associated associated){
        finderAssociatedService.findByCpf(associated.getCpf())
            .ifPresent((a) -> {
                log.error("COOPERATIVISM-007=CPF of associated duplicate. CPF:{}", associated.getCpf());
                throw new BusinessException("COOPERATIVISM-007", associated.getCpf());
            });
    }
}
