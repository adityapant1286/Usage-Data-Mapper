package com.zuora.usagedatamapper.repositories.configs;

import com.zuora.usagedatamapper.model.configs.dao.InstanceConfigDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InstanceConfigRepository extends JpaRepository<InstanceConfigDao, UUID> {

    Optional<InstanceConfigDao> findInstanceConfigDaoByName(String name);
    Optional<InstanceConfigDao> findInstanceConfigDaoByTenantId(String tenantId);
    Optional<InstanceConfigDao> findInstanceConfigDaoByTenantIdAndEntityId(String tenantId, String entityId);

    void deleteByName(String name);
    void deleteByTenantIdAndEntityId(String tenantId, String entityId);
}
