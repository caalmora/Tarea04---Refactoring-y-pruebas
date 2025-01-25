package com.mycompany.sistema_cinemaseat;

public class Correo {

    private String direccion;

    public Correo(String direccion) {
        if (direccion == null || !direccion.contains("@")) {
            throw new IllegalArgumentException("Correo no válido");
        }
        this.direccion = direccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public boolean esAdministrador() {
        return direccion.toLowerCase().contains("admin");
    }

    @Override
    public String toString() {
        return direccion;
    }
}
