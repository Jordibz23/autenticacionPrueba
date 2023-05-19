package com.example.auth.repositories;

import com.example.auth.modelo.roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRolesRepository extends JpaRepository<roles,Integer> {

    Optional<roles> findByName(String name);
}

