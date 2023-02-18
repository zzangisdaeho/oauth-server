package co.coinvestor.oauthserver.repository;

import co.coinvestor.oauthserver.entity.ClientEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientEntityRepository extends JpaRepository<ClientEntity, Long> {

    @EntityGraph(attributePaths = {"grantTypes", "scopes", "redirectUris"})
    Optional<ClientEntity> findByClientId(String clientId);
}
