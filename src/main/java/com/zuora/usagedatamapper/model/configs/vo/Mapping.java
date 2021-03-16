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
@ApiModel(description = "A mapping between two different systems (left and right).")
public class Mapping {

    @ApiModelProperty(notes = "A unique identifier of a mapping. " +
                            "A value for this field is required for PUT/update operation",
                        position = 1,
                        allowEmptyValue = true)
    private UUID id;

    @ApiModelProperty(notes = "A unique instanceConfig identifier for which this mapping is related to",
                        position = 2,
                        allowEmptyValue = true)
    private UUID configId;

    @ApiModelProperty(notes = "This config is classified under a category",
            position = 3,
            required = true,
            example = "SMS, Roaming, Data, Kilometers, Miles, Electricity")
    private String category;

    @ApiModelProperty(notes = "A unique fieldName of the left system which will be mapped with right usageFieldName.",
                        position = 3,
                        required = true)
    @NotBlank(message = "fromFieldName is required")
    private String fromFieldName;

    @ApiModelProperty(notes = "The datatype of fieldName of the left system.",
                        position = 4,
                        required = true)
    @NotBlank(message = "fromFieldDataType is required")
    private String fromFieldDataType;

    @ApiModelProperty(notes = "A Zuora usageFieldName (right system).",
                        position = 5,
                        required = true)
    @NotBlank(message = "usageFieldName is required")
    private String usageFieldName;

    @ApiModelProperty(notes = "A Zuora object name (right system).",
                        position = 6,
                        required = true)
    @NotBlank(message = "objectName is required")
    private String objectName;

    @ApiModelProperty(notes = "Record created date for auditing purpose.",
                        position = 7,
                        allowEmptyValue = true)
    private String createdDate;

    @ApiModelProperty(notes = "Record updated date for auditing purpose.",
                        position = 8,
                        allowEmptyValue = true)
    private String updatedDate;

}
