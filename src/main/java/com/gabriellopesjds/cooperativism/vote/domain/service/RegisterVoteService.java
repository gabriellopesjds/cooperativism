package com.gabriellopesjds.cooperativism.vote.domain.service;

import com.gabriellopesjds.cooperativism.associated.domain.model.Associated;
import com.gabriellopesjds.cooperativism.associated.domain.service.FinderAssociatedService;
import com.gabriellopesjds.cooperativism.exception.BusinessException;
import com.gabriellopesjds.cooperativism.exception.ClientErrorResponseException;
import com.gabriellopesjds.cooperativism.exception.ServerErrorResponseException;
import com.gabriellopesjds.cooperativism.stave.domain.service.FinderStaveService;
import com.gabriellopesjds.cooperativism.vote.domain.model.Vote;
import com.gabriellopesjds.cooperativism.vote.repository.VoteRepository;
import com.gabriellopesjds.cooperativism.vote.response.VoteStatusDTO;
import com.gabriellopesjds.cooperativism.votingsession.domain.model.VotingSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RegisterVoteService {

    private final VoteRepository voteRepository;
    private final FinderStaveService finderStaveService;
    private final FinderAssociatedService finderAssociatedService;
    private final FinderVoteService finderVoteService;
    private final VotingEnabledAssociatedRequestService requestFactory;

    @Transactional
    public Vote registerVote(UUID idStave, Vote vote) {
        VotingSession votingSession = findVotingSessionOpen(idStave);
        Associated associated = finderAssociatedService.findById(vote.getAssociated().getId());
        validateAlreadyVoted(votingSession, associated);
        validateVoteEnableForAssociated(associated);
        vote.setVotingSession(votingSession);
        vote.setAssociated(associated);
        return voteRepository.save(vote);
    }

    private VotingSession findVotingSessionOpen(UUID idStave){
        return finderStaveService.findById(idStave)
            .getVotingSessionList().stream()
            .filter(votingSession -> votingSession.getEndDate().isAfter(LocalDateTime.now()))
            .findFirst()
            .orElseThrow(() -> new BusinessException("COOPERATIVISM-010"));
    }

    private void validateAlreadyVoted(VotingSession votingSession, Associated associated){
        finderVoteService.findByVotingSessionIdAndAssociatedId(votingSession.getId(), associated.getId())
            .ifPresent((vote) -> {
                throw new BusinessException("COOPERATIVISM-011", votingSession.getStave().getTheme());
            });
    }

    private void validateVoteEnableForAssociated(Associated associated){
        try{
            if (!isVoteEnabled(requestFactory.executeGetRequest(associated.getCpf()))){
                throw new BusinessException("COOPERATIVISM-014");
            }
        } catch (ClientErrorResponseException ex){
            if (ex.getHttpStatus() == HttpStatus.NOT_FOUND){
                throw new BusinessException("COOPERATIVISM-013", associated.getCpf());
            }
            throw ex;
        } catch (ServerErrorResponseException ex){
            throw ex;
        }
    }

    private boolean isVoteEnabled(VoteStatusDTO statusDTO){
        return statusDTO.getStatus().equals("ABLE_TO_VOTE");
    }

}
