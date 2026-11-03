package com.nestle.trazabilidad.entity;

import jakarta.persistence.*;

@Entity
@Table(name="usuarios")
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name="nombres", length = 150, nullable = false)
    private String nombres;
    @Column(name="apellidos", length = 150, nullable = false)
    private String apellidos;
    @Column(name="user_name", length = 150, nullable = false)
    private String userName;
    @Column(name="password_hash", length = 255, nullable = false)
    private String passwordHash;
    @Column(name="estado", nullable = false)
    private boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol", nullable = false)
    private RolEntity rol;
}
