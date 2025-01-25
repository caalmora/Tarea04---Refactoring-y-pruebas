package com.mycompany.sistema_cinemaseat;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author User
 */

public class Reserva {

    private final Funcion funcion;
    private final String sala;
    private final int fila;
    private final int columna;
    private final double precio;

    public Reserva(Funcion funcion, String sala, int fila, int columna, double precio) {
        this.funcion = funcion;
        this.sala = sala;
        this.fila = fila;
        this.columna = columna;
        this.precio = precio;
    }

    public String getPelicula() {
        return funcion.getPelicula();
    }

    public String getSala() {
        return sala;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public double getPrecio() {
        return precio;
    }

    @Override
    public String toString() {
        return "Sala: " + sala
                + ", Puesto: [" + fila + "," + columna + "]"
                + ", Precio: $" + precio
                + ", Película: " + getPelicula();
    }
}