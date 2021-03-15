package com.gabriellopesjds.cooperativism.vote.application.service;

import com.gabriellopesjds.api.model.VoteRequestDTO;
import com.gabriellopesjds.api.model.VoteResponseDTO;
import com.gabriellopesjds.cooperativism.vote.domain.model.Vote;
import com.gabriellopesjds.cooperativism.vote.domain.service.RegisterVoteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VoteApplicationService {

    private final RegisterVoteService registerVoteService;
    private final VoteFactory voteFactory;

    public VoteResponseDTO registerVote(UUID idStave, VoteRequestDTO voteRequest) {
        Vote vote = registerVoteService.registerVote(idStave, voteFactory.fromValue(voteRequest));
        return voteFactory.buildResponse(vote);
    }

}
