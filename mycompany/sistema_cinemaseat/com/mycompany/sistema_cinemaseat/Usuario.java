package com.mycompany.sistema_cinemaseat;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */

public abstract class Usuario {
    private String user; 
    private String nombre; 
    private Correo correo; 
    private Contrasena contrasena; 

    public Usuario(String user, String nombre, String correo, String contrasena) {
        this.user = user;
        this.nombre = nombre;
        this.correo = new Correo(correo);
        this.contrasena = new Contrasena(contrasena);
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

    public void setCorreo(String correo) {
        this.correo = new Correo(correo);
    }

    public Contrasena getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = new Contrasena(contrasena);
    }

    public abstract void manejarAcciones(ContextoAccion contexto) throws CinemaException;

}
