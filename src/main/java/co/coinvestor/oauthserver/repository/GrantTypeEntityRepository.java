package co.coinvestor.oauthserver.repository;

import co.coinvestor.oauthserver.entity.GrantTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrantTypeEntityRepository extends JpaRepository<GrantTypeEntity, GrantTypeEntity.GrantTypes> {
}
