package co.coinvestor.oauthserver.repository;

import co.coinvestor.oauthserver.entity.ScopeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScopeEntityRepository extends JpaRepository<ScopeEntity, ScopeEntity.SCOPES> {
}
