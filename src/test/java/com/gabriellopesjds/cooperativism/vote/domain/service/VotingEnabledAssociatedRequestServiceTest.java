package com.gabriellopesjds.cooperativism.vote.domain.service;

import com.gabriellopesjds.cooperativism.config.property.AssociatedIntegrationServerProperties;
import com.gabriellopesjds.cooperativism.vote.response.VoteStatusDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VotingEnabledAssociatedRequestServiceTest {

    private static final String URL = "https://user-info.herokuapp.com/users/{cpf}";
    private static final String CPF = "08535693295";

    @Mock
    private RestTemplate restTemplate;

    @Spy
    private AssociatedIntegrationServerProperties properties = Mockito.spy(AssociatedIntegrationServerProperties.class);

    @InjectMocks
    private VotingEnabledAssociatedRequestService votingEnabledAssociatedRequestService;

    @Test
    void shouldExecuteGetRequest(){
        properties.setUrl(URL);
        votingEnabledAssociatedRequestService.executeGetRequest(CPF);

        verify(restTemplate).getForObject(eq("https://user-info.herokuapp.com/users/" + CPF),
            eq(VoteStatusDTO.class));
    }

}