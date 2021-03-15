package com.gabriellopesjds.cooperativism.votingsession.domain.service;

import com.gabriellopesjds.cooperativism.votingsession.repository.VotingSessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FinderVotingSessionServiceTest {

    @Mock
    private VotingSessionRepository votingSessionRepository;

    @InjectMocks
    private FinderVotingSessionService finderVotingSessionService;

    @Test
    void shouldFindVotingSessionByEndDateAfterAndStaveId(){
        LocalDateTime date = LocalDateTime.now();
        UUID id = UUID.randomUUID();
        finderVotingSessionService.findByEndDateAfterAndStaveId(date, id);

        verify(votingSessionRepository).findByEndDateAfterAndStaveId(eq(date), eq(id));
    }
}