package com.gabriellopesjds.cooperativism.stave.domain.service;

import com.gabriellopesjds.cooperativism.stave.domain.model.Stave;
import com.gabriellopesjds.cooperativism.votingsession.domain.model.VotingSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.UUID;

import static com.gabriellopesjds.cooperativism.utils.UtilsTest.generatedVotes;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockStaveDefault;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockVotingSession;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResultStaveServiceTest {

    @Mock
    private FinderStaveService finderStaveService;

    @InjectMocks
    private ResultStaveService resultStaveService;

    @Test
    void shouldContabilizeResultStave(){
        Stave stave = mockStaveDefault();
        UUID idVotingSession = UUID.randomUUID();

        UUID idVotingSessionMock = UUID.randomUUID();

        VotingSession votingSession = mockVotingSession();
        votingSession.setId(idVotingSession);
        votingSession.setVoteList(generatedVotes(votingSession, 100, 50));

        VotingSession votingSessionMock = mockVotingSession();
        votingSessionMock.setId(idVotingSessionMock);
        votingSessionMock.setVoteList(generatedVotes(votingSession, 30, 55));

        stave.setVotingSessionList(Arrays.asList(votingSession, votingSessionMock));

        when(finderStaveService.findById(stave.getId())).thenReturn(stave);

        Stave resultStave = resultStaveService.contabilizeResultStave(stave.getId());
        Assertions.assertNotNull(resultStave);
        Assertions.assertEquals(stave.getId(), resultStave.getId());
        Assertions.assertEquals(stave.getTheme(), resultStave.getTheme());
        Assertions.assertEquals(stave.getDescription(), resultStave.getDescription());
        Assertions.assertNotNull(stave.getVotingSessionList());
        Assertions.assertEquals(2, stave.getVotingSessionList().size());

        extractResult(idVotingSession, resultStave, 150, 100, 50);
        extractResult(idVotingSessionMock, resultStave, 85, 30, 55);
    }

    private void extractResult(UUID idVotingSession, Stave resultStave, int amountTotal, int amountPositive, int amountNegative) {
        resultStave.getVotingSessionList().stream().filter(session -> session.getId().equals(idVotingSession))
            .forEach(session -> {
                assertionsResult(amountTotal, amountPositive, amountNegative, session);
            });
    }

    private void assertionsResult(int amountTotal, int amountPositive, int amountNegative, VotingSession session) {
        Assertions.assertEquals(amountTotal, session.getTotalVotes());
        Assertions.assertEquals(amountPositive, session.getTotalPositiveVotes());
        Assertions.assertEquals(amountNegative, session.getTotalNegativeVotes());
    }

}