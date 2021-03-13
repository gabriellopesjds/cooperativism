package com.gabriellopesjds.cooperativism.assembly.domain.service;

import com.gabriellopesjds.cooperativism.exception.BusinessException;
import com.gabriellopesjds.cooperativism.assembly.domain.model.Assembly;
import com.gabriellopesjds.cooperativism.assembly.repository.AssemblyRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GetterAssemblyService {

    private final AssemblyRepository assemblyRepository;

    public Assembly findById(UUID id) {
        return assemblyRepository.findById(id)
            .orElseThrow(() -> new BusinessException("COOPERATIVISM-001", id.toString()));
    }

}
