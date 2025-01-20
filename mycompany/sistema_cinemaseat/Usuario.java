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
    private String correo;     // Ej: "carlos@gmail.com"
    private int contrasena;    // Ej: 1234

    public Usuario(String user, String nombre, String correo, int contrasena) {
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getContrasena() {
        return contrasena;
    }

    public void setContrasena(int contrasena) {
        this.contrasena = contrasena;
    }
}
