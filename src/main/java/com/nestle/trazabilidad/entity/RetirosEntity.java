package com.nestle.trazabilidad.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Retiros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RetirosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_retiro")
    private Integer idRetiro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_alerta_lote", nullable = false)
    private AlertasLotesEntity alertaLote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioEntity usuario;

    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_cierre")
    private LocalDateTime fechaCierre;

    @Column(name = "estado", length = 30)
    private String estado;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
}