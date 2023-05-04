package co.coinvestor.oauthserver.data;

import co.coinvestor.oauthserver.entity.*;
import co.coinvestor.oauthserver.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InitData {

    private final UserEntityRepository userEntityRepository;

    private final AuthorizeEntityRepository authorizeEntityRepository;
    private final GrantTypeEntityRepository grantTypeEntityRepository;
    private final ScopeEntityRepository scopeEntityRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClientEntityRepository clientEntityRepository;

    @Order(1)
    @EventListener(value = ApplicationReadyEvent.class)
    public void initAuthorities(){
        Set<AuthorizeEntity.Authorizations> allAuthorizations = Arrays.stream(AuthorizeEntity.Authorizations.values())
                .collect(Collectors.toSet());

        List<AuthorizeEntity> alreadyExistAuthorizations = authorizeEntityRepository.findAll();

        allAuthorizations.removeAll(alreadyExistAuthorizations.stream().map(AuthorizeEntity::getAuthorization)
                .collect(Collectors.toSet()));

        List<AuthorizeEntity> addAuthorizeEntityList = allAuthorizations.stream().map(AuthorizeEntity::new)
                .collect(Collectors.toList());

        authorizeEntityRepository.saveAll(addAuthorizeEntityList);
    }

    @Order(2)
    @EventListener(value = ApplicationReadyEvent.class)
    public void initGrantTypes(){
        Set<GrantTypeEntity.GrantTypes> allGrantTypes = Arrays.stream(GrantTypeEntity.GrantTypes.values())
                .collect(Collectors.toSet());

        List<GrantTypeEntity> alreadyExistGrantTypes = grantTypeEntityRepository.findAll();

        allGrantTypes.removeAll(alreadyExistGrantTypes.stream().map(GrantTypeEntity::getGrantType)
                .collect(Collectors.toSet()));

        List<GrantTypeEntity> addGrantTypeEntityList = allGrantTypes.stream().map(GrantTypeEntity::new)
                .collect(Collectors.toList());

        grantTypeEntityRepository.saveAll(addGrantTypeEntityList);
    }

    @Order(3)
    @EventListener(value = ApplicationReadyEvent.class)
    public void initScopes(){
        Set<ScopeEntity.SCOPES> allScopes = Arrays.stream(ScopeEntity.SCOPES.values())
                .collect(Collectors.toSet());

        List<ScopeEntity> alreadyExistScopes = scopeEntityRepository.findAll();

        allScopes.removeAll(alreadyExistScopes.stream().map(ScopeEntity::getScope)
                .collect(Collectors.toSet()));

        List<ScopeEntity> addScopeEntityList = allScopes.stream().map(ScopeEntity::new)
                .collect(Collectors.toList());

        scopeEntityRepository.saveAll(addScopeEntityList);
    }

    @Order(4)
//    @EventListener(value = ApplicationReadyEvent.class)
    public void initUserData(){
        List<AuthorizeEntity> all = authorizeEntityRepository.findAll();

        UserEntity issac = UserEntity.builder()
                .authorizes(new HashSet<>(all))
                .email("issac.kim@bclabs.com")
                .password(passwordEncoder.encode("pass"))
                .build();
        userEntityRepository.save(issac);
    }

    @Order(5)
//    @EventListener(value = ApplicationReadyEvent.class)
    public void initCredentialData(){
        List<ScopeEntity> allScopes = scopeEntityRepository.findAll();
        List<GrantTypeEntity> allGrantTypes = grantTypeEntityRepository.findAll();

        ClientEntity testClient = ClientEntity.builder()
                .clientId("client")
                .clientSecret(passwordEncoder.encode("secret"))
                .grantTypes(new HashSet<>(allGrantTypes))
                .scopes(new HashSet<>(allScopes))
                .redirectUris(Set.of("http://localhost:9090/home", "http://localhost:9091/home"))
                .build();

        ClientEntity resourceServerClient = ClientEntity.builder()
                .clientId("resourceserver")
                .clientSecret(passwordEncoder.encode("resourceserversecret"))
                .build();


        clientEntityRepository.save(testClient);
        clientEntityRepository.save(resourceServerClient);
    }


//    @EventListener(value = ApplicationReadyEvent.class)
//    public void deleteTest(){
//        UserEntity userEntity = userEntityRepository.findById(1L).get();
//        userEntityRepository.delete(userEntity);
//    }
}
