package com.zuora.usagedatamapper.model.configs.dao;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MappingsDao {

    private List<MappingDao> data;
    private int currentSegmentSize;
    private int totalPages;
    private long totalSize;

}
