package com.gabriellopesjds.cooperativism.vote.domain.service;

import com.gabriellopesjds.cooperativism.vote.domain.model.Vote;
import com.gabriellopesjds.cooperativism.vote.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FinderVoteService {

    private final VoteRepository voteRepository;

    public Optional<Vote> findByVotingSessionIdAndAssociatedId(UUID idVotingSession, UUID idAssociated){
        return voteRepository.findByVotingSessionIdAndAssociatedId(idVotingSession, idAssociated);
    }
}
