package com.zuora.usagedatamapper.repositories.configs;

import com.zuora.usagedatamapper.model.configs.dao.ScheduleDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Transactional
@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleDao, UUID> {
    Optional<ScheduleDao> findByInstanceConfigId(UUID configId);

    void deleteByInstanceConfigId(UUID instanceConfigId);
}
