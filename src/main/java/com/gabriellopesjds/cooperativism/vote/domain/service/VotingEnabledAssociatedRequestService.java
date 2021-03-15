package com.gabriellopesjds.cooperativism.vote.domain.service;

import com.gabriellopesjds.cooperativism.config.property.AssociatedIntegrationServerProperties;
import com.gabriellopesjds.cooperativism.vote.response.VoteStatusDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VotingEnabledAssociatedRequestService {

    private final RestTemplate restTemplate;
    private final AssociatedIntegrationServerProperties properties;

    public VoteStatusDTO executeGetRequest(String cpf) {
        return restTemplate.getForObject(properties.getUrl(cpf), VoteStatusDTO.class);
    }

}
