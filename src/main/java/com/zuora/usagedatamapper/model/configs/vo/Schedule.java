package com.zuora.usagedatamapper.model.configs.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ApiModel(description = "A background job schedule for an instance")
public class Schedule {

    @ApiModelProperty(notes = "A unique identifier of a schedule. " +
                            "A value for this field is required for PUT/update operation",
                        position = 1,
                        allowEmptyValue = true)
    private UUID id;

    @ApiModelProperty(notes = "A unique instanceConfig identifier for which this schedule is related to",
                        position = 2,
                        allowEmptyValue = true)
    private UUID configId;

    @ApiModelProperty(notes = "This config is classified under a category",
                        position = 3,
                        required = true,
                        example = "SMS, Roaming, Data, Kilometers, Miles, Electricity")
    private String category;

    @ApiModelProperty(notes = "A unique instanceConfig identifier for which this schedule is related to",
                        position = 4,
                        required = true)
    @NotBlank(message = "cronString is required")
    /*
     * <second> <minute> <hour> <day-of-month> <month> <day-of-week> <year>
     */
    private String cronString;


}
