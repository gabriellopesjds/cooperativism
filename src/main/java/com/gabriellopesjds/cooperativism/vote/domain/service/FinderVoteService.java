package com.gabriellopesjds.cooperativism.vote.domain.service;

import com.gabriellopesjds.cooperativism.vote.domain.model.Vote;
import com.gabriellopesjds.cooperativism.vote.repository.VoteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class FinderVoteService {

    private final VoteRepository voteRepository;

    public Optional<Vote> findByVotingSessionIdAndAssociatedId(UUID idVotingSession, UUID idAssociated){
        log.info("Finder vote by voting session: {} and associated: {}", idVotingSession, idAssociated);
        return voteRepository.findByVotingSessionIdAndAssociatedId(idVotingSession, idAssociated);
    }
}
