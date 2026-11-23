package com.nestle.trazabilidad.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Desviaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DesviacionesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_desviacion")
    private Integer idDesviacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_lote", nullable = false)
    private LotesEntity lote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_control")
    private ControlesCalidadEntity control;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioEntity usuario;

    @Column(name = "fecha_deteccion")
    private LocalDateTime fechaDeteccion;

    @Column(name = "fuente", length = 100)
    private String fuente;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "severidad", length = 20)
    private String severidad;

    @Column(name = "estado", length = 30)
    private String estado;
}