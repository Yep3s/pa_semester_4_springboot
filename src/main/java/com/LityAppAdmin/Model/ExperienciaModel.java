package com.LityAppAdmin.Model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "experiencias")
public class ExperienciaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;

    private String parrafo;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public void setFechaDeCreacion(LocalDateTime fechaDeCreacion) {
        this.fechaDeCreacion = fechaDeCreacion;
    }

    @Override
    public String toString() {
        return "ExperienciaModel{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", parrafo='" + parrafo + '\'' +
                ", correoAdministrador='" + correoAdministrador + '\'' +
                ", fechaDeCreacion=" + fechaDeCreacion +
                '}';
    }

}
