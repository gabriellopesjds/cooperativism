package com.gabriellopesjds.cooperativism.assembly.domain.service;

import com.gabriellopesjds.cooperativism.assembly.domain.model.Assembly;
import com.gabriellopesjds.cooperativism.assembly.repository.AssemblyRepository;
import com.gabriellopesjds.cooperativism.exception.BusinessException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.gabriellopesjds.cooperativism.utils.FormatDateUtil.localDateTimeToStringUtc;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UpdateAssemblyService {

    private final AssemblyRepository assemblyRepository;
    private final FinderAssemblyService finderAssemblyService;

    @Transactional
    public Assembly updateAssembly(UUID id, Assembly assembly) {
        log.info("Update assembly with id: {}", assembly.getId());
        log.debug("Update assembly with body: {}", assembly);

        Assembly assemblySaved = finderAssemblyService.findById(id);
        validateAssemblyDuplicate(assembly.getDescription(), assembly.getDate(), assemblySaved.getId());
        assemblySaved.setDescription(assembly.getDescription());
        assemblySaved.setDate(assembly.getDate());
        return assemblyRepository.save(assemblySaved);
    }

    private void validateAssemblyDuplicate(String description, LocalDateTime date, UUID id) {
        finderAssemblyService.findByDescriptionAndDateIsNot(description, date, id)
            .ifPresent(assemblySaved -> {
                log.error("COOPERATIVISM-004=Assembly already registered with description {} and date {}.", description, localDateTimeToStringUtc(date));
                throw new BusinessException("COOPERATIVISM-004", description, localDateTimeToStringUtc(date));
            });
    }
}
