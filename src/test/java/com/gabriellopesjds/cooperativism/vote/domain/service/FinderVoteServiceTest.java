package com.gabriellopesjds.cooperativism.vote.domain.service;

import com.gabriellopesjds.cooperativism.vote.repository.VoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FinderVoteServiceTest {

    @Mock
    private VoteRepository voteRepository;

    @InjectMocks
    private FinderVoteService finderVoteService;

    @Test
    void shouldFindByVotingSessionIdAndAssociatedId(){
        UUID idVotingSession = UUID.randomUUID();
        UUID idAssociated = UUID.randomUUID();

        finderVoteService.findByVotingSessionIdAndAssociatedId(idVotingSession, idAssociated);

        verify(voteRepository).findByVotingSessionIdAndAssociatedId(eq(idVotingSession), eq(idAssociated));
    }
}