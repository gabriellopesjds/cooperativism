package com.gabriellopesjds.cooperativism.assembly.domain.service;

import com.gabriellopesjds.cooperativism.assembly.domain.model.Assembly;
import com.gabriellopesjds.cooperativism.assembly.repository.AssemblyRepository;
import com.gabriellopesjds.cooperativism.exception.BusinessException;
import com.gabriellopesjds.cooperativism.stave.domain.service.RegisterStaveService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.gabriellopesjds.cooperativism.utils.FormatDateUtil.localDateTimeToStringUtc;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RegisterAssemblyService {

    private final AssemblyRepository assemblyRepository;
    private final FinderAssemblyService finderAssemblyService;
    private final RegisterStaveService registerStaveService;

    @Transactional
    public Assembly registerAssembly(Assembly assembly) {
        validateAssemblyDuplicate(assembly);
        registerStaveService.validateThemeDuplicate(assembly.getStaveList());
        return assemblyRepository.save(assembly);
    }

    private void validateAssemblyDuplicate(Assembly assembly) {
        finderAssemblyService.findByDescriptionAndDate(assembly)
            .ifPresent(assemblySaved -> {
                throw new BusinessException("COOPERATIVISM-004", assembly.getDescription(), localDateTimeToStringUtc(assembly.getDate()));
            });
    }

}
