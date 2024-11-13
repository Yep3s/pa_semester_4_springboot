package com.LityAppAdmin.Model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name = "guias_rapidas1")
public class GuiaRapidaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoDeGuia;
    private String titulo;
    private String parrafo;
    private String image;


    // Campo para almacenar solo el correo del administrador
    private String correoAdministrador;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime fechaDeCreacion;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    @Override
    public String toString() {
        return "GuiaRapidaModel{" +
                "id=" + id +
                ", tipoDeGuia='" + tipoDeGuia + '\'' +
                ", titulo='" + titulo + '\'' +
                ", parrafo='" + parrafo + '\'' +
                ", image='" + image + '\'' +
                ", correoAdministrador='" + correoAdministrador + '\'' +
                ", fechaDeCreacion=" + fechaDeCreacion +
                '}';
    }
}


