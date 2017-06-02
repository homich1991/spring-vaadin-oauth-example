package com.homich.auth.repositories;

import com.homich.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);

    <T> List<T> findAllProjectedBy(Class<T> beanProjection);

    <T> T findByName(String name, Class<T> beanProjection);

    <T> List<T> findByNameLikeIgnoreCase(String name, Class<T> beanProjection);

    void deleteByName(String name);
}

