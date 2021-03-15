package com.gabriellopesjds.cooperativism.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "cooperativism.config.integration.associated.server")
public class AssociatedIntegrationServerProperties {

    private  String url;
    private static final String CPF_RESOLVER = "{cpf}";

    public String getUrl(String cpf) {
        return url.replace(CPF_RESOLVER, cpf);
    }

}
