package com.gabriellopesjds.cooperativism.stave.domain.service;

import com.gabriellopesjds.cooperativism.exception.BusinessException;
import com.gabriellopesjds.cooperativism.stave.domain.model.Stave;
import com.gabriellopesjds.cooperativism.stave.repository.StaveRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UpdateStaveService {

    private final StaveRepository staveRepository;
    private final FinderStaveService finderStaveService;

    @Transactional
    public Stave updateStave(UUID id, Stave stave) {
        Stave staveSaved = finderStaveService.findById(id);
        validateThemeDuplicate(stave, staveSaved);
        staveSaved.setTheme(stave.getTheme());
        staveSaved.setDescription(stave.getDescription());
        return staveRepository.save(staveSaved);
    }

    private static void validateThemeDuplicate(Stave stave, Stave staveSaved) {
        staveSaved.getAssembly().getStaveList().stream()
            .filter(staveFind -> stave.getTheme().equals(staveFind.getTheme()) && staveFind.getId() != staveSaved.getId())
            .findFirst()
            .ifPresent(s -> {
                throw new BusinessException("COOPERATIVISM-003", staveSaved.getTheme(), staveSaved.getAssembly().getId().toString());
            });
    }

}
