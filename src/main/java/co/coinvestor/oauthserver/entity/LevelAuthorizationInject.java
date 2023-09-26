package co.coinvestor.oauthserver.entity;

import java.util.Set;

import static co.coinvestor.oauthserver.entity.AuthorizeEntity.Authorizations;
import static co.coinvestor.oauthserver.entity.AuthorizeEntity.Authorizations.*;

public interface LevelAuthorizationInject<T> {

    boolean isApplicable(int level);
    Set<T> injectVipLevelAuthorization(Set<T> authorizations);

    Set<T> injectTraderLevelAuthorization(Set<T> authorizations);

    Set<T> injectAffiliateLevelAuthorization(Set<T> authorizations);
}

class Level0 implements LevelAuthorizationInject<Authorizations> {

    @Override
    public boolean isApplicable(int level) {
        return level >= 0;
    }

    @Override
    public Set<Authorizations> injectVipLevelAuthorization(Set<Authorizations> authorizations) {
        authorizations.add(SPOT);
        return authorizations;
    }

    @Override
    public Set<Authorizations> injectTraderLevelAuthorization(Set<Authorizations> authorizations) {
        authorizations.add(TRADER0);
        return authorizations;
    }

    @Override
    public Set<Authorizations> injectAffiliateLevelAuthorization(Set<Authorizations> authorizations) {
        authorizations.add(AFFLIATED0);
        return authorizations;
    }
}

class Level1 implements LevelAuthorizationInject<Authorizations> {

    @Override
    public boolean isApplicable(int level) {
        return level >= 1;
    }

    @Override
    public Set<Authorizations> injectVipLevelAuthorization(Set<Authorizations> authorizations) {
        authorizations.add(ALGO_NOVICE);
        return authorizations;
    }

    @Override
    public Set<Authorizations> injectTraderLevelAuthorization(Set<Authorizations> authorizations) {
        authorizations.add(TRADER1);
        return authorizations;
    }

    @Override
    public Set<Authorizations> injectAffiliateLevelAuthorization(Set<Authorizations> authorizations) {
        authorizations.add(AFFLIATED1);
        return authorizations;
    }
}

class Level2 implements LevelAuthorizationInject<Authorizations> {

    @Override
    public boolean isApplicable(int level) {
        return level >= 2;
    }

    @Override
    public Set<Authorizations> injectVipLevelAuthorization(Set<Authorizations> authorizations) {
        authorizations.add(ALGO_PRO);
        return authorizations;
    }

    @Override
    public Set<Authorizations> injectTraderLevelAuthorization(Set<Authorizations> authorizations) {
        authorizations.add(TRADER2);
        return authorizations;
    }

    @Override
    public Set<Authorizations> injectAffiliateLevelAuthorization(Set<Authorizations> authorizations) {
        authorizations.add(AFFLIATED2);
        return authorizations;
    }
}