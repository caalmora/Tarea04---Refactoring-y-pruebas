package com.mycompany.sistema_cinemaseat;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author User
 */

public class Sala {

    private String nombre;
    private Asiento[][] asientos;

    public Sala(String nombre, int filas, int columnas) {
        this.nombre = nombre;
        this.asientos = new Asiento[filas][columnas];
    }

    public String getNombre() {
        return nombre;
    }

    public Asiento[][] getAsientos() {
        return asientos;
    }

    public void inicializarAsientos(double precioEstandar, double precioVIP, double precio4D) {
        int filas = asientos.length;
        int columnas = asientos[0].length;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (i < filas / 3) {
                    asientos[i][j] = new Asiento4D(precio4D);
                } else if (i < 2 * filas / 3) {
                    asientos[i][j] = new AsientoVIP(precioVIP);
                } else {
                    asientos[i][j] = new AsientoEstandar(precioEstandar);
                }
            }
        }
    }

    public void mostrarMapa() {
        System.out.println("=== Mapa - " + nombre + " ===");
        System.out.print("      ");
        for (int j = 1; j <= asientos[0].length; j++) {
            System.out.print("[" + j + "]");
        }
        System.out.println();
        for (int i = 0; i < asientos.length; i++) {
            System.out.print("[" + (i + 1) + "] ");
            for (int j = 0; j < asientos[i].length; j++) {
                String simbolo = "";
                switch (asientos[i][j].getEstado()) {
                    case DISPONIBLE -> {
                        String tipo = asientos[i][j].getTipo();
                        if (tipo.equals("VIP")) {
                            simbolo = "V";
                        } else if (tipo.equals("4D")) {
                            simbolo = "D";
                        } else {
                            simbolo = "E";
                        }
                    }
                    case EN_PROCESO_COMPRA -> simbolo = "/";
                    case RESERVADO -> simbolo = "X";
                }
                System.out.print("[" + simbolo + "] ");
            }
        }
        System.out.println("\n===== Pantalla =====\n");
        System.out.println();
        System.out.println("Leyenda:\nX = Ocupado, / = En proceso de compra\n");
        System.out.println("VIP -> V: Mayor espacio y comodidad  |  4D -> D: Efectos especiales (movimiento y vibracion)  |  Estandar -> E: Asientos normales");
    }

    public double seleccionarAsiento(int fila, int columna) throws CinemaException {
        if (fila >= 0 && fila < asientos.length && columna >= 0 && columna < asientos[0].length) {
            Asiento asiento = asientos[fila][columna];
            if (asiento.getEstado() == EstadoAsiento.DISPONIBLE) {
                asiento.setEstado(EstadoAsiento.EN_PROCESO_COMPRA);
                return asiento.getPrecio();
            } else {
                throw new CinemaException("\nEl asiento ya esta ocupado o en proceso de compra\n");
            }
        } else {
            throw new CinemaException("\nCoordenadas invalidas para el asiento\n");
        }
    }

    public void liberarAsiento(int fila, int columna) throws CinemaException {
        if (fila >= 0 && fila < asientos.length && columna >= 0 && columna < asientos[0].length) {
            Asiento asiento = asientos[fila][columna];
            if (asiento.getEstado() == EstadoAsiento.EN_PROCESO_COMPRA
                    || asiento.getEstado() == EstadoAsiento.RESERVADO) {
                asiento.setEstado(EstadoAsiento.DISPONIBLE);
                System.out.println("\nEl asiento ha sido liberado\n");
            }
        } else {
            throw new CinemaException("\nCoordenadas invalidas para el asiento\n");
        }
    }
}
