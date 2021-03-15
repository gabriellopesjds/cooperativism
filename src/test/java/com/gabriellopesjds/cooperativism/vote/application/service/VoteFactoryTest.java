package com.gabriellopesjds.cooperativism.vote.application.service;

import com.gabriellopesjds.api.model.AssociatedResponseDTO;
import com.gabriellopesjds.api.model.VoteRequestDTO;
import com.gabriellopesjds.api.model.VoteResponseDTO;
import com.gabriellopesjds.cooperativism.associated.application.service.AssociatedFactory;
import com.gabriellopesjds.cooperativism.vote.domain.model.Vote;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockVote;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockVoteRequestDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class VoteFactoryTest {

    @Mock
    private AssociatedFactory associatedFactory;

    @InjectMocks
    private VoteFactory voteFactory;

    @Test
    void shouldBuildVoteFromValueVoteRequestDTO() {
        VoteRequestDTO voteRequestDTO = mockVoteRequestDTO();
        Vote vote = voteFactory.fromValue(voteRequestDTO);

        assertEquals(voteRequestDTO.getVoteType().name(), vote.getVoteType().name());
        assertEquals(voteRequestDTO.getIdAssociated(), vote.getAssociated().getId());
    }

    @Test
    void shouldBuildVoteResponseDTO() {
        Vote vote = mockVote();
        AssociatedResponseDTO associatedResponseDTO = Mockito.mock(AssociatedResponseDTO.class);
        Mockito.when(associatedFactory.buildResponse(vote.getAssociated())).thenReturn(associatedResponseDTO);

        VoteResponseDTO voteResponseDTO = voteFactory.buildResponse(vote);

        assertEquals(vote.getId(), voteResponseDTO.getId());
        assertEquals(vote.getVoteType().name(), voteResponseDTO.getVote().name());
        assertNotNull(voteResponseDTO.getAssociated());
    }

}