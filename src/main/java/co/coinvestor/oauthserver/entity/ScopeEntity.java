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
public class ScopeEntity {

    @Id
    @Enumerated(EnumType.STRING)
    private SCOPES scope;

    public enum SCOPES{
        USER, ADMIN
    }
}
