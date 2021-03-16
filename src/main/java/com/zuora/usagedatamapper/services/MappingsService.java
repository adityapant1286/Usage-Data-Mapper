package com.zuora.usagedatamapper.services;

import com.zuora.usagedatamapper.errorhandling.exceptions.MappingRuntimeException;
import com.zuora.usagedatamapper.model.configs.vo.Mapping;
import com.zuora.usagedatamapper.model.configs.vo.Mappings;

import java.util.Collection;
import java.util.UUID;

public interface MappingsService {

    Mappings findById(UUID id);
    Mappings findAllByInstanceConfigId(UUID instanceConfigId,
                                       int pageNo, int size,
                                       String sortBy, String sortOrder);

    Mappings createMappings(Collection<Mapping> mappings);
//    Mappings createMapping(Mapping mapping);

    Mappings updateMappingById(UUID id, Mapping mapping);
    Mappings updateMappingsByInstanceConfigId(UUID instanceConfigId, Collection<Mapping> mappings);

    void deleteMappingById(UUID id) throws MappingRuntimeException;
    void deleteMappingsByInstanceConfigId(UUID instanceConfigId) throws MappingRuntimeException;

}
