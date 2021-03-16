package com.zuora.usagedatamapper.controllers.configs;

import com.zuora.usagedatamapper.model.configs.vo.IORealm;
import com.zuora.usagedatamapper.services.IORealmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
import java.util.List;
import java.util.UUID;

import static com.zuora.usagedatamapper.util.Constants.API_CURRENT_VERSION;


@RestController()
@RequestMapping(path = API_CURRENT_VERSION + "/IO")
@Api(value = "Instance IO Realm details. One per tenant.")
public class IORealmController {

    @Qualifier("defaultIORealmService")
    private final IORealmService ioRealmService;

    public IORealmController(IORealmService ioRealmService) {
        this.ioRealmService = ioRealmService;
    }

    /*---------------------------- GET -----------------------------------*/

    @ApiOperation(value = "Get Input Output Realm by id",
                    httpMethod = "GET",
                    response = ResponseEntity.class,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(path = "/{id}")
    public ResponseEntity<IORealm> getIORealm(
                                                @ApiParam(required = true, value = "id")
                                                @Valid @PathVariable(name = "id")
                                                        UUID id) {

        return ResponseEntity.ok(ioRealmService.findIORealmById(id));
    }

    @ApiOperation(value = "Get Input Output Realm by instanceConfigId",
                    httpMethod = "GET",
                    response = ResponseEntity.class,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(path = "/{instanceConfigId}/instance")
    public ResponseEntity<IORealm> getIORealmInstanceConfigId(
                                                                @ApiParam(required = true, value = "instanceConfigId")
                                                                @PathVariable(name = "instanceConfigId")
                                                                        UUID instanceConfigId) {

        return ResponseEntity.ok(ioRealmService.findIORealmByInstanceConfigId(instanceConfigId));
    }

    @ApiOperation(value = "Get Input Output Realm by name",
                    httpMethod = "GET",
                    response = ResponseEntity.class,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(path = "/{name}")
    public ResponseEntity<List<IORealm>> getIORealmByName(
                                                        @ApiParam(required = true, value = "name")
                                                        @PathVariable(name = "name")
                                                                String name,
                                                        @RequestParam(required = false, name = "host")
                                                                String host) {

        return ResponseEntity.ok(ioRealmService.findIORealmDaoByNameOrHost(name, host));
    }

    /*---------------------------- POST -----------------------------------*/
    @ApiOperation(value = "Create a new Input Output Realm by instanceConfigId",
                    httpMethod = "POST",
                    response = ResponseEntity.class,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(path = "/{instanceConfigId}/instance")
    public ResponseEntity<IORealm> createIORealm(
                                                    @ApiParam(required = true, value = "instanceConfigId")
                                                    @PathVariable(name = "instanceConfigId")
                                                            UUID instanceConfigId,
                                                    @Valid @RequestBody
                                                            IORealm IORealm) {

        IORealm.setConfigId(instanceConfigId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ioRealmService.createIORealm(IORealm));
    }

    /*---------------------------- PUT -----------------------------------*/

    @ApiOperation(value = "Update a Input Output Realm by id",
                    httpMethod = "PUT",
                    response = ResponseEntity.class,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @PutMapping(path = "/{id}")
    public ResponseEntity<IORealm> updateIORealmById(
                                                        @ApiParam(required = true, value = "id")
                                                        @Valid @PathVariable(name = "id")
                                                                UUID id,
                                                        @Valid @RequestBody
                                                                IORealm IORealm) {

        return ResponseEntity.ok(ioRealmService.updateIORealmById(id, IORealm));
    }

    @ApiOperation(value = "Update a Input Output Realm by instanceConfigId",
                    httpMethod = "PUT",
                    response = ResponseEntity.class,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @PutMapping(path = "/{instanceConfigId}/instance")
    public ResponseEntity<IORealm> updateIORealmByInstanceConfigId(
                                                                @ApiParam(required = true, value = "instanceConfigId")
                                                                @PathVariable(name = "instanceConfigId")
                                                                        UUID instanceConfigId,
                                                                @Valid @RequestBody
                                                                        IORealm IORealm) {

        return ResponseEntity.ok(ioRealmService.updateIORealmByInstanceConfigId(instanceConfigId,
                IORealm));
    }

    @ApiOperation(value = "Update a Input Output Realm by name",
                    httpMethod = "PUT",
                    response = ResponseEntity.class,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @PutMapping(path = "/{name}")
    public ResponseEntity<IORealm> updateIORealmByName(
                                                        @ApiParam(required = true, value = "name")
                                                        @PathVariable(name = "name")
                                                                String name,
                                                        @Valid @RequestBody
                                                                IORealm IORealm) {
        return ResponseEntity.ok(ioRealmService.updateIORealmByName(name, IORealm));
    }

    /*---------------------------- DELETE - Admin supported -----------------------------------*/

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteById(@ApiParam(required = true, value = "id")
                                                 @Valid @PathVariable(name = "id")
                                                         UUID id) {
        ioRealmService.deleteById(id);
        return ResponseEntity.ok("{ \"message\": \"Input Output Realm deleted of id=" + id + "\" }");
    }

    @DeleteMapping(path = "/{instanceConfigId}/instance")
    public ResponseEntity<String> deleteByInstanceConfigId(@ApiParam(required = true, value = "instanceConfigId")
                                                               @Valid @PathVariable(name = "instanceConfigId")
                                                                       UUID instanceConfigId) {

        ioRealmService.deleteByInstanceConfigId(instanceConfigId);
        return ResponseEntity.ok("{ \"message\": \"Input Output Realm deleted of instanceConfigId=" + instanceConfigId + "\" }");
    }

    @DeleteMapping(path = "/{name}")
    public ResponseEntity<String> deleteByName(@ApiParam(required = true, value = "name")
                                                               @PathVariable(name = "name")
                                                                       String name) {
        ioRealmService.deleteByName(name);
        return ResponseEntity.ok("{ \"message\": \"Input Output Realm deleted of name=" + name + "\" }");
    }

}
