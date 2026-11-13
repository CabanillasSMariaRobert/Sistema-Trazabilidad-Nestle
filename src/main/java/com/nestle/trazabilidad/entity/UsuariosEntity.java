package com.nestle.trazabilidad.entity;

import com.nestle.trazabilidad.enums.Estados;
import jakarta.persistence.*;
import lombok.*;

import static com.nestle.trazabilidad.enums.Estados.ACTIVO;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name="usuarios")
public class UsuariosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Column(name="nombres", length = 80, nullable = false)
    private String nombres;
    @Column(name="apellidos", length = 80, nullable = false)
    private String apellidos;
    @Column(name="user_name", length = 40, nullable = false)
    private String userName;
    @Column(name="password_hash", length = 255, nullable = false)
    private String passwordHash;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    @Builder.Default
    private Estados estado = ACTIVO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol", nullable = false)
    private RolesEntity rol;
}
