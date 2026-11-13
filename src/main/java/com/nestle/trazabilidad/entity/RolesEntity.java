package com.nestle.trazabilidad.entity;

import com.nestle.trazabilidad.enums.Estados;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static com.nestle.trazabilidad.enums.Estados.ACTIVO;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "roles")
public class RolesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Integer idRol;
    @Column(name = "nombre", length = 60, nullable = false)
    private String nombre;
    @Column(name = "descripcion", length = 200, nullable = false)
    private String descripcion;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    @Builder.Default
    private Estados estado = ACTIVO;

    @OneToMany(mappedBy = "rol", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RolesPermisosEntity> permisos;

    @OneToMany(mappedBy = "rol", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UsuariosEntity> usuarios = new ArrayList<>();

}
