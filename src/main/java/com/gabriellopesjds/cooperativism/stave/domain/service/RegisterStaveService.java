package com.gabriellopesjds.cooperativism.stave.domain.service;

import com.gabriellopesjds.cooperativism.assembly.domain.model.Assembly;
import com.gabriellopesjds.cooperativism.assembly.domain.service.FinderAssemblyService;
import com.gabriellopesjds.cooperativism.exception.BusinessException;
import com.gabriellopesjds.cooperativism.stave.domain.model.Stave;
import com.gabriellopesjds.cooperativism.stave.repository.StaveRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class RegisterStaveService {

    private final StaveRepository staveRepository;
    private final FinderAssemblyService finderAssemblyService;

    @Transactional
    public Stave registerStave(Stave stave) {
        log.info("Register stave with theme: {}", stave.getTheme());
        log.debug("Register stave with body: {}", stave);
        Assembly assembly = finderAssemblyService.findById(stave.getAssembly().getId());

        validateThemeDuplicate(stave, assembly);

        stave.setAssembly(assembly);
        return staveRepository.save(stave);
    }

    private static void validateThemeDuplicate(Stave stave, Assembly assembly) {
        log.info("validate Theme Duplicate. Theme: {}", stave.getTheme());
        assembly.getStaveList().stream()
            .filter( staveSaved -> staveSaved.getTheme().equals(stave.getTheme()))
            .findFirst()
            .ifPresent(staveSaved -> {
                log.error("COOPERATIVISM-003=Theme {} already registered for this stave in the assembly. Assembly ID: {}.", staveSaved.getTheme(), staveSaved.getAssembly().getId());
                throw new BusinessException("COOPERATIVISM-003", staveSaved.getTheme(), staveSaved.getAssembly().getId().toString());
            });
    }
    public void validateThemeDuplicate(List<Stave> staveList){
        log.info("Validate Theme Duplicate.");
        staveList.stream()
            .filter(stave -> Collections.frequency(staveList, stave) > 1)
            .findFirst()
            .ifPresent(stave -> {
                throw new BusinessException("COOPERATIVISM-005", stave.getTheme());
            });
    }

}
