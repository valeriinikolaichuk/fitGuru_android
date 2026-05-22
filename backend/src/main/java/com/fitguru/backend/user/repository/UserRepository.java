package com.fitguru.backend.user.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import java.util.List;

import com.fitguru.backend.user.entity.User;
import com.fitguru.backend.user.entity.enums.Role;

@Repository
public interface UserRepository extends JpaRepository<User, Long> 
{
    boolean existsByPhone(String phone);

    List<User> findByRole(Role role);

    Optional<User> findByPhone(String phone);
}
