package com.mycompany.sistema_cinemaseat;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class Asiento4D extends Asiento {

    public Asiento4D(double precio) {
        super(precio);
    }

    @Override
    public String getTipo() {
        return "4D";
    }
}
