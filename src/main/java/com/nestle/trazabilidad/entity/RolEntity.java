package com.nestle.trazabilidad.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
public class RolEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Integer idRol;
    @Column(name = "nombre", length = 150, nullable = false)
    private String nombre;
    @Column(name = "descripcion", length = 150, nullable = false)
    private String descripcion;
    @Column(name = "estado", nullable = false)
    private boolean estado;

    @OneToMany(mappedBy = "rol", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UsuarioEntity> usuarios = new ArrayList<>();

}
