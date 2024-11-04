package com.LityAppAdmin.Model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "guias_rapidas")
public class GuiaRapidaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idGuia;

    private String tipoDeGuia;
    private String titulo;
    private String parrafo;
    private String imagen;

    @Column(name = "fecha_de_creacion",updatable = false)
    private Timestamp fechaDeCreacion;

    @ManyToOne
    @JoinColumn(name = "cedula")
    private AdministradorModel administradorModel;

    public int getIdGuia() {
        return idGuia;
    }

    public void setIdGuia(int idGuia) {
        this.idGuia = idGuia;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Timestamp getFechaDeCreacion() {
        return fechaDeCreacion;
    }

    public void setFechaDeCreacion(Timestamp fechaDeCreacion) {
        this.fechaDeCreacion = fechaDeCreacion;
    }

    public AdministradorModel getAdministrador() {
        return administradorModel;
    }

    public void setAdministrador(AdministradorModel administradorModel) {
        this.administradorModel = administradorModel;
    }

}
