package com.coppel.api.denuncias.api_denuncias.Entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;


@Entity
@Table(name = "Denuncia")
public class Denuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDenuncia;

    @Column(nullable = false)
    private int idEmpresa;

    @Column(nullable = false)
    private int idPais;

    @Column(nullable = false)
    private int idEstado;

    @Column(length = 10, nullable = false)
    private String centro;

    @Column(nullable = false)
    private String detalle;

    @Column(nullable = false)
    private LocalDate fechaHechos;

    @Column(length = 10, nullable = false, unique = true)
    private String folio;

    @Column(nullable = false, length = 255)
    private String contrasena;

    @Column(nullable = false)
    private boolean anonimato;

    @Column(length = 150)
    private String nombre;

    @Column(length = 150)
    private String correo;

    @Column(length = 15)
    private String telefono;

    @Column(name = "comentarios", columnDefinition = "TEXT[]")
    private String[] comentarios;  // Usando array de Strings




    @Column(nullable = false, length = 50)
    private String estatus = "Pendiente";

    // Getters y Setters
    public int getIdDenuncia() {
        return idDenuncia;
    }

    public void setIdDenuncia(int idDenuncia) {
        this.idDenuncia = idDenuncia;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public LocalDate getFechaHechos() {
        return fechaHechos;
    }

    public void setFechaHechos(LocalDate fechaHechos) {
        this.fechaHechos = fechaHechos;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean isAnonimato() {
        return anonimato;
    }

    public void setAnonimato(boolean anonimato) {
        this.anonimato = anonimato;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String[] getComentarios() {
        return comentarios;
    }

    public void setComentarios(String[] comentarios) {
        this.comentarios = comentarios;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    
}
