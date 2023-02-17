package co.coinvestor.oauthserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class AuthorizeEntity {

    @Id
    @Enumerated(EnumType.STRING)
    private Authorizations authorization;

    public enum Authorizations {
        USER, ADMIN
    }

}