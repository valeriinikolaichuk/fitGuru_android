package com.fitguru.backend.auth.repository;

import org.springframework.stereotype.Repository;

import com.fitguru.backend.auth.entity.User;
import com.fitguru.backend.auth.entity.enums.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> 
{
    boolean existsByPhone(String phone);

    List<User> findByRole(Role role);

    Optional<User> findByPhone(String phone);
}
