package com.nestle.trazabilidad.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "DetallesRetiro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetallesRetiroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_retiro")
    private Integer idDetalleRetiro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_retiro", nullable = false)
    private RetirosEntity retiro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_distribucion", nullable = false)
    private DistribucionesEntity distribucion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioEntity usuario;

    @Column(name = "fecha_recuperacion")
    private LocalDateTime fechaRecuperacion;

    @Column(name = "cantidad_recuperada")
    private Integer cantidadRecuperada;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
}