package co.coinvestor.oauthserver.config;

import co.coinvestor.oauthserver.entity.ClientEntity;
import co.coinvestor.oauthserver.repository.ClientEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomClientDetailsService implements ClientDetailsService {

    private final ClientEntityRepository clientEntityRepository;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        ClientEntity findClient = clientEntityRepository.findByClientId(clientId)
                .orElseThrow(() -> new ClientRegistrationException("Client not found: " + clientId));

        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId(findClient.getClientId());
        clientDetails.setClientSecret(findClient.getClientSecret());
        clientDetails.setAuthorizedGrantTypes(findClient.getGrantTypes().stream()
                .map(type -> type.getGrantType().getOauthType()).collect(Collectors.toSet()));
        clientDetails.setRegisteredRedirectUri(findClient.getRedirectUris());
        clientDetails.setScope(findClient.getScopes().stream()
                .map(scopeEntity -> scopeEntity.getScope().name()).collect(Collectors.toSet()));

        return clientDetails;
    }
}
