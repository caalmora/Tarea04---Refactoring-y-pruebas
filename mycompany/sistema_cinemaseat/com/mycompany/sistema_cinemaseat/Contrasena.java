package com.mycompany.sistema_cinemaseat;

public class Contrasena {

    private String hash;

    public Contrasena(String textoContrasena) {
        if (textoContrasena == null || textoContrasena.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
        this.hash = generarHash(textoContrasena);
    }

    public String generarHash(String textoContrasena) {
        return Integer.toString(textoContrasena.hashCode());
    }

    public boolean validar(String textoContrasena) {
        return this.hash.equals(generarHash(textoContrasena));
    }

    public String getHash() {
        return hash;
    }

    @Override
    public String toString() {
        return "Contrasena{protegida}";
    }
}

