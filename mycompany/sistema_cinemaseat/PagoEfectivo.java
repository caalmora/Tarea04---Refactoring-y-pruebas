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
public class PagoEfectivo extends MetodoPago {

    @Override
    public boolean procesarPago(double cantidad, Scanner scanner) throws CinemaException {
        boolean pagoExitoso = false;
        System.out.println("\n1 ---> Ingresar monto");
        System.out.println("2 ---> Salir");
        System.out.print("Seleccione una opcion numerica (Ej:1): ");
        while (!pagoExitoso) {
            try {
                int opcion = Integer.parseInt(scanner.nextLine());
                if (opcion == 1) {
                    System.out.print("\nIngrese el monto a pagar: ");
                    double montoPagado = Double.parseDouble(scanner.nextLine());
                    if (montoPagado >= cantidad) {
                        double vuelto = montoPagado - cantidad;
                        System.out.println("\nPago en efectivo exitoso. Vuelto: $" + vuelto);
                        pagoExitoso = true;
                        return true;
                    } else {
                        System.out.println("\nMonto insuficiente. Intente nuevamente\n");
                    }
                } else if (opcion == 2) {
                    System.out.println("\nSaliste del proceso de pago\n");
                    return false;
                }
            } catch (NumberFormatException e) {
                System.out.println("\nEntrada invalida\n");
            }
        }
        return false;
    }
}
