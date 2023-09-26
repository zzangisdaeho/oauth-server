package co.coinvestor.oauthserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailEntity {

    @Id
    @Column(name = "user_id")
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private int vipLevel;
    private int traderLevel;
    private int affiliateLevel;

    @ElementCollection(targetClass = Badge.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_detail_badges", joinColumns = @JoinColumn(name = "user_detail_id"))
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<Badge> badges = new HashSet<>();

    public enum Badge {
        BADGE1, BADGE2;
    }
}
