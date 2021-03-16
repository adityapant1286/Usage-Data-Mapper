package com.zuora.usagedatamapper.controllers.configs;

import com.zuora.usagedatamapper.model.configs.dao.ConfigurationWrapper;
import com.zuora.usagedatamapper.model.configs.dao.InstanceConfigDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.zuora.usagedatamapper.util.AppUtil.formatStr;
import static com.zuora.usagedatamapper.util.AppUtil.isEmpty;
import static com.zuora.usagedatamapper.util.Constants.EITHER_REQUIRED;
import static com.zuora.usagedatamapper.util.Constants.ENTITY_ID_REQUIRED;

@RestController("/configs")
@Slf4j
public class ConfigurationsWrapperController {

/*
    private final InstanceConfigController instanceConfigController;
    private final ScheduleController scheduleController;
    private final SftpConnectionController sftpConnectionController;
    private final MappingsController mappingsController;

    public ConfigurationsWrapperController(InstanceConfigController instanceConfigController,
                                           ScheduleController scheduleController,
                                           SftpConnectionController sftpConnectionController,
                                           MappingsController mappingsController) {
        this.instanceConfigController = instanceConfigController;
        this.scheduleController = scheduleController;
        this.sftpConnectionController = sftpConnectionController;
        this.mappingsController = mappingsController;
    }

    @GetMapping()
    public ConfigurationWrapper getConfigurations(@RequestParam(required = false) Long instanceConfigId,
                                                  @RequestParam(required = false) String instanceConfigName,
                                                  @RequestParam(required = false) String tenantId,
                                                  @RequestParam(required = false) String entityId) {

        InstanceConfigDao instanceConfigDao = fetchInstanceConfig(instanceConfigId, instanceConfigName,
                                                            tenantId, entityId);
        Long configId = instanceConfigDao.getId();

        return ConfigurationWrapper.builder()
                .instanceConfigDao(instanceConfigDao)
                .scheduleDao(scheduleController.getInstanceSchedule(configId))
                .sftpConnectionDao(sftpConnectionController.getInstanceSftpConnection(configId))
                .mappingsDao(mappingsController.getInstanceMappings(configId,
                                                        0, -1,
                                                        "fromFieldName", "A"))
                .build();
    }

    private InstanceConfigDao fetchInstanceConfig(Long id, String name, String tenant, String entity) {

        if (!isEmpty(id))
            return instanceConfigController.getInstanceConfig(id);

        if (!isEmpty(name))
            return instanceConfigController.getInstanceConfigByName(name);

        if (!isEmpty(tenant))
            return instanceConfigController.getInstanceConfigByTenantIdAndEntityId(tenant, entity);

        throw new IllegalArgumentException(formatStr(EITHER_REQUIRED,
                "instanceConfigId OR instanceConfigName OR tenantId." + ENTITY_ID_REQUIRED));
    }
*/

}
