/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_cinemaseat;

import java.util.Scanner;

/**
 *
 * @author User
 */

public abstract class MetodoPago {

    public abstract boolean procesarPago(double cantidad, Scanner scanner) throws CinemaException;
}

