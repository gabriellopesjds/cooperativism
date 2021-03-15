package com.gabriellopesjds.cooperativism.vote.domain.service;

import com.gabriellopesjds.cooperativism.associated.domain.model.Associated;
import com.gabriellopesjds.cooperativism.associated.domain.service.FinderAssociatedService;
import com.gabriellopesjds.cooperativism.exception.BusinessException;
import com.gabriellopesjds.cooperativism.stave.domain.model.Stave;
import com.gabriellopesjds.cooperativism.stave.domain.service.FinderStaveService;
import com.gabriellopesjds.cooperativism.stave.domain.service.RegisterStaveService;
import com.gabriellopesjds.cooperativism.vote.domain.model.Vote;
import com.gabriellopesjds.cooperativism.vote.repository.VoteRepository;
import com.gabriellopesjds.cooperativism.vote.response.VoteStatusDTO;
import com.gabriellopesjds.cooperativism.votingsession.domain.model.VotingSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockAssociated;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockStaveDefault;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockVote;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockVotingSession;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterVoteServiceTest {

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private FinderVoteService finderVoteService;

    @Mock
    private RegisterStaveService registerStaveService;

    @Mock
    private FinderStaveService finderStaveService;

    @Mock
    private FinderAssociatedService finderAssociatedService;

    @Mock
    private VotingEnabledAssociatedRequestService consumingUserInfoService;

    @InjectMocks
    private RegisterVoteService registerVoteService;

    @Test
    void shouldRegisterVoteWithSuccess(){
        UUID idStave = UUID.randomUUID();
        Associated associated = mockAssociated();
        Vote vote = mockVote();
        Stave stave = mockStaveDefault();
        VotingSession votingSession = mockVotingSession();
        votingSession.setEndDate(LocalDateTime.MAX);
        stave.setVotingSessionList(Collections.singletonList(votingSession));
        VoteStatusDTO voteStatusDTO = new VoteStatusDTO();
        voteStatusDTO.setStatus("ABLE_TO_VOTE");

        when(finderStaveService.findById(idStave)).thenReturn(stave);
        when(finderAssociatedService.findById(vote.getAssociated().getId())).thenReturn(associated);
        when(finderVoteService.findByVotingSessionIdAndAssociatedId(votingSession.getId(), associated.getId())).thenReturn(Optional.empty());
        when(consumingUserInfoService.executeGetRequest(associated.getCpf())).thenReturn(voteStatusDTO);

        registerVoteService.registerVote(idStave, vote);

        verify(finderStaveService).findById(eq(idStave));
        verify(finderAssociatedService).findById(eq(vote.getAssociated().getId()));
        verify(finderVoteService).findByVotingSessionIdAndAssociatedId(eq(votingSession.getId()), eq(associated.getId()));
        verify(consumingUserInfoService).executeGetRequest(eq(associated.getCpf()));
        verify(voteRepository).save(eq(vote));
    }

    @Test
    void shouldReturnBusinessExceptionWhenVotingSessionClose(){
        UUID idStave = UUID.randomUUID();
        Associated associated = mockAssociated();
        Vote vote = mockVote();
        Stave stave = mockStaveDefault();
        VotingSession votingSession = mockVotingSession();
        votingSession.setEndDate(LocalDateTime.MIN);
        stave.setVotingSessionList(Collections.singletonList(votingSession));

        when(finderStaveService.findById(idStave)).thenReturn(stave);

        assertThrows(BusinessException.class, () -> registerVoteService.registerVote(idStave, vote));

        verify(finderStaveService).findById(eq(idStave));
        verify(finderAssociatedService, never()).findById(eq(vote.getAssociated().getId()));
        verify(finderVoteService, never()).findByVotingSessionIdAndAssociatedId(eq(votingSession.getId()), eq(associated.getId()));
        verify(consumingUserInfoService, never()).executeGetRequest(eq(associated.getCpf()));
        verify(voteRepository, never()).save(eq(vote));
    }

    @Test
    void shouldReturnBusinessExceptionWhenAssociatedAlreadyVoted(){
        UUID idStave = UUID.randomUUID();
        Associated associated = mockAssociated();
        Vote vote = mockVote();
        Stave stave = mockStaveDefault();
        VotingSession votingSession = mockVotingSession();
        votingSession.setEndDate(LocalDateTime.MAX);
        stave.setVotingSessionList(Collections.singletonList(votingSession));

        when(finderStaveService.findById(idStave)).thenReturn(stave);
        when(finderAssociatedService.findById(vote.getAssociated().getId())).thenReturn(associated);
        when(finderVoteService.findByVotingSessionIdAndAssociatedId(votingSession.getId(), associated.getId())).thenReturn(Optional.of(vote));

        assertThrows(BusinessException.class, () -> registerVoteService.registerVote(idStave, vote));

        verify(finderStaveService).findById(eq(idStave));
        verify(finderAssociatedService).findById(eq(vote.getAssociated().getId()));
        verify(finderVoteService).findByVotingSessionIdAndAssociatedId(eq(votingSession.getId()), eq(associated.getId()));
        verify(consumingUserInfoService, never()).executeGetRequest(eq(associated.getCpf()));
        verify(voteRepository, never()).save(eq(vote));
    }

    @Test
    void shouldReturnBusinessExceptionWhenAssociatedNotEligibleToVote(){
        UUID idStave = UUID.randomUUID();
        Associated associated = mockAssociated();
        Vote vote = mockVote();
        Stave stave = mockStaveDefault();
        VotingSession votingSession = mockVotingSession();
        votingSession.setEndDate(LocalDateTime.MAX);
        stave.setVotingSessionList(Collections.singletonList(votingSession));
        VoteStatusDTO voteStatusDTO = new VoteStatusDTO();
        voteStatusDTO.setStatus("UNABLE_TO_VOTE");

        when(finderStaveService.findById(idStave)).thenReturn(stave);
        when(finderAssociatedService.findById(vote.getAssociated().getId())).thenReturn(associated);
        when(finderVoteService.findByVotingSessionIdAndAssociatedId(votingSession.getId(), associated.getId())).thenReturn(Optional.empty());
        when(consumingUserInfoService.executeGetRequest(associated.getCpf())).thenReturn(voteStatusDTO);

        assertThrows(BusinessException.class, () -> registerVoteService.registerVote(idStave, vote));

        verify(finderStaveService).findById(eq(idStave));
        verify(finderAssociatedService).findById(eq(vote.getAssociated().getId()));
        verify(finderVoteService).findByVotingSessionIdAndAssociatedId(eq(votingSession.getId()), eq(associated.getId()));
        verify(consumingUserInfoService).executeGetRequest(eq(associated.getCpf()));
        verify(voteRepository, never()).save(eq(vote));
    }
}