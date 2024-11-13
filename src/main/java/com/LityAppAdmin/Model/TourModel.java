package com.LityAppAdmin.Model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tours")
public class TourModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreTour;
    private String descripcionTour;
    private String image;
    private String precio;
    private String agencia;
    private String contacto;

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

    public String getNombreTour() {
        return nombreTour;
    }

    public void setNombreTour(String nombreTour) {
        this.nombreTour = nombreTour;
    }

    public String getDescripcionTour() {
        return descripcionTour;
    }

    public void setDescripcionTour(String descripcionTour) {
        this.descripcionTour = descripcionTour;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
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
        return "TourModel{" +
                "id=" + id +
                ", nombreTour='" + nombreTour + '\'' +
                ", descripcionTour='" + descripcionTour + '\'' +
                ", image='" + image + '\'' +
                ", precio='" + precio + '\'' +
                ", agencia='" + agencia + '\'' +
                ", contacto='" + contacto + '\'' +
                ", correoAdministrador='" + correoAdministrador + '\'' +
                ", fechaDeCreacion=" + fechaDeCreacion +
                '}';
    }

}
