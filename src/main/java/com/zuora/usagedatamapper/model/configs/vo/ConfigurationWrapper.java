package com.zuora.usagedatamapper.model.configs.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConfigurationWrapper {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private InstanceConfig instanceConfig;
    private Schedule schedule;
    private IORealm IORealm;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Mappings mappings;

}
