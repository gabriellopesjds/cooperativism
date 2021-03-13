package com.gabriellopesjds.cooperativism.stave.application.service;

import com.gabriellopesjds.api.model.StaveRequestDTO;
import com.gabriellopesjds.api.model.StaveResponseDTO;
import com.gabriellopesjds.cooperativism.stave.domain.model.Stave;
import com.gabriellopesjds.cooperativism.stave.domain.service.RegisterStaveService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StaveApplicationService {

    private final RegisterStaveService registerStaveService;
    private final StaveFactory staveFactory;

    public StaveResponseDTO registerStave(StaveRequestDTO staveRequestDTO) {
        Stave stave = registerStaveService.registerStave(staveFactory.fromValue(staveRequestDTO));
        return staveFactory.buildResponse(stave);
    }

}
