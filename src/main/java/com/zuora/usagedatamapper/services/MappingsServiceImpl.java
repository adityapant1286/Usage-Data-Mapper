package com.zuora.usagedatamapper.services;

import com.zuora.usagedatamapper.errorhandling.exceptions.MappingRuntimeException;
import com.zuora.usagedatamapper.model.configs.dao.MappingDao;
import com.zuora.usagedatamapper.model.configs.vo.Mapping;
import com.zuora.usagedatamapper.model.configs.vo.Mappings;
import com.zuora.usagedatamapper.repositories.configs.MappingRepository;
import com.zuora.usagedatamapper.transformers.Transformer;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.zuora.usagedatamapper.util.AppUtil.commonExclusionFields;
import static com.zuora.usagedatamapper.util.AppUtil.formatStr;
import static com.zuora.usagedatamapper.util.Constants.FAILED_TO_DELETE;
import static com.zuora.usagedatamapper.util.Constants.FAILED_TO_SAVE;
import static com.zuora.usagedatamapper.util.Constants.RECORD_NOT_FOUND_BY;

@Service(value = "defaultMappingsService")
public class MappingsServiceImpl implements MappingsService {

    private final MappingRepository mappingRepository;

    public MappingsServiceImpl(MappingRepository mappingRepository) {
        this.mappingRepository = mappingRepository;
    }

    @Override
    public Mappings findById(UUID id) {
        MappingDao dao = mappingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(formatStr(RECORD_NOT_FOUND_BY,
                        "Mapping", "id=" + id)));

        return Mappings.builder()
                        .currentSegmentSize(1)
                        .totalPages(1)
                        .totalSize(1)
                        .data(toVos().transform(List.of(dao)))
                        .build();
    }

    @Override
    public Mappings findAllByInstanceConfigId(UUID instanceConfigId,
                                              int pageNo, int size,
                                              String sortBy, String sortOrder) {

        final Sort sort = buildSort(sortBy, sortOrder);
        final Mappings.MappingsBuilder mappingsBuilder = Mappings.builder();

        if (size < 0) { // fetch all records
            List<MappingDao> daos = mappingRepository.findAllByInstanceConfigId(instanceConfigId, sort)
                                    .orElseThrow(() -> new IllegalArgumentException(formatStr(RECORD_NOT_FOUND_BY,
                                            "Mapping", "configId=" + instanceConfigId)));

            return mappingsBuilder.data(toVos().transform(daos))
                                .totalSize(daos.size())
                                .currentSegmentSize(daos.size())
                                .build();
        }

        Page<MappingDao> page = fetchPageData(instanceConfigId, pageNo, size, sort);

        return mappingsBuilder.data(toVos().transform(page.getContent()))
                            .totalPages(page.getTotalPages())
                            .totalSize(page.getTotalElements())
                            .currentSegmentSize(page.getNumberOfElements())
                            .build();
    }

    @Override
    public Mappings createMappings(Collection<Mapping> mappings) {
        List<MappingDao> daos = toDaos().transform(mappings);
        List<MappingDao> saved = Optional.of(mappingRepository.saveAll(daos))
                                            .orElseThrow(() -> new MappingRuntimeException(formatStr(FAILED_TO_SAVE,
                                                    "Mappings")));

        return Mappings.builder()
                        .data(toVos().transform(saved))
                        .totalSize(saved.size())
                        .currentSegmentSize(saved.size())
                        .build();
    }

