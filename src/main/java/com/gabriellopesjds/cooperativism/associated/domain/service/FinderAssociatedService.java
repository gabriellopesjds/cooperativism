package com.gabriellopesjds.cooperativism.associated.domain.service;

import com.gabriellopesjds.cooperativism.associated.domain.model.Associated;
import com.gabriellopesjds.cooperativism.associated.repository.AssociatedRepository;
import com.gabriellopesjds.cooperativism.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.fromOptionalString;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FinderAssociatedService {

    private final AssociatedRepository associatedRepository;

    public Page<Associated> finderAllAssociated(Integer pageSize, Integer pageNumber, String sortDirection) {
        Sort.Direction direction = fromOptionalString(sortDirection).orElse(Sort.Direction.ASC);
        Sort sort = Sort.by(direction, "name");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return associatedRepository.findAll(pageable);
    }

    public Associated findById(UUID id) {
        return associatedRepository.findById(id)
            .orElseThrow(() -> new BusinessException("COOPERATIVISM-006", id.toString()));
    }

    public Optional<Associated> findByCpf(String cpf){
        return associatedRepository.findByCpf(cpf);
    }

    public Associated findAssociatedByCpf(String cpf){
        return findByCpf(cpf)
            .orElseThrow(() -> new BusinessException("COOPERATIVISM-008", cpf));
    }

}
