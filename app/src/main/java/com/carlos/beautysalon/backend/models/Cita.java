package com.carlos.beautysalon.backend.models;

import java.io.Serializable;

public class Cita implements Serializable {

    private String email;
    private String sesion;
    private String fecha;
    private String hora;

    public Cita(String email, String sesion, String fecha, String hora) {
        this.email = email;
        this.sesion = sesion;
        this.fecha = fecha;
        this.hora = hora;
    }

    public Cita() {
    }

    public String getSesion() {
        return sesion;
    }

    public void setSesion(String sesion) {
        this.sesion = sesion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
