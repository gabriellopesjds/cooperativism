package com.gabriellopesjds.cooperativism.stave.domain.service;

import com.gabriellopesjds.cooperativism.stave.domain.model.Stave;
import com.gabriellopesjds.cooperativism.vote.domain.model.enumerated.VoteTypeEnum;
import com.gabriellopesjds.cooperativism.votingsession.domain.model.VotingSession;
import com.gabriellopesjds.cooperativism.votingsession.domain.model.enumerated.VotingSessionStateEnum;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.gabriellopesjds.cooperativism.votingsession.domain.model.enumerated.VotingSessionStateEnum.COMPLETED;
import static com.gabriellopesjds.cooperativism.votingsession.domain.model.enumerated.VotingSessionStateEnum.OPEN;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ResultStaveService {

    private final FinderStaveService finderStaveService;

    public Stave contabilizeResultStave(UUID idStave){
        Stave stave = finderStaveService.findById(idStave);
        stave.getVotingSessionList()
            .forEach(votingSession -> {
                Long totalPositiveVotes = extractVotes(votingSession, VoteTypeEnum.YES);
                Long totalNegativeVotes = extractVotes(votingSession, VoteTypeEnum.NO);
                Long totalVotes = totalPositiveVotes + totalNegativeVotes;

                VotingSessionStateEnum stateEnum =
                    votingSession.getEndDate().isBefore(LocalDateTime.now()) ? COMPLETED : OPEN;

                votingSession.setTotalVotes(totalVotes);
                votingSession.setTotalPositiveVotes(totalPositiveVotes);
                votingSession.setTotalNegativeVotes(totalNegativeVotes);
                votingSession.setStateEnum(stateEnum);
            });
        return stave;
    }

    private long extractVotes(VotingSession votingSession, VoteTypeEnum typeVote) {
        return votingSession.getVoteList().stream()
            .filter(vote -> vote.getVoteType().equals(typeVote))
            .count();
    }

}
