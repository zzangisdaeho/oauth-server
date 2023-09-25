package co.coinvestor.oauthserver.entity;

import co.coinvestor.oauthserver.entity.AuthorizeEntity.Authorizations;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static co.coinvestor.oauthserver.entity.AuthorizeEntity.Authorizations.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(
        name = "USER_SEQ_GENERATOR"
        , sequenceName = "USER_SEQ"
        , allocationSize = 1
)
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ_GENERATOR")
    private Long id;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false)
    private String password;

    @ManyToMany
    @JoinTable(name = "user_authorization_table")
    private Set<AuthorizeEntity> authorizes = new HashSet<>();

    public Set<Authorizations> getEffectiveAuthorizations() {
        Set<Authorizations> effectiveAuthorizations = authorizes.stream()
                .map(AuthorizeEntity::getAuthorization)
                .collect(Collectors.toSet());

        if (effectiveAuthorizations.contains(ADMIN)) {
            effectiveAuthorizations.add(USER);
        }

        return effectiveAuthorizations;
    }

}
