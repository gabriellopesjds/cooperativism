package com.gabriellopesjds.cooperativism.votingsession.domain.service;

import com.gabriellopesjds.cooperativism.exception.BusinessException;
import com.gabriellopesjds.cooperativism.stave.domain.model.Stave;
import com.gabriellopesjds.cooperativism.stave.domain.service.FinderStaveService;
import com.gabriellopesjds.cooperativism.stave.domain.service.RegisterStaveService;
import com.gabriellopesjds.cooperativism.votingsession.domain.model.VotingSession;
import com.gabriellopesjds.cooperativism.votingsession.repository.VotingSessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockStaveDefault;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockVotingSession;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterVotingSessionServiceTest {

    @Mock
    private VotingSessionRepository votingSessionRepository;

    @Mock
    private FinderVotingSessionService finderVotingSessionService;

    @Mock
    private RegisterStaveService registerStaveService;

    @Mock
    private FinderStaveService finderStaveService;

    @InjectMocks
    private RegisterVotingSessionService registerVotingSessionService;

    @Test
    void shouldRegisterVotingSessionWithSuccess(){
        VotingSession votingSession = mockVotingSession();
        Stave stave = mockStaveDefault();

        when(finderStaveService.findById(votingSession.getStave().getId())).thenReturn(stave);
        when(finderVotingSessionService.findByEndDateAfterAndStaveId(Mockito.any(), eq(stave.getId()))).thenReturn(Optional.empty());

        registerVotingSessionService.registerVotingSession(votingSession);

        verify(finderStaveService).findById(eq(votingSession.getStave().getId()));
        verify(finderVotingSessionService).findByEndDateAfterAndStaveId(any(), eq(stave.getId()));
        verify(votingSessionRepository).save(eq(votingSession));
    }

    @Test
    void shouldReturnBusinessExceptionWhenOpenVotingSession(){
        VotingSession votingSession = mockVotingSession();
        Stave stave = mockStaveDefault();

        when(finderStaveService.findById(votingSession.getStave().getId())).thenReturn(stave);
        when(finderVotingSessionService.findByEndDateAfterAndStaveId(Mockito.any(), eq(stave.getId()))).thenReturn(Optional.of(votingSession));

        assertThrows((BusinessException.class), () -> registerVotingSessionService.registerVotingSession(votingSession));

        verify(votingSessionRepository, never()).save(eq(votingSession));
    }

}