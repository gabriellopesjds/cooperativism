package com.gabriellopesjds.cooperativism.votingsession.domain.service;

import com.gabriellopesjds.cooperativism.stave.domain.service.FinderStaveService;
import com.gabriellopesjds.cooperativism.votingsession.domain.model.VotingSession;
import com.gabriellopesjds.cooperativism.votingsession.repository.VotingSessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FinderVotingSessionService {

    private final VotingSessionRepository votingSessionRepository;
    private final FinderStaveService finderStaveService;


    public Optional<VotingSession> findByEndDateAfterAndStaveId(LocalDateTime time, UUID staveId){
        return votingSessionRepository.findByEndDateAfterAndStaveId(time, staveId);
    }

}
