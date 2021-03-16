package com.zuora.usagedatamapper.repositories.configs;

import com.zuora.usagedatamapper.model.configs.dao.MappingDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Repository
public interface MappingRepository extends JpaRepository<MappingDao, UUID> {

    Optional<Page<MappingDao>> findAllByInstanceConfigId(UUID configId, Pageable pageable);
    Optional<List<MappingDao>> findAllByInstanceConfigId(UUID configId, Sort sort);

}
