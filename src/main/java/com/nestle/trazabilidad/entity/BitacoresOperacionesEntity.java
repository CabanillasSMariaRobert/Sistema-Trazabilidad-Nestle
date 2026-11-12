package com.nestle.trazabilidad.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "BitacorasOperaciones")
public class BitacoresOperacionesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_operacion")
    private Integer idOperacion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioEntity usuario;
    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;
    @Column(name = "accion")
    private String accion;
    @Column(name = "entidad_afectada")
    private String entidadAfectada;
    @Column(name = "identificador_registro")
    private String identificadorRegistro;
    @Column(name = "valores_anteriores")
    private String valoresAnteriores;
    @Column(name = "valores_nuevos")
    private String valoresNuevos;
}
