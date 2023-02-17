package co.coinvestor.oauthserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
public class GrantTypeEntity {

    @Id
    @Enumerated(EnumType.STRING)
    private GrantTypes grantType;

    @Getter
    public enum GrantTypes{
        PASSWORD("password"), AUTHORIZATION_CODE("authorization_code"), REFRESH_TOKEN("refresh_token");

        private String oauthType;

        GrantTypes(String oauthType) {
            this.oauthType = oauthType;
        }
    }
}
