package com.gabriellopesjds.cooperativism.votingsession.domain.service;

import com.gabriellopesjds.cooperativism.exception.BusinessException;
import com.gabriellopesjds.cooperativism.stave.domain.model.Stave;
import com.gabriellopesjds.cooperativism.stave.domain.service.FinderStaveService;
import com.gabriellopesjds.cooperativism.votingsession.domain.model.VotingSession;
import com.gabriellopesjds.cooperativism.votingsession.repository.VotingSessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RegisterVotingSessionService {

    private final VotingSessionRepository votingSessionRepository;
    private final FinderVotingSessionService finderVotingSessionService;
    private final FinderStaveService finderStaveService;

    @Transactional
    public VotingSession registerVotingSession(VotingSession votingSession) {
        Stave stave = finderStaveService.findById(votingSession.getStave().getId());
        validateOpenVotingSession(stave.getId());
        votingSession.setStave(stave);
        return votingSessionRepository.save(votingSession);
    }

    private void validateOpenVotingSession(UUID staveId) {
        finderVotingSessionService.findByEndDateAfterAndStaveId(LocalDateTime.now(), staveId)
            .ifPresent(votingSession -> {
                throw new BusinessException("COOPERATIVISM-009");
            });
    }

}
