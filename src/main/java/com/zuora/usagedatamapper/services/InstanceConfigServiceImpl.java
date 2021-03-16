package com.zuora.usagedatamapper.services;

import com.zuora.usagedatamapper.errorhandling.exceptions.InstanceConfigRuntimeException;
import com.zuora.usagedatamapper.model.configs.dao.InstanceConfigDao;
import com.zuora.usagedatamapper.model.configs.vo.InstanceConfig;
import com.zuora.usagedatamapper.repositories.configs.InstanceConfigRepository;
import com.zuora.usagedatamapper.transformers.Transformer;
import com.zuora.usagedatamapper.util.AppUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.zuora.usagedatamapper.util.AppUtil.commonExclusionFields;
import static com.zuora.usagedatamapper.util.AppUtil.formatStr;
import static com.zuora.usagedatamapper.util.AppUtil.isEmpty;
import static com.zuora.usagedatamapper.util.Constants.FAILED_TO_DELETE;
import static com.zuora.usagedatamapper.util.Constants.FAILED_TO_SAVE;
import static com.zuora.usagedatamapper.util.Constants.RECORD_NOT_FOUND_BY;

@Slf4j
@Service(value = "defaultInstanceConfigService")
public class InstanceConfigServiceImpl implements InstanceConfigService {

    private final InstanceConfigRepository instanceConfigRepository;

    public InstanceConfigServiceImpl(InstanceConfigRepository instanceConfigRepository) {
        this.instanceConfigRepository = instanceConfigRepository;
    }

    @Override
    public InstanceConfig findById(UUID id) {
        InstanceConfigDao dao = instanceConfigRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(formatStr(RECORD_NOT_FOUND_BY,
                        "InstanceConfig", "id=" + id)));

        return toVo().transform(dao);
    }

    @Override
    public InstanceConfig findByName(String name) {
        InstanceConfigDao dao = instanceConfigRepository.findInstanceConfigDaoByName(name)
                .orElseThrow(() -> new IllegalArgumentException(formatStr(RECORD_NOT_FOUND_BY,
                        "InstanceConfig", "name=" + name)));

        return toVo().transform(dao);
    }

    @Override
    public InstanceConfig findByTenantIdAndEntityId(String tenantId, String entityId) {

        InstanceConfigDao dao = (isEmpty(entityId))

                ? instanceConfigRepository.findInstanceConfigDaoByTenantId(tenantId)
                                .orElseThrow(() -> new IllegalArgumentException(formatStr(RECORD_NOT_FOUND_BY,
                                        "InstanceConfig", "tenantId=" + tenantId)))

                : instanceConfigRepository.findInstanceConfigDaoByTenantIdAndEntityId(tenantId, entityId)
                                .orElseThrow(() -> new IllegalArgumentException(formatStr(RECORD_NOT_FOUND_BY,
                                        "InstanceConfig", "tenantId=" + tenantId + " and entityId=" + entityId)));
        return toVo().transform(dao);
    }

    @Override
    public InstanceConfig createInstanceConfig(InstanceConfig instanceConfig) {
        InstanceConfigDao dao = toDao().transform(instanceConfig);
        InstanceConfigDao saved = Optional.of(instanceConfigRepository.save(dao))
                .orElseThrow(() -> new InstanceConfigRuntimeException(formatStr(FAILED_TO_SAVE, "InstanceConfig")));

        return toVo().transform(saved);
    }

    @Override
    public InstanceConfig updateInstanceConfigById(UUID instanceId, InstanceConfig instanceConfig) {
        final InstanceConfigDao dao = toDao().transform(instanceConfig);
        dao.setId(instanceId);

        InstanceConfigDao saved = Optional.of(instanceConfigRepository.save(dao))
                .orElseThrow(() -> new InstanceConfigRuntimeException(formatStr(FAILED_TO_SAVE, "InstanceConfig")));

        return toVo().transform(saved);
    }

    @Override
    public InstanceConfig updateInstanceConfigByName(String instanceConfigName, InstanceConfig instanceConfig) {

        InstanceConfigDao found = instanceConfigRepository.findInstanceConfigDaoByName(instanceConfigName)
                .orElseThrow(() -> new IllegalArgumentException(formatStr(RECORD_NOT_FOUND_BY,
                        "InstanceConfig", "name=" + instanceConfigName)));

//        InstanceConfigDao transform = toDao("id", "createdDate", "updatedDate").transform(found);
        Transformer.transform(instanceConfig, found, commonExclusionFields());
//        BeanUtils.copyProperties(instanceConfig, found,
//                "id", "createdDate", "updatedDate");

        InstanceConfigDao saved = Optional.of(instanceConfigRepository.save(found))
                .orElseThrow(() -> new InstanceConfigRuntimeException(formatStr(FAILED_TO_SAVE, "InstanceConfig")));

        return toVo().transform(saved);
    }

    @Override
    public void deleteById(UUID id) {

        try {
            instanceConfigRepository.deleteById(id);
        } catch (Exception ex) {
            throw new InstanceConfigRuntimeException(formatStr(FAILED_TO_DELETE,"InstanceConfig", "id=" + id));
        }
    }

    @Override
    public void deleteByName(String name) {
        try {
            instanceConfigRepository.deleteByName(name);
        } catch (Exception ex) {
            throw new InstanceConfigRuntimeException(formatStr(FAILED_TO_DELETE,"InstanceConfig", "name=" + name));
        }
    }

    @Override
    public void deleteByTenantId(String tenantId, String entityId) {
        try {
            instanceConfigRepository.deleteByTenantIdAndEntityId(tenantId, entityId);
        } catch (Exception ex) {
            String param = "tenantId=" + tenantId;
            if (!AppUtil.isEmpty(entityId))
                param += ", entityId=" + entityId;

            throw new InstanceConfigRuntimeException(formatStr(FAILED_TO_DELETE,"InstanceConfig", param));
        }
    }

    private static Transformer<InstanceConfigDao, InstanceConfig> toVo() {
        return (dao) -> {
            InstanceConfig vo = new InstanceConfig();
            BeanUtils.copyProperties(dao, vo, InstanceConfig.class);
            return vo;
        };
    }

    private static Transformer<InstanceConfig, InstanceConfigDao> toDao() {
        return (vo) -> {
            InstanceConfigDao dao = new InstanceConfigDao();
            BeanUtils.copyProperties(vo, dao, InstanceConfigDao.class);
            return dao;
        };
    }

/*
    private static Transformer<InstanceConfig, InstanceConfigDao> toDao(String... exclusionFields) {
        return (vo) -> {
            InstanceConfigDao dao = new InstanceConfigDao();
            Optional.of(exclusionFields).ifPresent(f -> BeanUtils.copyProperties(vo, dao, f));
            return dao;
        };
    }
*/
}
