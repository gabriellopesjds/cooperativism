package com.gabriellopesjds.cooperativism.votingsession.application.service;

import com.gabriellopesjds.api.model.VotingSessionRequestDTO;
import com.gabriellopesjds.api.model.VotingSessionResponseDTO;
import com.gabriellopesjds.cooperativism.votingsession.domain.model.VotingSession;
import com.gabriellopesjds.cooperativism.votingsession.domain.service.FinderVotingSessionService;
import com.gabriellopesjds.cooperativism.votingsession.domain.service.RegisterVotingSessionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VotingSessionApplicationService {

    private final RegisterVotingSessionService registerVotingSessionService;
    private final FinderVotingSessionService finderVotingSessionService;
    private final VotingSessionFactory votingSessionFactory;

    public VotingSessionResponseDTO registerVotingSession(UUID id, VotingSessionRequestDTO votingSessionRequestDTO) {
        VotingSession votingSession = registerVotingSessionService.registerVotingSession(votingSessionFactory.fromValue(id, votingSessionRequestDTO));
        return votingSessionFactory.buildResponse(votingSession);
    }

}
