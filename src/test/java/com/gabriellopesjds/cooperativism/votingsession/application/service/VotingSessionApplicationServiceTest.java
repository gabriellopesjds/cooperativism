package com.gabriellopesjds.cooperativism.votingsession.application.service;

import com.gabriellopesjds.api.model.VotingSessionRequestDTO;
import com.gabriellopesjds.api.model.VotingSessionResponseDTO;
import com.gabriellopesjds.cooperativism.votingsession.domain.model.VotingSession;
import com.gabriellopesjds.cooperativism.votingsession.domain.service.RegisterVotingSessionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockVotingSession;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockVotingSessionRequestDTO;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockVotingSessionResponseDTO;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VotingSessionApplicationServiceTest {

    @Mock
    private RegisterVotingSessionService registerVotingSessionService;

    @Mock
    private VotingSessionFactory votingSessionFactory;

    @InjectMocks
    private VotingSessionApplicationService votingSessionApplicationService;

    @Test
    void shouldRegisterVotingSession() {
        UUID id = UUID.randomUUID();
        VotingSessionRequestDTO votingSessionRequestDTO = mockVotingSessionRequestDTO();
        VotingSession votingSession = mockVotingSession();
        VotingSessionResponseDTO votingSessionResponseDTO = mockVotingSessionResponseDTO();

        when(votingSessionFactory.fromValue(id, votingSessionRequestDTO)).thenReturn(votingSession);
        when(registerVotingSessionService.registerVotingSession(votingSession)).thenReturn(votingSession);
        when(votingSessionFactory.buildResponse(votingSession)).thenReturn(votingSessionResponseDTO);

        votingSessionApplicationService.registerVotingSession(id, votingSessionRequestDTO);

        verify(votingSessionFactory).fromValue(eq(id), eq(votingSessionRequestDTO));
        verify(registerVotingSessionService).registerVotingSession(eq(votingSession));
        verify(votingSessionFactory).buildResponse(eq(votingSession));
    }
}