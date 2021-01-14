package com.carlos.beautysalon.backend.utils;

import java.util.Date;

public class Utilidades {

    //Constantes campos tabla usuario
    public static final String TABLA_USUARIO = "usuario";
    public static final String CAMPO_ID_EMAIL = "correo_electronico";
    public static final String CAMPO_PASSWORD = "password";
    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_APELLIDO = "apellido";
    public static final String CAMPO_FEC_NAC = "fecha_de_nacimiento";
    public static final String CAMPO_TELEFONO = "telefono";

    public static final String CREAR_TABLA_USUARIO = "CREATE TABLE " + TABLA_USUARIO + " (" +
            CAMPO_ID_EMAIL + " TEXT UNIQUE PRIMARY KEY, " +
            CAMPO_PASSWORD + " TEXT, " +
            CAMPO_NOMBRE + " TEXT, " +
            CAMPO_APELLIDO + " TEXT, " +
            CAMPO_FEC_NAC + " TEXT, " +
            CAMPO_TELEFONO + " TEXT)";

    // Constantes campos tabla citas
    public static final String TABLA_CITAS = "citas";
    public static final String CAMPO_TIPO_SESION = "tipo_sesión";
    public static final String CAMPO_FECHA_CITA = "fecha_cita";
    public static final String CAMPO_FECHA_AGENDA = "fecha_agenda";

    public static final String CREAR_TABLA_CITAS = "CREATE TABLE " + TABLA_CITAS + " (" +
            CAMPO_ID_EMAIL + " TEXT, " +
            CAMPO_TIPO_SESION + " TEXT, " +
            CAMPO_FECHA_CITA + " TEXT UNIQUE, " +
            CAMPO_FECHA_AGENDA + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
            " FOREIGN KEY (" + CAMPO_ID_EMAIL + ") REFERENCES " + TABLA_USUARIO + "(" + CAMPO_ID_EMAIL + "));";

}