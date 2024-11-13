package com.LityAppAdmin.Model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "noticias")
public class NoticiaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoDeNoticia;
    private String titulo;
    private String parrafo;
    private String image;
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

    public String getTipoDeNoticia() {
        return tipoDeNoticia;
    }

    public void setTipoDeNoticia(String tipoDeNoticia) {
        this.tipoDeNoticia = tipoDeNoticia;
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

    public void setFechaDeCreacion(LocalDateTime fechaDeCreacion) {
        this.fechaDeCreacion = fechaDeCreacion;
    }

    @Override
    public String toString() {
        return "NoticiaModel{" +
                "id=" + id +
                ", tipoDeNoticia='" + tipoDeNoticia + '\'' +
                ", titulo='" + titulo + '\'' +
                ", parrafo='" + parrafo + '\'' +
                ", image='" + image + '\'' +
                ", correoAdministrador='" + correoAdministrador + '\'' +
                ", fechaDeCreacion=" + fechaDeCreacion +
                '}';
    }
}