/*
    @Override
    public Mappings createMapping(Mapping mapping) {

        final MappingDao dao = toDao().transform(mapping);
        final MappingDao saved = Optional.of(mappingRepository.save(dao))
                                            .orElseThrow(() -> new MappingException(formatStr(FAILED_TO_SAVE,
                                                    "Mappings")));

        return Mappings.builder()
                    .data(List.of(toVo().transform(saved)))
                    .currentSegmentSize(1)
                    .totalSize(1)
                    .totalPages(1)
                    .build();
    }
*/

    @Override
    public Mappings updateMappingById(UUID id, Mapping mapping) {
        final MappingDao dao = toDao().transform(mapping);
        dao.setId(id);

        final MappingDao saved = Optional.of(mappingRepository.save(dao))
                                            .orElseThrow(() -> new MappingRuntimeException(formatStr(FAILED_TO_SAVE,
                                                    "Mappings")));

        return Mappings.builder()
                .data(List.of(toVo().transform(saved)))
                .currentSegmentSize(1)
                .totalSize(1)
                .build();
    }

    @Override
    public Mappings updateMappingsByInstanceConfigId(UUID instanceConfigId, Collection<Mapping> mappings) {

        final Sort sort = buildSort("fromFieldName", "A");
        // fetch
        final List<MappingDao> daos = mappingRepository.findAllByInstanceConfigId(instanceConfigId, sort)
                                        .orElseThrow(() -> new IllegalArgumentException(formatStr(RECORD_NOT_FOUND_BY,
                                                "Mappings", "configId=" + instanceConfigId)));
        // sort VOs
        final List<Mapping> sorted = mappings.stream()
                                        .sorted(Comparator.comparing(Mapping::getFromFieldName))
                                        .collect(Collectors.toList());

        // copy data from VOs to DAOs
        for(int i = 0; i < sorted.size(); i++) {
            BeanUtils.copyProperties(sorted.get(i), daos.get(i), commonExclusionFields());
        }

        // save mappings
        List<MappingDao> saved = Optional.of(mappingRepository.saveAll(daos))
                                        .orElseThrow(() -> new MappingRuntimeException(formatStr(FAILED_TO_SAVE,
                                                "Mappings")));

        return Mappings.builder()
                        .data(toVos().transform(saved))
                        .currentSegmentSize(saved.size())
                        .totalSize(saved.size())
                        .build();
    }

    @Override
    public void deleteMappingById(UUID id) throws MappingRuntimeException {
//        final MappingDao dao = mappingRepository.findById(id)
//                                        .orElseThrow(() -> new IllegalArgumentException(formatStr(RECORD_NOT_FOUND_BY,
//                                                "Mapping", "id=" + id)));
        try {
            mappingRepository.deleteById(id);
        } catch (Exception ex) {
            throw new MappingRuntimeException(formatStr(FAILED_TO_DELETE,"Mapping", "id=" + id));
        }
    }

    @Override
    public void deleteMappingsByInstanceConfigId(UUID instanceConfigId) throws MappingRuntimeException {

        final List<MappingDao> daos = mappingRepository.findAllByInstanceConfigId(instanceConfigId,
                                                                    buildSort("fromFieldName", "A"))
                                            .orElseThrow(() -> new IllegalArgumentException(formatStr(RECORD_NOT_FOUND_BY,
                                                    "Mapping", "configId=" + instanceConfigId)));

        try {
            mappingRepository.deleteInBatch(daos);
        } catch (Exception ex) {
            throw new MappingRuntimeException(formatStr(FAILED_TO_DELETE,"Mappings", "size=" + daos.size()));
        }
    }

    /*--------------------- Private functions -----------------------*/

    private Page<MappingDao> fetchPageData(UUID configId,
                                           int pageNo, int size,
                                           Sort sort) {

        return mappingRepository.findAllByInstanceConfigId(configId, PageRequest.of(pageNo, size, sort))
                                .orElseThrow(() -> new IllegalArgumentException(formatStr(RECORD_NOT_FOUND_BY,
                                "Mapping", "configId=" + configId)));
    }

    private static Transformer<MappingDao, Mapping> toVo() {
        return (dao) -> {
            Mapping vo = new Mapping();
            BeanUtils.copyProperties(dao, vo, Mapping.class);
            return vo;
        };
    }

    private static Transformer<Mapping, MappingDao> toDao() {
        return (vo) -> {
            MappingDao dao = new MappingDao();
            BeanUtils.copyProperties(vo, dao, Mapping.class);
            return dao;
        };
    }

    private static Transformer<Collection<MappingDao>, List<Mapping>> toVos() {
        return (daos) -> daos.stream()
                            .map(dao -> toVo().transform(dao))
                            .collect(Collectors.toList());
    }

    private static Transformer<Collection<Mapping>, List<MappingDao>> toDaos() {
        return (vos) -> vos.stream()
                            .map(vo -> toDao().transform(vo))
                            .collect(Collectors.toList());
    }

    private static Sort buildSort(String sortBy, String sortOrder) {
        Sort sort = Sort.by(sortBy.trim()
                .replace(" ", "")
                .replace("  ", "")
                .split(","));

        return "A".equalsIgnoreCase(sortOrder) ? sort.ascending() : sort.descending();
    }

}
