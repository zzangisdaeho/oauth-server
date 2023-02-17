package co.coinvestor.oauthserver.repository;

import co.coinvestor.oauthserver.entity.AuthorizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorizeEntityRepository extends JpaRepository<AuthorizeEntity, AuthorizeEntity.Authorizations> {
}
