package com.example.faikyoussef.repository;

import com.example.faikyoussef.entity.ERole;
import com.example.faikyoussef.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
