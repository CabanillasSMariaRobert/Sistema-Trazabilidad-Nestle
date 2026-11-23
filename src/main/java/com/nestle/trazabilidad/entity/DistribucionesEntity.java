package com.nestle.trazabilidad.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Distribuciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DistribucionesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_distribucion")
    private Integer idDistribucion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_lote", nullable = false)
    private LotesEntity lote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_punto", nullable = false)
    private PuntosVentaEntity puntoVenta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioEntity usuario;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

    @Column(name = "cantidad_enviada")
    private Integer cantidadEnviada;

    @Column(name = "estado", length = 40)
    private String estado;
}