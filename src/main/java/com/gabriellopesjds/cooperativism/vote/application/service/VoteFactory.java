package com.gabriellopesjds.cooperativism.vote.application.service;

import com.gabriellopesjds.api.model.VoteRequestDTO;
import com.gabriellopesjds.api.model.VoteResponseDTO;
import com.gabriellopesjds.api.model.VoteTypeDTO;
import com.gabriellopesjds.cooperativism.associated.application.service.AssociatedFactory;
import com.gabriellopesjds.cooperativism.associated.domain.model.Associated;
import com.gabriellopesjds.cooperativism.vote.domain.model.Vote;
import com.gabriellopesjds.cooperativism.vote.domain.model.enumerated.VoteTypeEnum;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VoteFactory {

    private final AssociatedFactory associatedFactory;

    public Vote fromValue(VoteRequestDTO voteRequest) {
        Associated associated = new Associated();
        associated.setId(voteRequest.getIdAssociated());

        Vote vote = new Vote();
        vote.setVoteType(VoteTypeEnum.valueOf(voteRequest.getVoteType().getValue()));
        vote.setAssociated(associated);

        return vote;
    }

    public VoteResponseDTO buildResponse(Vote vote) {
        return new VoteResponseDTO()
            .id(vote.getId())
            .vote(VoteTypeDTO.fromValue(vote.getVoteType().name()))
            .associated(associatedFactory.buildResponse(vote.getAssociated()));
    }

}
