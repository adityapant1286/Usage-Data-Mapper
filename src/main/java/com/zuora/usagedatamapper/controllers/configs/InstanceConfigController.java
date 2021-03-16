package com.zuora.usagedatamapper.controllers.configs;

import com.zuora.usagedatamapper.model.configs.vo.InstanceConfig;
import com.zuora.usagedatamapper.services.InstanceConfigService;
import com.zuora.usagedatamapper.util.AppUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

import static com.zuora.usagedatamapper.util.Constants.API_CURRENT_VERSION;

@Slf4j
@RestController()
@RequestMapping(path = API_CURRENT_VERSION + "/InstanceConfigs")
@Api(value = "Instance Configuration. One per tenant.")
public class InstanceConfigController {

    @Qualifier("defaultInstanceConfigService")
    private final InstanceConfigService instanceConfigService;

    public InstanceConfigController(InstanceConfigService instanceConfigService) {
        this.instanceConfigService = instanceConfigService;
    }

    /*---------------------------- GET -----------------------------------*/

    @ApiOperation(value = "Get Instance configurations by id",
                    httpMethod = "GET",
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(path = "/{id}")
    public ResponseEntity<InstanceConfig> getInstanceConfig(
                                                @ApiParam(required = true, value = "id")
                                                @PathVariable(name = "id")
                                                                UUID id) {

        log.debug(">--> id=" + id);

        return ResponseEntity.ok(instanceConfigService.findById(id));
    }

    @ApiOperation(value = "Get Instance configurations by name",
                    httpMethod = "GET",
                    response = ResponseEntity.class,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(path = "/{name}/name")
    public ResponseEntity<InstanceConfig> getInstanceConfigByName(
                                                @ApiParam(required = true, value = "name")
                                                @PathVariable(name = "name")
                                                                String name) {

        return ResponseEntity.ok(instanceConfigService.findByName(name));
    }

    @ApiOperation(value = "Get Instance configurations by tenantId and optionally by entityId for multi-entity tenant",
                    httpMethod = "GET",
                    response = ResponseEntity.class,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(path = "/{tenantId}/tenant")
    public ResponseEntity<InstanceConfig> getInstanceConfigByTenantIdAndEntityId(
                                                @ApiParam(required = true, type = "String", value = "tenantId")
                                                @PathVariable(name = "tenantId")
                                                        String tenantId,
                                                @RequestParam(name = "entityId", required = false)
                                                        String entityId) {

        return ResponseEntity.ok(instanceConfigService.findByTenantIdAndEntityId(tenantId, entityId));
    }

    /*---------------------------- POST -----------------------------------*/

    @ApiOperation(value = "Create a new Instance configurations",
                    httpMethod = "POST",
                    response = ResponseEntity.class,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping()
    public ResponseEntity<InstanceConfig> createInstanceConfig(
                                                @Valid @RequestBody
                                                        InstanceConfig instanceConfig) {
        log.debug("new instanceConfig");
        log.debug(instanceConfig.toString());

        return ResponseEntity.status(HttpStatus.CREATED)
                        .body(instanceConfigService.createInstanceConfig(instanceConfig));
    }

    /*---------------------------- PUT -----------------------------------*/

    @ApiOperation(value = "Update an Instance configurations by id",
                    httpMethod = "PUT",
                    response = ResponseEntity.class,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @PutMapping(path = "/{id}")
    public ResponseEntity<InstanceConfig> updateInstanceConfigById(
                                                @ApiParam(required = true, value = "id")
                                                @PathVariable(name = "id")
                                                        UUID id,
                                                @Valid @RequestBody
                                                        InstanceConfig instanceConfig) {

                                                                                        return ResponseEntity.ok(instanceConfigService.updateInstanceConfigById(id, instanceConfig));
                                                                                    }

    @ApiOperation(value = "Update an Instance configurations by name",
                    httpMethod = "PUT",
                    response = ResponseEntity.class,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @PutMapping(path = "/{instanceConfigName}/name")
    public ResponseEntity<InstanceConfig> updateInstanceConfigByName(
                                                @ApiParam(required = true, type = "String", value = "instanceConfigName")
                                                @PathVariable(name = "instanceConfigName")
                                                        String instanceConfigName,
                                                @Valid @RequestBody
                                                        InstanceConfig instanceConfig) {

                                            return ResponseEntity.ok(instanceConfigService.updateInstanceConfigByName(instanceConfigName, instanceConfig));
                                        }

    /*---------------------------- DELETE - Admin supported -----------------------------------*/

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteById(@PathVariable(name = "id") UUID id) {
        instanceConfigService.deleteById(id);
        return ResponseEntity.ok("{ \"message\": \"InstanceConfig deleted of id=" + id + "\" }");
    }

    @DeleteMapping(path = "/{name}/name")
    public ResponseEntity<String> deleteInstanceConfigByName(@PathVariable(name = "name") String name) {
        instanceConfigService.deleteByName(name);
        return ResponseEntity.ok("{ \"message\": \"InstanceConfig deleted of name=" + name + "\" }");
    }

    @DeleteMapping(path = "/{tenantId}/tenant")
    public ResponseEntity<String> deleteInstanceConfigByTenantId(
                                    @ApiParam(required = true, type = "String", value = "tenantId")
                                    @PathVariable(name = "tenantId")
                                            String tenantId,
                                    @RequestParam(name = "entityId", required = false)
                                            String entityId) {
        instanceConfigService.deleteByTenantId(tenantId, entityId);
        String msg = AppUtil.isEmpty(entityId) ? "" : ", entityId=" + entityId;
        return ResponseEntity.ok("{ \"message\": \"InstanceConfig deleted of tenantId=" + tenantId + msg + "\" }");
    }

}
