package com.nestle.trazabilidad.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "AtencionesReclamos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AtencionesReclamosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_atencion")
    private Integer idAtencion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reclamo", nullable = false)
    private ReclamosEntity reclamo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioEntity usuario;

    @Column(name = "fecha_atencion")
    private LocalDateTime fechaAtencion;

    @Column(name = "accion_realizada", length = 200)
    private String accionRealizada;

    @Column(name = "respuesta", columnDefinition = "TEXT")
    private String respuesta;
}