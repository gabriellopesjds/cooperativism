package com.gabriellopesjds.cooperativism.votingsession.application.service;

import com.gabriellopesjds.api.model.AssemblyBaseResponseDTO;
import com.gabriellopesjds.api.model.StaveResponseDTO;
import com.gabriellopesjds.api.model.VotingSessionRequestDTO;
import com.gabriellopesjds.api.model.VotingSessionResponseDTO;
import com.gabriellopesjds.api.model.VotingSessionResultBaseResponseDTO;
import com.gabriellopesjds.api.model.VotingSessionResultResponseDTO;
import com.gabriellopesjds.api.model.VotingSessionStateDTO;
import com.gabriellopesjds.cooperativism.assembly.application.service.AssemblyFactory;
import com.gabriellopesjds.cooperativism.stave.application.service.StaveFactory;
import com.gabriellopesjds.cooperativism.stave.domain.model.Stave;
import com.gabriellopesjds.cooperativism.votingsession.domain.model.VotingSession;
import com.gabriellopesjds.cooperativism.votingsession.domain.model.enumerated.VotingSessionStateEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockStaveDefault;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockVotingSession;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockVotingSessionRequestDTO;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockVotingSessionResult;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VotingSessionFactoryTest {

    @Mock
    private StaveFactory staveFactory;

    @Mock
    private AssemblyFactory assemblyFactory;

    @InjectMocks
    private VotingSessionFactory votingSessionFactory;

    @Test
    void shouldBuildVotingSessionFromValueVotingSessionRequestDTO() {
        UUID id = UUID.randomUUID();
        VotingSessionRequestDTO votingSessionRequestDTO = mockVotingSessionRequestDTO();
        VotingSession votingSession = votingSessionFactory.fromValue(id, votingSessionRequestDTO);

        assertEquals(votingSessionRequestDTO.getDurationInMinutes(), votingSession.getDuration());
        assertEquals(id, votingSession.getStave().getId());
        assertEquals(votingSession.getEndDate().minusMinutes(votingSession.getDuration()), votingSession.getStartDate());
        Assertions.assertNotNull(votingSession.getStartDate());
    }

    @Test
    void shouldBuildVotingSessionResponseDTO() {
        StaveResponseDTO staveResponseDTO = Mockito.mock(StaveResponseDTO.class);
        VotingSession votingSession = mockVotingSession();
        votingSession.setEndDate(LocalDateTime.MAX);
        when(staveFactory.buildResponse(votingSession.getStave())).thenReturn(staveResponseDTO);

        VotingSessionResponseDTO votingSessionResponseDTO = votingSessionFactory.buildResponse(votingSession);

        assertEquals(votingSession.getId(), votingSessionResponseDTO.getId());
        assertEquals(votingSession.getStartDate(), votingSessionResponseDTO.getStartDate());
        assertEquals(votingSession.getEndDate(), votingSessionResponseDTO.getEndDate());
        assertEquals(votingSession.getDuration(), votingSessionResponseDTO.getDurationInMinutes());
        assertEquals(VotingSessionStateDTO.OPEN, votingSessionResponseDTO.getState());
        assertNotNull(votingSession.getStave());

    }

    @Test
    void shouldBuildListVotingSessionResultBaseResponseDTO(){
        VotingSession votingSession = mockVotingSessionResult(mockVotingSession());

        List<VotingSessionResultBaseResponseDTO> votingSessionResultBaseResponseDTOS =
            votingSessionFactory.buildListVotingSessionResultBaseResponseDTO(Collections.singletonList(votingSession));

        Assertions.assertNotNull(votingSessionResultBaseResponseDTOS);
        Assertions.assertEquals(1, votingSessionResultBaseResponseDTOS.size());
    }

    @Test
    void shouldBuildResponseVotingSessionResultResponseDTO(){
        Stave stave = mockStaveDefault();
        stave.setVotingSessionList(Arrays.asList(mockVotingSessionResult(mockVotingSession())));
        AssemblyBaseResponseDTO assemblyBaseResponseDTO = Mockito.mock(AssemblyBaseResponseDTO.class);
        when(assemblyFactory.buildAssemblyBaseResponseDTO(stave.getAssembly())).thenReturn(assemblyBaseResponseDTO);

        VotingSessionResultResponseDTO votingSessionResultResponseDTO = votingSessionFactory.buildResponse(stave);

        Assertions.assertEquals(stave.getId(), votingSessionResultResponseDTO.getId());
        Assertions.assertEquals(stave.getDescription(), votingSessionResultResponseDTO.getDescription());
        Assertions.assertEquals(stave.getTheme(), votingSessionResultResponseDTO.getTheme());
        Assertions.assertNotNull(votingSessionResultResponseDTO.getAssembly());
        Assertions.assertNotNull(votingSessionResultResponseDTO.getVotingSession());

        verify(assemblyFactory).buildAssemblyBaseResponseDTO(eq(stave.getAssembly()));
    }

}