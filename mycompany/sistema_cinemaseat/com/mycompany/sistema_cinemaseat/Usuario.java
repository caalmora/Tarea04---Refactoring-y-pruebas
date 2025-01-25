package com.mycompany.sistema_cinemaseat;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author User
 */
public class Usuario {

    private String user;       // Ej: "carlosp"
    private String nombre;     // Ej: "Carlos Pérez"
    private Correo correo;     // Ej: "carlos@gmail.com"
    private Contrasena contrasena;    // Ej: 1234

    public Usuario(String user, String nombre, Correo correo, Contrasena contrasena) {
        this.user = user;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public String getUser() {
        return user;
    }

    public String getNombre() {
        return nombre;
    }

    public Correo getCorreo() {
        return correo;
    }

    public void setCorreo(Correo correo) {
        this.correo = correo;
    }

    public Contrasena getContrasena() {
        return contrasena;
    }

    public void setContrasena(Contrasena contrasena) {
        this.contrasena = contrasena;
    }
}
