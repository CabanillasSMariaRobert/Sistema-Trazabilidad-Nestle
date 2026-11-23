package com.nestle.trazabilidad.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ControlesCalidad")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ControlesCalidadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_control")
    private Integer idControl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_lote", nullable = false)
    private LotesEntity lote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioEntity usuario;

    @Column(name = "fecha_control")
    private LocalDateTime fechaControl;

    @Column(name = "parametro", length = 100)
    private String parametro;

    @Column(name = "valor_obtenido", length = 100)
    private String valorObtenido;

    @Column(name = "resultado", length = 30)
    private String resultado;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
}