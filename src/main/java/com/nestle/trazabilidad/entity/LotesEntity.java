package com.nestle.trazabilidad.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "Lotes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LotesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lote")
    private Integer idLote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    private ProductosEntity producto;

    @Column(name = "codigo_lote", length = 30)
    private String codigoLote;

    @Column(name = "fecha_fabricacion")
    private LocalDate fechaFabricacion;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @Column(name = "planta", length = 100)
    private String planta;

    @Column(name = "estado", length = 30)
    private String estado;
}