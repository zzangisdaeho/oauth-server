package co.coinvestor.oauthserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class AuthorizeEntity implements Serializable {

    @Id
    @Enumerated(EnumType.STRING)
    private Authorizations authorization;

    public enum Authorizations {
        USER, ADMIN
    }

}