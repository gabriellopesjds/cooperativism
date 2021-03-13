package com.gabriellopesjds.cooperativism.stave.domain.service;

import com.gabriellopesjds.cooperativism.assembly.domain.model.Assembly;
import com.gabriellopesjds.cooperativism.assembly.domain.service.GetterAssemblyService;
import com.gabriellopesjds.cooperativism.stave.domain.model.Stave;
import com.gabriellopesjds.cooperativism.stave.repository.StaveRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RegisterStaveService {

    private final StaveRepository staveRepository;
    private final GetterAssemblyService getterAssemblyService;

    @Transactional
    public Stave registerStave(Stave stave) {
        Assembly assembly = getterAssemblyService.findById(stave.getAssembly().getId());
        stave.setAssembly(assembly);
        return staveRepository.save(stave);
    }

}
