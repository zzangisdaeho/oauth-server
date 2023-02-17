package co.coinvestor.oauthserver.repository;

import co.coinvestor.oauthserver.entity.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    @EntityGraph(attributePaths = {"authorizes"})
    Optional<UserEntity> findByEmail(String email);
}
