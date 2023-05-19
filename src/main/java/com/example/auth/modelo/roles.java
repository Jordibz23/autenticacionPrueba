package com.example.auth.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class roles {
    @Column(name = "id_rol")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_rol;
    @Column(name = "name")
    private String name;
}
