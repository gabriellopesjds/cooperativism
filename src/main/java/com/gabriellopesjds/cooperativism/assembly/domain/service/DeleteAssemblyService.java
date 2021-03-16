package com.gabriellopesjds.cooperativism.assembly.domain.service;

import com.gabriellopesjds.cooperativism.assembly.repository.AssemblyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class DeleteAssemblyService {

    private final AssemblyRepository assemblyRepository;
    private final FinderAssemblyService finderAssemblyService;

    @Transactional
    public void delete(UUID id) {
        log.info("Deleting assembly with id: {}.", id);
        assemblyRepository.delete(finderAssemblyService.findById(id));
    }

}
