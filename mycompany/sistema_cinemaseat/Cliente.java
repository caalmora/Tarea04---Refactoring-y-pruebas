package com.mycompany.sistema_cinemaseat;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author User
 */

public class Cliente extends Usuario {

    private List<Reserva> reservas;
    private boolean compraPendiente;
    private Funcion funcionPendiente;
    private String horarioPendiente;
    private double totalPendiente;
    private List<Reserva> asientosPendientes;

    public Cliente(String user, String nombre, String correo, int contrasena) {
        super(user, nombre, correo, contrasena);
        this.reservas = new ArrayList<>();
        this.compraPendiente = false;
        this.asientosPendientes = new ArrayList<>();
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public boolean isCompraPendiente() {
        return compraPendiente;
    }

    public void setCompraPendiente(boolean compraPendiente) {
        this.compraPendiente = compraPendiente;
    }

    // Menú principal del Cliente
    public void manejarAcciones(
            Scanner scanner,List<Funcion> funciones,List<Usuario> usuarios,IPersistenciaUsuarios repoUsuarios
    ) throws CinemaException {
        System.out.println("\nBIENVENIDO/A " + getNombre().toUpperCase());
        // Si hay una compra pendiente, preguntar si desea reanudar
        if (compraPendiente) {
            System.out.print("\nTienes una compra pendiente. ¿Deseas reanudar el proceso? (S/N): ");
            System.out.println();
            String resp = scanner.nextLine().trim().toLowerCase();
            if (resp.equals("s")) {
                reanudarCompra(scanner);
            } else {
                liberarAsientosPendientes();
                System.out.println("\nCompra pendiente eliminada\n");
            }
        }
        boolean activo = true;
        while (activo) {
            System.out.println("\n--- Menu Cliente ---");
            System.out.println("1 ---> Ver Funciones");
            System.out.println("2 ---> Ver Reservas");
            System.out.println("3 ---> Eliminar Cuenta");
            System.out.println("4 ---> Salir");
            System.out.print("\nSeleccione una opcion nuemrica (Ej:1): ");
            try {
                int opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> { verFunciones(scanner, funciones);
                    }
                    case 2 -> { gestionarReservas(scanner);
                    }
                    case 3 -> {
                        eliminarCuenta(scanner, usuarios, repoUsuarios);
                        activo = false; // Al eliminar la cuenta, salimos del menú
                    }
                    case 4 -> { activo = false; }
                    default -> System.out.println("\nOpcion no valida\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nEntrada invalida. Ingrese un numero\n");
            }
        }
    }

    // Reanudar compra pendiente
    private void reanudarCompra(Scanner scanner) throws CinemaException {
        System.out.println("\nRetomando pago pendiente para " + funcionPendiente.getPelicula()
                + " - Horario: " + horarioPendiente + " - Total: $" + totalPendiente);
        System.out.println();
        boolean pagoCompletado = mostrarMenuPago(scanner, totalPendiente, true);
        if (pagoCompletado) {
            reservas.addAll(asientosPendientes);
            ArchivoCorreo.escribirCliente(
                    getCorreo(),
                    funcionPendiente.getPelicula() + " - " + horarioPendiente
                    + " - Total: $" + totalPendiente + " - Compra exitosa (reanudada)"
            );
            System.out.println("\nReserva exitosa (reanudada)\n");

            // Limpiamos datos de compra pendiente
            compraPendiente = false;
            funcionPendiente = null;
            horarioPendiente = null;
            totalPendiente = 0;
            asientosPendientes.clear();
        } else {
            liberarAsientosPendientes();
            System.out.println("\nProceso de pago pendiente o cancelado nuevamente\n");
        }
    }

    // Liberar asientos en proceso (compra pendiente)
    private void liberarAsientosPendientes() {
        if (funcionPendiente != null && horarioPendiente != null) {
            try {
                Sala sala = funcionPendiente.obtenerSala(horarioPendiente);
                for (Reserva r : asientosPendientes) {
                    sala.liberarAsiento(r.getFila() - 1, r.getColumna() - 1);
                }
            } catch (CinemaException e) {
                System.out.println("\nError al liberar asientos: " + e.getMessage());
                System.out.println();
            }
        }
        compraPendiente = false;funcionPendiente = null;
        horarioPendiente = null;totalPendiente = 0;asientosPendientes.clear();
    }

    // Ver Funciones
    private void verFunciones(Scanner scanner, List<Funcion> funciones) throws CinemaException {
        Map<String, Funcion> funcionesUnificadas = agruparFuncionesPorPelicula(funciones);
        if (funcionesUnificadas.isEmpty()) {
            System.out.println("\nNo hay funciones disponibles\n");
            return;
        }
        System.out.println("\n--- Cartelera ---");
        List<String> listaPeliculas = new ArrayList<>(funcionesUnificadas.keySet());
        for (int i = 0; i < listaPeliculas.size(); i++) {
            System.out.println((i + 1) + " ---> " + listaPeliculas.get(i));
        }
        System.out.print("\nSeleccione el numero de la pelicula que desea ver (Ej:1) : ");
        try {
            int opcion = Integer.parseInt(scanner.nextLine());
            if (opcion < 1 || opcion > listaPeliculas.size()) {
                System.out.println("\nPelicula invalida\n");
                return;
            }
            Funcion funcionSeleccionada = funcionesUnificadas.get(listaPeliculas.get(opcion - 1));
            System.out.println();
            funcionSeleccionada.mostrarHorarios();
            System.out.print("\nIngrese el horario de la funcion que desea ver (HH:mm-dd/MM/yyyy): ");
            String horario = scanner.nextLine();
            Sala sala;
            try {
                sala = funcionSeleccionada.obtenerSala(horario);
                System.out.println();
            } catch (CinemaException e) {
                System.out.println(e.getMessage());
                return;
            }
            sala.mostrarMapa();

            System.out.print("\nIngrese la cantidad de boletos a comprar (Ej:1): ");
            int cantidadAsientos = Integer.parseInt(scanner.nextLine());
            double total = 0;
            List<Reserva> nuevasReservas = new ArrayList<>();
            for (int i = 0; i < cantidadAsientos; i++) {
                System.out.println("\nSelección del Asiento " + (i + 1));
                System.out.print("Ingrese la fila: ");
                int fila = Integer.parseInt(scanner.nextLine()) - 1;
                System.out.print("Ingrese la columna: ");
                int columna = Integer.parseInt(scanner.nextLine()) - 1;
                double precio;
                try {
                    precio = sala.seleccionarAsiento(fila, columna);
                    System.out.println("\nAsiento seleccionado. Precio: $" + precio);
                    total += precio;
                    nuevasReservas.add(
                            new Reserva(funcionSeleccionada, sala.getNombre(), fila + 1, columna + 1, precio)
                    );
                } catch (CinemaException e) {
                    System.out.println(e.getMessage());
                    i--; // Volver a intentar si falló
                }
            }
            System.out.println("\n--- Resumen de Compra ---\n");
            for (Reserva r : nuevasReservas) {
                System.out.println(r);
            }
            System.out.println("Total a pagar: $" + total);
            System.out.println("\nMetodos de pago: \n1. Efectivo \n2. Tarjeta");
            System.out.print("\nSeleccione un metodo: ");
            int metodo = Integer.parseInt(scanner.nextLine());

            boolean pagoCompletado = false;
            switch (metodo) {
                case 1 -> pagoCompletado = mostrarMenuPago(scanner, total, false, new PagoEfectivo());
                case 2 -> pagoCompletado = mostrarMenuPago(scanner, total, false, new PagoTarjeta());
                default -> System.out.println("\nMetodo invalido; la compra quedara pendiente\n");
            }
            if (pagoCompletado) {
                reservas.addAll(nuevasReservas);
                ArchivoCorreo.escribirCliente(
                        getCorreo(),
                        funcionSeleccionada.getPelicula() + " - " + horario + " - " + sala.getNombre()
                        + " - Total: $" + total + " - Compra exitosa"
                );
                System.out.println("\nReserva exitosa\n");
            } else {
                // Se marca como compra pendiente
                compraPendiente = true;
                funcionPendiente = funcionSeleccionada;
                horarioPendiente = horario;
                totalPendiente = total;
                asientosPendientes.addAll(nuevasReservas);
                System.out.println("\nProceso de pago pendiente. Puedes reanudar el pago mas tarde\n");
            }

        } catch (NumberFormatException e) {
            System.out.println("\nEntrada invalida\n");
        }
    }

    // Método sobrecargado para reanudación
    private boolean mostrarMenuPago(Scanner scanner, double total, boolean esReanudacion)
            throws CinemaException {
        System.out.println("\nMetodos de pago: \n1 ---> Efectivo \n2 ---> Tarjeta\n");
        System.out.print("Seleccione un metodo (Ej:1): ");
        try {
            int metodo = Integer.parseInt(scanner.nextLine());
            switch (metodo) {
                case 1 -> {return mostrarMenuPago(scanner, total, esReanudacion, new PagoEfectivo());}
                case 2 -> {return mostrarMenuPago(scanner, total, esReanudacion, new PagoTarjeta());}
                default -> {
                    System.out.println("\nnMetodo invalido\n");
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("\nEntrada invalida\n");
            return false;
        }
    }

    // Método principal para procesar el pago
    private boolean mostrarMenuPago(
            Scanner scanner,double total,
            boolean esReanudacion,MetodoPago metodoPago
    ) throws CinemaException {
        if (metodoPago instanceof PagoEfectivo) {
            System.out.println("\n--- Pago en Efectivo ---\n");
        } else {
            System.out.println("\n--- Pago con Tarjeta ---\n");
        }
        ProcesoPago procesoPago = new ProcesoPago(metodoPago);
        procesoPago.iniciarProcesoPago(total, scanner);
        if (procesoPago.isPagoExitoso() && !procesoPago.isTiempoAgotado()) {
            return true;
        } else if (procesoPago.isTiempoAgotado()) {
            System.out.println("\nSe acabo el tiempo para ingresar el pago\n");
            System.out.print("\n¿Desea reintentar el pago? (S/N): ");
            String r = scanner.nextLine().trim().toLowerCase();
            if (r.equals("s")) {
                return mostrarMenuPago(scanner, total, esReanudacion, metodoPago);
            }
        } else {
            // Monto incorrecto o usuario salió voluntariamente
            System.out.println("\n¿Desea reintentar el pago? (S/N): ");
            String r = scanner.nextLine().trim().toLowerCase();
            if (r.equals("s")) {
                return mostrarMenuPago(scanner, total, esReanudacion, metodoPago);
            }
        }
        return false;
    }

    // Agrupar funciones por película
    private Map<String, Funcion> agruparFuncionesPorPelicula(List<Funcion> funciones) {
        Map<String, Funcion> funcionesUnificadas = new LinkedHashMap<>();
        for (Funcion f : funciones) {
            if (funcionesUnificadas.containsKey(f.getPelicula())) {
                Funcion yaExiste = funcionesUnificadas.get(f.getPelicula());
                yaExiste.getHorarios().putAll(f.getHorarios());
            } else {
                funcionesUnificadas.put(f.getPelicula(), f);
            }
        }
        return funcionesUnificadas;
    }

    // Ver Reservas
    private void gestionarReservas(Scanner s) {
        if (reservas.isEmpty()) {
            System.out.println("\nNo tienes reservas\n");
            return;
        }
        System.out.println("\n--- Mis Reservas ---\n");

        Map<String, List<Reserva>> reservasAgrupadas = reservas.stream()
                .collect(Collectors.groupingBy(Reserva::getPelicula));
        int cont = 1;
        for (String pelicula : reservasAgrupadas.keySet()) {
            double totalPelicula = reservasAgrupadas.get(pelicula)
                    .stream()
                    .mapToDouble(Reserva::getPrecio)
                    .sum();
            System.out.println(cont + " ---> " + pelicula + ", Total de la compra: $" + totalPelicula);
            for (Reserva r : reservasAgrupadas.get(pelicula)) {
                System.out.println("    - Sala: " + r.getSala()
                        + " Puesto: [" + r.getFila() + "," + r.getColumna() + "] Precio: $" + r.getPrecio());
            }
            cont++;
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    // Eliminar cuenta
    private void eliminarCuenta(Scanner scanner, List<Usuario> usuarios, IPersistenciaUsuarios repoUsuarios) {
        System.out.print("\n¿Esta seguro de que desea eliminar su cuenta? (S/N): ");
        System.out.println();
        String confirm = scanner.nextLine().trim().toLowerCase();
        if (confirm.equals("s")) {
            liberarAsientosPendientes();
            reservas.clear();
            usuarios.remove(this);
            repoUsuarios.guardarUsuarios(usuarios);
            Reporte reporte = new Reporte();
            reporte.notificarAdministradores(usuarios,"El cliente " + getUser() + " ha eliminado su cuenta."
            );
            System.out.println("\nCuenta eliminada con exito. Saliendo del sistema\n");
        } else {
            System.out.println("\nOperacion cancelada\n");
        }
    }
}
