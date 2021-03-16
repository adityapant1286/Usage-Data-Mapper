package com.zuora.usagedatamapper.model.configs.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ApiModel(description = "InstanceConfig properties")
public class InstanceConfig {

    @ApiModelProperty(notes = "A unique numeric identifier. " +
                            "A value for this field is required for PUT/update operation.",
                        position = 1,
                        allowEmptyValue = true,
                        example = "1234567890")
    private UUID id;

    @ApiModelProperty(notes = "A unique tenant identifier.",
                        position = 2,
                        required = true)
    @NotBlank(message = "tenantId is required")
    private String tenantId; // required

    @ApiModelProperty(notes = "A unique entity identifier. Value for this field is conditional when working with multi-entity tenant.",
                        position = 3,
                        allowEmptyValue = true)
    private String entityId; // conditional required

    @ApiModelProperty(notes = "A unique name of an instance config. Minimum 3 characters required",
                        position = 4,
                        required = true)
    @NotBlank(message = "name is required")
    @Size(min = 3, message = "Minimum 3 characters required")
    private String name; // required

/*
    @ApiModelProperty(notes = "A valid file or folder location path from your local machine",
                        position = 5,
                        allowEmptyValue = true)
    private String filePath;

    @ApiModelProperty(notes = "A regular expression or a comma separated list of file extensions to be considered, in case " +
                            "the value for filePath is a folder location",
                        position = 6,
                        allowEmptyValue = true)
    private String inclusion;

    @ApiModelProperty(notes = "A regular expression or a comma separated list of file extensions to be excluded, in case " +
                                "the value for filePath is a folder location",
                        position = 7,
                        allowEmptyValue = true)
    private String exclusion;
*/

}
