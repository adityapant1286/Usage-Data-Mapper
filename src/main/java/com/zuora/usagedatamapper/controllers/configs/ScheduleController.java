package com.zuora.usagedatamapper.controllers.configs;

import com.zuora.usagedatamapper.model.configs.vo.Schedule;
import com.zuora.usagedatamapper.services.ScheduleService;
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
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.UUID;

import static com.zuora.usagedatamapper.util.Constants.API_CURRENT_VERSION;

@RestController()
@RequestMapping(path = API_CURRENT_VERSION + "/Schedules")
@Api(value = "Instance Schedule. One per tenant.")
public class ScheduleController {

    @Qualifier("defaultScheduleService")
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /*---------------------------- GET -----------------------------------*/
    @ApiOperation(value = "Get Schedule by id",
                    httpMethod = "GET",
                    response = ResponseEntity.class,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(path = "/{id}")
    public ResponseEntity<Schedule> getSchedule(@ApiParam(required = true, value = "id")
                                                    @Valid @PathVariable(name = "id")
                                                            UUID id) {
        return ResponseEntity.ok(scheduleService.findById(id));
    }

    @ApiOperation(value = "Get Schedule by instanceConfigId",
                    httpMethod = "GET",
                    response = ResponseEntity.class,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(path = "/{instanceConfigId}/instance")
    public ResponseEntity<Schedule> getInstanceSchedule(@ApiParam(required = true, value = "instanceConfigId")
                                                            @PathVariable(name = "instanceConfigId")
                                                                    UUID instanceConfigId) {
        return ResponseEntity.ok(scheduleService.findByInstanceConfigId(instanceConfigId));
    }

    /*---------------------------- POST -----------------------------------*/

    @ApiOperation(value = "Create a new Schedule by instanceConfigId",
                    httpMethod = "POST",
                    response = ResponseEntity.class,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(path = "/{instanceConfigId}")
    public ResponseEntity<Schedule> createSchedule(@ApiParam(required = true, value = "instanceConfigId")
                                                       @PathVariable(name = "instanceConfigId")
                                                               UUID instanceConfigId,
                                                       @Valid @RequestBody
                                                               Schedule schedule) {
        schedule.setConfigId(instanceConfigId);

        return ResponseEntity.status(HttpStatus.CREATED)
                            .body(scheduleService.createSchedule(schedule));
    }

    /*---------------------------- PUT -----------------------------------*/

    @ApiOperation(value = "Update a Schedule by id",
                    httpMethod = "PUT",
                    response = ResponseEntity.class,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @PutMapping(path = "/{id}")
    public ResponseEntity<Schedule> updateSchedule(@ApiParam(required = true, value = "id")
                                                       @Valid @PathVariable(name = "id")
                                                               UUID id,
                                                       @Valid @RequestBody
                                                               Schedule schedule) {

        return ResponseEntity.ok(scheduleService.updateScheduleById(id, schedule));
    }

    @ApiOperation(value = "Update a Schedule by instanceConfigId",
                    httpMethod = "PUT",
                    response = ResponseEntity.class,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @PutMapping(path = "/{instanceConfigId}/instance")
    public ResponseEntity<Schedule> updateScheduleByInstanceConfigId(
                                                    @ApiParam(required = true, value = "instanceConfigId")
                                                    @Valid @PathVariable(name = "instanceConfigId")
                                                            UUID instanceConfigId,
                                                    @Valid @RequestBody
                                                            Schedule schedule) {

        return ResponseEntity.ok(scheduleService.updateScheduleByInstanceConfigId(instanceConfigId, schedule));
    }

    /*---------------------------- DELETE - Admin supported -----------------------------------*/

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteById(@ApiParam(required = true, value = "id")
                                                 @Valid @PathVariable(name = "id")
                                                         UUID id) {
        scheduleService.deleteById(id);
        return ResponseEntity.ok("{ \"message\": \"Schedule deleted of id=" + id + "\" }");
    }

    @DeleteMapping(path = "/{instanceConfigId}/instance")
    public ResponseEntity<String> deleteByInstanceConfigId(@ApiParam(required = true, value = "instanceConfigId")
                                                               @Valid @PathVariable(name = "instanceConfigId")
                                                                       UUID instanceConfigId) {
        scheduleService.deleteByInstanceConfigId(instanceConfigId);
        return ResponseEntity.ok("{ \"message\": \"Schedule deleted of instanceConfigId=" + instanceConfigId + "\" }");
    }

}
