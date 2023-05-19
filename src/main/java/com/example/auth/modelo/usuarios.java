package com.example.auth.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="usuarios")
public class usuarios {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "last_name")
    private String last_name;
    @Column(name = "email")
    private String email;
    @Column(name="phone")
    private int phone;
    @Column(name = "password")
    private  String password;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "usuario_roles",joinColumns = @JoinColumn(name = "usuario_id",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "rol_id",referencedColumnName = "id_rol"))
    private List<roles> rol = new ArrayList<>();
}
