package com.w3bd3vm4n.ecommerce_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombre;
    @Column(nullable = false)
    @JsonIgnore
    private String contrasena;
    @Column(nullable = false)
    private boolean habilitado;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

}
