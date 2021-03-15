package com.gabriellopesjds.cooperativism.stave.application.service;

import com.gabriellopesjds.api.model.StavePageableResponseDTO;
import com.gabriellopesjds.api.model.StaveRequestDTO;
import com.gabriellopesjds.api.model.StaveResponseDTO;
import com.gabriellopesjds.api.model.StaveUpdateRequestDTO;
import com.gabriellopesjds.api.model.VotingSessionResultResponseDTO;
import com.gabriellopesjds.cooperativism.stave.domain.model.Stave;
import com.gabriellopesjds.cooperativism.stave.domain.service.DeleteStaveService;
import com.gabriellopesjds.cooperativism.stave.domain.service.FinderStaveService;
import com.gabriellopesjds.cooperativism.stave.domain.service.RegisterStaveService;
import com.gabriellopesjds.cooperativism.stave.domain.service.ResultStaveService;
import com.gabriellopesjds.cooperativism.stave.domain.service.UpdateStaveService;
import com.gabriellopesjds.cooperativism.votingsession.application.service.VotingSessionFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StaveApplicationService {

    private final RegisterStaveService registerStaveService;
    private final UpdateStaveService updateStaveService;
    private final FinderStaveService finderStaveService;
    private final DeleteStaveService deleteStaveService;
    private final ResultStaveService resultStaveService;
    private final StaveFactory staveFactory;
    private final VotingSessionFactory votingSessionFactory;

    public StaveResponseDTO registerStave(StaveRequestDTO staveRequestDTO) {
        Stave stave = registerStaveService.registerStave(staveFactory.fromValue(staveRequestDTO));
        return staveFactory.buildResponse(stave);
    }

    public StavePageableResponseDTO finderAllStave(Integer pageSize,
                                                   Integer pageNumber,
                                                   String sortDirection,
                                                   String sortBy) {
        Page<Stave> page = finderStaveService.finderAllStave(pageSize, pageNumber, sortDirection, sortBy);
        return staveFactory.buildResponse(page);
    }

    public StaveResponseDTO findStave(UUID id) {
        return staveFactory.buildResponse(finderStaveService.findById(id));
    }

    public void deleteStave(UUID id) {
        deleteStaveService.delete(id);
    }

    public StaveResponseDTO updateStave(UUID id, StaveUpdateRequestDTO staveUpdateRequestDTO) {
        Stave stave = updateStaveService.updateStave(id, staveFactory.fromValue(staveUpdateRequestDTO));
        return staveFactory.buildResponse(stave);
    }

    public VotingSessionResultResponseDTO contabilizeResultStave(UUID id) {
        return votingSessionFactory.buildResponse(resultStaveService.contabilizeResultStave(id));
    }

}
