package com.zuora.usagedatamapper.repositories;

import com.zuora.usagedatamapper.model.configs.dao.AuthoritiesDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorityRepository extends JpaRepository<AuthoritiesDao, UUID> {

    Optional<List<AuthoritiesDao>> findAllByUsername(String username);
}
