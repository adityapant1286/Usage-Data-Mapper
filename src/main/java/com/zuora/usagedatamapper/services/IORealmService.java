package com.zuora.usagedatamapper.services;

import com.zuora.usagedatamapper.model.configs.vo.IORealm;

import java.util.List;
import java.util.UUID;

public interface IORealmService {
    IORealm findIORealmById(UUID id);
    IORealm findIORealmByInstanceConfigId(UUID instanceConfigId);
    List<IORealm> findIORealmDaoByNameOrHost(String name, String host);

    IORealm createIORealm(IORealm IORealm);

    IORealm updateIORealmById(UUID id, IORealm IORealm);
    IORealm updateIORealmByInstanceConfigId(UUID instanceConfigId, IORealm IORealm);
    IORealm updateIORealmByName(String name, IORealm IORealm);

    void deleteById(UUID id);
    void deleteByName(String name);
    void deleteByInstanceConfigId(UUID instanceConfigId);
}
