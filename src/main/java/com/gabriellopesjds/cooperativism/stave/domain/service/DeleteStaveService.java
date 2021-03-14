package com.gabriellopesjds.cooperativism.stave.domain.service;

import com.gabriellopesjds.cooperativism.stave.repository.StaveRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DeleteStaveService {

    private final StaveRepository staveRepository;
    private final FinderStaveService finderStaveService;

    @Transactional
    public void delete(UUID id) {
        staveRepository.delete(finderStaveService.findById(id));
    }

}
