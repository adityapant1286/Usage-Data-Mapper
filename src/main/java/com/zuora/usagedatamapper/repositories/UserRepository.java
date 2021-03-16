package com.zuora.usagedatamapper.repositories;

import com.zuora.usagedatamapper.model.configs.dao.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDao, String> {

    Optional<UserDao> findByUsername(String username);
}
