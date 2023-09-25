package co.coinvestor.oauthserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(
        name = "CLIENT_SEQ_GENERATOR"
        , sequenceName = "CLIENT_SEQ"
        , allocationSize = 1
)
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLIENT_SEQ_GENERATOR")
    private Long id;

    @Column(unique = true)
    private String clientId;

    private String clientSecret;

    @ElementCollection
    @CollectionTable(
            name = "redirectUriTable",
            joinColumns = @JoinColumn(name = "clientId")
    )
    private Set<String> redirectUris = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "client_grant_type_table")
    private Set<GrantTypeEntity> grantTypes = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "client_scope_table")
    private Set<ScopeEntity> scopes = new HashSet<>();

}
