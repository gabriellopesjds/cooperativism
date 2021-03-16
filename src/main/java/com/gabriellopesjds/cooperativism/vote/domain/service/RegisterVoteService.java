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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class RegisterVoteService {

    private final VoteRepository voteRepository;
    private final FinderStaveService finderStaveService;
    private final FinderAssociatedService finderAssociatedService;
    private final FinderVoteService finderVoteService;
    private final VotingEnabledAssociatedRequestService requestFactory;

    @Transactional
    public Vote registerVote(UUID idStave, Vote vote) {
        log.info("Register vote in stave id: {} for associate: {}", idStave, vote.getAssociated().getId());
        log.debug("Register assembly with id: {} and body: {}", idStave, vote);
        VotingSession votingSession = findVotingSessionOpen(idStave);
        Associated associated = finderAssociatedService.findById(vote.getAssociated().getId());
        validateAlreadyVoted(votingSession, associated);
        validateVoteEnableForAssociated(associated);
        vote.setVotingSession(votingSession);
        vote.setAssociated(associated);
        return voteRepository.save(vote);
    }

    private VotingSession findVotingSessionOpen(UUID idStave){
        log.info("Validate Voting Session OPEN");
        return finderStaveService.findById(idStave)
            .getVotingSessionList().stream()
            .filter(votingSession -> votingSession.getEndDate().isAfter(LocalDateTime.now()))
            .findFirst()
            .orElseThrow(() -> new BusinessException("COOPERATIVISM-010"));
    }

    private void validateAlreadyVoted(VotingSession votingSession, Associated associated){
        log.info("Validate already voted");
        finderVoteService.findByVotingSessionIdAndAssociatedId(votingSession.getId(), associated.getId())
            .ifPresent((vote) -> {
                log.error("COOPERATIVISM-011=Only one new per stave is allowed. Associated has already voted in stave theme [{}].", votingSession.getStave().getTheme());
                throw new BusinessException("COOPERATIVISM-011", votingSession.getStave().getTheme());
            });
    }

    private void validateVoteEnableForAssociated(Associated associated){
       log.info("Start request for verifier if associated enabled vote. CPF {}", associated.getCpf());
        try{
            if (!isVoteEnabled(requestFactory.executeGetRequest(associated.getCpf()))){
                log.error("COOPERATIVISM-014=Associate not eligible to vote");
                throw new BusinessException("COOPERATIVISM-014");
            }
        } catch (ClientErrorResponseException ex){
            log.error("COOPERATIVISM-013=Unable to vote because the associated CPF is invalid. CPF: {}", associated.getCpf());
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
