package com.zuora.usagedatamapper.model.configs.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConfigurationWrapper {

    private InstanceConfigDao instanceConfigDao;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ScheduleDao scheduleDao;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private IORealmDao IORealmDao;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private MappingsDao mappingsDao;

}
