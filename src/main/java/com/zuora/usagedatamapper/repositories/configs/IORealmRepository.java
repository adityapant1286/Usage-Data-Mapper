package com.zuora.usagedatamapper.repositories.configs;

import com.zuora.usagedatamapper.model.configs.dao.IORealmDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Repository
public interface IORealmRepository extends JpaRepository<IORealmDao, UUID> {
    Optional<IORealmDao> findIORealmDaoByInstanceConfigId(UUID instanceConfigId);
    Optional<List<IORealmDao>> findIORealmDaosByNameOrHost(String name, String host);
    Optional<IORealmDao> findIORealmDaoByName(String name);

    void deleteByInstanceConfigId(UUID instanceConfigId);
    void deleteByName(String name);
}
