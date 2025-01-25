/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_cinemaseat;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 *
 * @author User
 */

public class ProcesoPago {

    private MetodoPago metodoPago;
    private CuentaRegresivaGUI gui;
    private AtomicBoolean pagoExitoso;
    private boolean tiempoAgotado;

    public ProcesoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
        this.pagoExitoso = new AtomicBoolean(false);
        this.tiempoAgotado = false;
    }

    public boolean isPagoExitoso() {
        return pagoExitoso.get();
    }

    public boolean isTiempoAgotado() {
        return tiempoAgotado;
    }

    public void iniciarProcesoPago(double cantidad, Scanner scanner) throws CinemaException {
        gui = new CuentaRegresivaGUI();
        Thread cuentaThread = new Thread(() -> {
            int segundos = 15;
            while (segundos > 0 && !pagoExitoso.get()) {
                gui.actualizarTiempo(segundos);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.err.println("\nCuenta regresiva interrumpida\n");
                }
                segundos--;
            }
            if (!pagoExitoso.get()) {
                tiempoAgotado = true;
            }
            gui.cerrarVentana();
        });
        cuentaThread.start();

        boolean resultadoPago = metodoPago.procesarPago(cantidad, scanner);

        try {
            cuentaThread.join();
        } catch (InterruptedException e) {
            System.err.println("\nError esperando el fin del hilo de cuenta regresiva\n");
        }

        if (resultadoPago && !tiempoAgotado) {
            pagoExitoso.set(true);
        }
    }
}
