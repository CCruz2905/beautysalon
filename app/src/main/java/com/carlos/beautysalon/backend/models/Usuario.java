package com.carlos.beautysalon.backend.models;

import java.io.Serializable;

public class Usuario implements Serializable {

    private String email;
    private String password;
    private String nombre;
    private String apellido;
    private String fec_nac;
    private String telefono;

    public Usuario(String email, String password, String nombre, String apellido, String fec_nac, String telefono) {
        this.email = email;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fec_nac = fec_nac;
        this.telefono = telefono;
    }

    public Usuario() {
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getFec_nac() {
        return fec_nac;
    }

    public void setFec_nac(String fec_nac) {
        this.fec_nac = fec_nac;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}