package com.LityAppAdmin.Model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name = "guias_rapidas")
public class GuiaRapidaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoDeGuia;
    private String titulo;
    private String parrafo;

    // Campo para almacenar solo el correo del administrador
    private String correoAdministrador;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fechaDeCreacion;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoDeGuia() {
        return tipoDeGuia;
    }

    public void setTipoDeGuia(String tipoDeGuia) {
        this.tipoDeGuia = tipoDeGuia;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getParrafo() {
        return parrafo;
    }

    public void setParrafo(String parrafo) {
        this.parrafo = parrafo;
    }

    public String getCorreoAdministrador() {
        return correoAdministrador;
    }

    public void setCorreoAdministrador(String correoAdministrador) {
        this.correoAdministrador = correoAdministrador;
    }

    public LocalDateTime getFechaDeCreacion() {
        return fechaDeCreacion;
    }

}


