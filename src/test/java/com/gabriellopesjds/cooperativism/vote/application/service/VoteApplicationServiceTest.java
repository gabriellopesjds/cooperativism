package com.gabriellopesjds.cooperativism.vote.application.service;

import com.gabriellopesjds.api.model.VoteRequestDTO;
import com.gabriellopesjds.api.model.VoteResponseDTO;
import com.gabriellopesjds.cooperativism.utils.UtilsTest;
import com.gabriellopesjds.cooperativism.vote.domain.model.Vote;
import com.gabriellopesjds.cooperativism.vote.domain.service.RegisterVoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockVote;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockVoteResponseDTO;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VoteApplicationServiceTest {

    @Mock
    private RegisterVoteService registerVoteService;

    @Mock
    private VoteFactory voteFactory;

    @InjectMocks
    private VoteApplicationService voteApplicationService;

    @Test
    void shouldRegisterVote() {
        UUID id = UUID.randomUUID();
        VoteRequestDTO voteRequestDTO = UtilsTest.mockVoteRequestDTO();
        Vote vote = mockVote();
        VoteResponseDTO voteResponseDTO = mockVoteResponseDTO();

        when(voteFactory.fromValue(voteRequestDTO)).thenReturn(vote);
        when(registerVoteService.registerVote(id, vote)).thenReturn(vote);
        when(voteFactory.buildResponse(vote)).thenReturn(voteResponseDTO);

        voteApplicationService.registerVote(id, voteRequestDTO);

        verify(voteFactory).fromValue(eq(voteRequestDTO));
        verify(registerVoteService).registerVote(eq(id), eq(vote));
        verify(voteFactory).buildResponse(eq(vote));
    }
}