package com.zuora.usagedatamapper.model.configs.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ApiModel(description = "A mappings wrapper object will be send as a response by /mappings Api")
public class Mappings {

    @ApiModelProperty(position = 1,
                        allowEmptyValue = true)
    private List<Mapping> data;
    private int currentSegmentSize;
    @Builder.Default
    private int totalPages = 0;
    private long totalSize;

}
