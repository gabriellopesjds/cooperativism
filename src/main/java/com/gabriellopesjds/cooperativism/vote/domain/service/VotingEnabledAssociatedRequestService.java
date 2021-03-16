package com.gabriellopesjds.cooperativism.vote.domain.service;

import com.gabriellopesjds.cooperativism.config.property.AssociatedIntegrationServerProperties;
import com.gabriellopesjds.cooperativism.vote.response.VoteStatusDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class VotingEnabledAssociatedRequestService {

    private final RestTemplate restTemplate;
    private final AssociatedIntegrationServerProperties properties;

    public VoteStatusDTO executeGetRequest(String cpf) {
        log.info("Execute request in: GET {}", properties.getUrl(cpf));
        return restTemplate.getForObject(properties.getUrl(cpf), VoteStatusDTO.class);
    }

}
