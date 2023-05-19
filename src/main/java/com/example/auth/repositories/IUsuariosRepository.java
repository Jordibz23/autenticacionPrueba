package com.example.auth.repositories;

import com.example.auth.modelo.usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUsuariosRepository extends JpaRepository<usuarios,Integer> {

    Optional<usuarios> findByName(String name);

    Boolean existsByName(String Name);
}
