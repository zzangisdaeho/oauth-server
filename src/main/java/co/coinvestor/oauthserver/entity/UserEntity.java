package co.coinvestor.oauthserver.entity;

import co.coinvestor.oauthserver.entity.AuthorizeEntity.Authorizations;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus = UserStatus.ACTIVE;

    @ManyToMany
    @JoinTable(name = "user_authorization_table")
    private Set<AuthorizeEntity> authorizes = new HashSet<>();

    //추천받아서 들어온 사람
    @OneToMany(mappedBy = "parentUser", cascade = CascadeType.PERSIST)
    private List<UserEntity> childUserList = new ArrayList<>();
    // 추천인

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_user_id")
    private UserEntity parentUser;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserDetailEntity userDetail = new UserDetailEntity(this.id, this, 0, 0 ,0);

    @Transient
    private static final LevelAuthorizationInject<Authorizations>[] LEVELS;

    static {
        LEVELS = new LevelAuthorizationInject[]{
                new Level0(),
                new Level1(),
                new Level2()
        };
    }

    public enum UserStatus{
        ACTIVE, INACTIVE, DISABLED
    }

    @Builder
    public UserEntity(String email, String password, UserStatus userStatus, Set<AuthorizeEntity> authorizes, List<UserEntity> childUserList, UserEntity parentUser, UserDetailEntity userDetail) {
        this.email = email;
        this.password = password;
        this.userStatus = userStatus != null ? userStatus : UserStatus.ACTIVE; // default를 ACTIVE로 설정
        this.authorizes = authorizes != null ? authorizes : new HashSet<>();
        this.childUserList = childUserList != null ? childUserList : new ArrayList<>();
        this.parentUser = parentUser;
        this.userDetail = userDetail != null ? userDetail : new UserDetailEntity(this.id, this, 0, 0, 0);
    }

    public Set<Authorizations> getEffectiveAuthorizations() {
        Set<Authorizations> effectiveAuthorizations = new HashSet<>();

        int vipLevel = this.userDetail.getVipLevel();
        int traderLevel = this.userDetail.getTraderLevel();
        int affiliateLevel = this.userDetail.getAffiliateLevel();

        Arrays.stream(LEVELS)
                .filter(level -> level.isApplicable(vipLevel))
                .forEach(level -> level.injectVipLevelAuthorization(effectiveAuthorizations));

        Arrays.stream(LEVELS)
                .filter(level -> level.isApplicable(traderLevel))
                .forEach(level -> level.injectTraderLevelAuthorization(effectiveAuthorizations));

        Arrays.stream(LEVELS)
                .filter(level -> level.isApplicable(affiliateLevel))
                .forEach(level -> level.injectAffiliateLevelAuthorization(effectiveAuthorizations));

//        for (LevelAuthorizationInject<Authorizations> level : LEVELS) {
//            if (level.isApplicable(vipLevel)) level.injectVipLevelAuthorization(effectiveAuthorizations);
//
//            if (level.isApplicable(traderLevel)) level.injectTraderLevelAuthorization(effectiveAuthorizations);
//
//            if (level.isApplicable(affiliateLevel)) level.injectAffiliateLevelAuthorization(effectiveAuthorizations);
//        }

        return effectiveAuthorizations;
    }



}
