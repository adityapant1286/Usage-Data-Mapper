package com.zuora.usagedatamapper.controllers.configs;

import com.zuora.usagedatamapper.model.configs.vo.InstanceConfig;
import com.zuora.usagedatamapper.model.configs.vo.Mapping;
import com.zuora.usagedatamapper.model.configs.vo.Mappings;
import com.zuora.usagedatamapper.services.MappingsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
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
@RequestMapping(path = API_CURRENT_VERSION + "/Mappings")
@Api(value = "Instance mappings. Many mappings per tenant instance.")
public class MappingsController {

    @Qualifier("defaultMappingsService")
    private final MappingsService mappingsService;

    public MappingsController(MappingsService mappingsService) {
        this.mappingsService = mappingsService;
    }

    /*---------------------------- GET -----------------------------------*/

    @ApiOperation(value = "Get single mapping by id",
                    httpMethod = "GET",
                    response = ResponseEntity.class,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(path = "/{id}")
    public ResponseEntity<Mappings> getMappingById(
                                                    @ApiParam(required = true, value = "id")
                                                    @Valid @PathVariable(name = "id")
                                                            UUID id) {
        return ResponseEntity.ok(mappingsService.findById(id));
    }

    /**
     * Retrieves instance mappings by {@link InstanceConfig}{@code .id}.
     * Additionally user can provide request params to change the behaviour of the response.
     * <ol>
     *     <li>Provide {@code pageNo > 0} and {@code size > 0} to fetch data by page number
     *     and total size to be fetched per page</li>
     *     <li>Providing {@code size < 0} will fetch all mappings. This will ignore {@code pageNo}</li>
     *     <li>Provide field name(s) (<b>Case Sensitive</b>) in {@code sortBy} will sort the records by this field.
     *     Please provide multiple fields in comma separated format in order to sort by multiple fields</li>
     *     <li>Provide {@code sortOrder = A or D} will sort the records either by
     *     ascending or descending order respectively.</li>
     * </ol>
     *
     * @param instanceConfigId {@link InstanceConfig}{@code .id}
     * @param pageNo Any positive number to fetch records of this page number.
     *               Use this field with {@code size} to fetch number of records per page.
     *               {@code Default 1}
     * @param size Any positive number to fetch number of records.
     *             Use this field with {@code pageNo} to fetch number of records per page.
     *             Mentioning {@code size < 0} will fetch all records and
     *             this will ignore value mentioned for {@code pageNo}
     *             {@code Default 10}
     * @param sortBy Records will be sorted by the field value mentioned for {@code sortBy}.
     *               This can also sort multiple fields at once by providing a comma separated field names.
     *               {@code Default fromFieldName}
     * @param sortOrder Records will be sorted either by ascending or descending order.
     *                  Please provide value either {@code A or D}
     *                  {@code Default A}
     * @return A {@link Mappings} object containing information instance mappings.
     * The response contains:
     * <ul>
     *     <li>{@code data (array)}</li>
     *     <li>{@code currentSegmentSize (int)}</li>
     *     <li>{@code totalPages (int)}</li>
     *     <li>{@code totalSize (long)}</li>
     * </ul>
     *
     */
    @ApiOperation(value = "Get all mappings by instanceConfigId. "
                    + "This allows to retrieve values by page (default=1) and " +
                    "size (default=10, mention size < 0 to retrieve all). " +
                    "Provide a field name to sort (default=fromFieldName) or " +
                    "comma separated field names to sort by multiple fields. " +
                    "Also the records can be sorted in ascending (A) (default=A) or descending (D) order.",
                    httpMethod = "GET",
                    response = ResponseEntity.class,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(path = "/{instanceConfigId}/instance")
    public ResponseEntity<Mappings> getInstanceMappings(
                                                        @ApiParam(required = true, value = "instanceConfigId")
                                                        @PathVariable(name = "instanceConfigId")
                                                                UUID instanceConfigId,
                                                       @RequestParam(required = false, defaultValue = "1") int pageNo,
                                                       @RequestParam(required = false, defaultValue = "10") int size,
                                                       @RequestParam(required = false, defaultValue = "fromFieldName") String sortBy,
                                                       @RequestParam(required = false, defaultValue = "A") String sortOrder) {

        return ResponseEntity.ok(mappingsService.findAllByInstanceConfigId(instanceConfigId,
                                                                            pageNo, size,
                                                                            sortBy, sortOrder));
    }

//    @PostMapping()
//    public ResponseEntity<Mappings> createMapping(@Valid @RequestBody Mapping mapping) {
//        return ResponseEntity.status(HttpStatus.CREATED)
//                            .body(mappingsService.createMapping(mapping));
//    }

    /*---------------------------- POST -----------------------------------*/
    @ApiOperation(value = "Create a new list of mappings by instanceConfigId",
                    httpMethod = "POST",
                    response = ResponseEntity.class,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(path = "/{instanceConfigId}/instance")
    public ResponseEntity<Mappings> createInstanceMappings(
                                                            @ApiParam(required = true, value = "instanceConfigId")
                                                            @PathVariable(name = "instanceConfigId")
                                                                    UUID instanceConfigId,
                                                            @Valid @RequestBody List<Mapping> mappings) {

        mappings.forEach(m -> m.setConfigId(instanceConfigId));

        return ResponseEntity.status(HttpStatus.CREATED)
                            .body(mappingsService.createMappings(mappings));
    }

    /*---------------------------- PUT -----------------------------------*/
    @ApiOperation(value = "Update mapping by mapping id",
                    httpMethod = "PUT",
                    response = ResponseEntity.class,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PutMapping(path = "/{id}")
    public ResponseEntity<Mappings> updateInstanceMappingById(
                                                            @ApiParam(required = true, value = "id")
                                                            @Valid @PathVariable(name = "id")
                                                                    UUID id,
                                                            @Valid @RequestBody Mapping mapping) {

        return ResponseEntity.ok(mappingsService.updateMappingById(id, mapping));
    }

    @ApiOperation(value = "Update multiple mappings by instanceConfigId",
                    httpMethod = "PUT",
                    response = ResponseEntity.class,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PutMapping(path = "/{instanceConfigId}/instance")
    public ResponseEntity<Mappings> updateInstanceMappings(
                                                        @ApiParam(required = true, value = "instanceConfigId")
                                                        @PathVariable(name = "instanceConfigId")
                                                                UUID instanceConfigId,
                                                        @Valid @RequestBody List<Mapping> mappings) {

        return ResponseEntity.ok(mappingsService.updateMappingsByInstanceConfigId(instanceConfigId, mappings));
    }

    /*---------------------------- DELETE -----------------------------------*/

    @ApiOperation(value = "Delete instance mapping by id",
                    httpMethod = "DELETE",
                    response = ResponseEntity.class,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteInstanceMapping(
                                                        @ApiParam(required = true, value = "id")
                                                        @Valid @PathVariable(name = "id")
                                                                UUID id) {
        mappingsService.deleteMappingById(id);
        return ResponseEntity.ok("{ \"message\": \"Mapping deleted of id=" + id + "\" }");
    }

    @ApiOperation(value = "Delete all instance mappings by instanceConfigId",
                    httpMethod = "DELETE",
                    response = ResponseEntity.class,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @DeleteMapping(path = "/{instanceConfigId}/instance")
    public ResponseEntity<String> deleteAllInstanceMappings(
                                                            @ApiParam(required = true, value = "instanceConfigId")
                                                            @PathVariable(name = "instanceConfigId")
                                                                    UUID instanceConfigId) {
        mappingsService.deleteMappingsByInstanceConfigId(instanceConfigId);

        return ResponseEntity.ok("{ \"message\": \"All mappings deleted of instanceConfigId=" + instanceConfigId + "\" }");
    }

}
