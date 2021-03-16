package com.zuora.usagedatamapper.services;

import com.zuora.usagedatamapper.model.configs.vo.InstanceConfig;

import java.util.UUID;

public interface InstanceConfigService {

    InstanceConfig findById(UUID id);
    InstanceConfig findByName(String name);
    InstanceConfig findByTenantIdAndEntityId(String tenantId, String entityId);

    InstanceConfig createInstanceConfig(InstanceConfig instanceConfig);
    InstanceConfig updateInstanceConfigById(UUID instanceId, InstanceConfig instanceConfig);
    InstanceConfig updateInstanceConfigByName(String instanceConfigName, InstanceConfig instanceConfig);

    void deleteById(UUID id);
    void deleteByTenantId(String tenantId, String entityId);
    void deleteByName(String name);
}
