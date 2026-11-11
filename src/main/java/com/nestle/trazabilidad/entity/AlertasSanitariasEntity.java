package com.nestle.trazabilidad.entity;

import com.nestle.trazabilidad.enums.EstadosAlertas;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static com.nestle.trazabilidad.enums.EstadosAlertas.ACTIVA;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "AlertasSanitarias")
public class AlertasSanitariasEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alerta")
    private Integer idAlerta;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioEntity usuario;
    @Column(name = "nro_comunicado")
    private String nroComunicado;
    @Column(name = "fecha_emision")
    private LocalDate fechaEmision;
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
    @Column(name = "origen")
    private String origen;
    @Column(name = "descripcion")
    private String descripcion;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    @Builder.Default
    private EstadosAlertas estado = ACTIVA;
}
