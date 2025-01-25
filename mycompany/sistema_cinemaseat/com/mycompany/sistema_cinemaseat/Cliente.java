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

    public Cliente(String user, String nombre, String correo, String contrasena) {
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

    @Override
    public void manejarAcciones(ContextoAccion contexto) throws CinemaException {
        Scanner scanner = contexto.getScanner();
        if (compraPendiente) {
            System.out.print("\nTienes una compra pendiente. ¿Deseas reanudar el proceso? (S/N): ");
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
            System.out.print("\nSeleccione una opcion numerica (Ej:1): ");
            try {
                int opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1:
                        verFunciones(scanner, contexto);
                        break;
                    case 2:
                        gestionarReservas(scanner);
                        break;
                    case 3:
                        eliminarCuenta(scanner, contexto);
                        activo = false;
                        break;
                    case 4:
                        activo = false;
                        break;
                    default:
                        System.out.println("\nOpcion no valida\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nEntrada invalida. Ingrese un numero\n");
            }
        }
    }

    private void reanudarCompra(Scanner scanner) throws CinemaException {
        System.out.println("\nRetomando pago pendiente para " + funcionPendiente.getPelicula()
                + " - Horario: " + horarioPendiente + " - Total: $" + totalPendiente);
        boolean pagoCompletado = mostrarMenuPago(scanner, totalPendiente, true);
        if (pagoCompletado) {
            reservas.addAll(asientosPendientes);
            ArchivoCorreo.escribirNotificacion(getCorreo().getDireccion(), funcionPendiente.getPelicula() + " - "
                    + horarioPendiente + " - Total: $" + totalPendiente + " - Compra exitosa (reanudada)");
            System.out.println("\nReserva exitosa (reanudada)\n");
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

    private void liberarAsientosPendientes() {
        if (funcionPendiente != null && horarioPendiente != null) {
            try {
                funcionPendiente.liberarAsientos(horarioPendiente, asientosPendientes);
            } catch (CinemaException e) {
                System.out.println("\nError al liberar asientos: " + e.getMessage());
            }
        }
        compraPendiente = false;
        funcionPendiente = null;
        horarioPendiente = null;
        totalPendiente = 0;
        asientosPendientes.clear();
    }

    private void verFunciones(Scanner scanner, ContextoAccion contexto) throws CinemaException {
        Map<String, Funcion> funcionesUnificadas = agruparFuncionesPorPelicula(
                contexto.getGestorFunciones().obtenerFunciones());
        if (funcionesUnificadas.isEmpty()) {
            System.out.println("\nNo hay funciones disponibles\n");
            return;
        }
        System.out.println("\n--- Cartelera ---");
        Object[] listaPeliculas = funcionesUnificadas.keySet().toArray();
        for (int i = 0; i < listaPeliculas.length; i++) {
            System.out.println((i + 1) + " ---> " + listaPeliculas[i]);
        }
        System.out.print("\nSeleccione el numero de la pelicula que desea ver (Ej:1): ");
        try {
            int opcion = Integer.parseInt(scanner.nextLine());
            if (opcion < 1 || opcion > listaPeliculas.length) {
                System.out.println("\nPelicula invalida\n");
                return;
            }
            Funcion funcionSeleccionada = funcionesUnificadas.get(listaPeliculas[opcion - 1].toString());
            funcionSeleccionada.mostrarHorarios();
            System.out.print("\nIngrese el horario de la funcion que desea ver (HH:mm-dd/MM/yyyy): ");
            String horario = scanner.nextLine();
            Sala sala;
            try {
                sala = funcionSeleccionada.obtenerSala(horario);
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
                    nuevasReservas
                            .add(new Reserva(funcionSeleccionada, sala.getNombre(), fila + 1, columna + 1, precio));
                } catch (CinemaException e) {
                    System.out.println(e.getMessage());
                    i--;
                }
            }
            System.out.println("\n--- Resumen de Compra ---\n");
            nuevasReservas.forEach(r -> System.out.println(r));
            System.out.println("Total a pagar: $" + total);
            System.out.println("\nMetodos de pago: \n1. Efectivo \n2. Tarjeta");
            System.out.print("\nSeleccione un metodo: ");
            int metodo = Integer.parseInt(scanner.nextLine());
            boolean pagoCompletado = false;
            switch (metodo) {
                case 1:
                    pagoCompletado = mostrarMenuPago(scanner, total, false, new PagoEfectivo());
                    break;
                case 2:
                    pagoCompletado = mostrarMenuPago(scanner, total, false, new PagoTarjeta());
                    break;
                default:
                    System.out.println("\nMetodo invalido; la compra quedara pendiente\n");
            }
            if (pagoCompletado) {
                reservas.addAll(nuevasReservas);
                ArchivoCorreo.escribirNotificacion(getCorreo().getDireccion(), funcionSeleccionada.getPelicula()
                        + " - " + horario + " - " + sala.getNombre() + " - Total: $" + total + " - Compra exitosa");
                System.out.println("\nReserva exitosa\n");
            } else {
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

    private boolean mostrarMenuPago(Scanner scanner, double total, boolean esReanudacion) throws CinemaException {
        System.out.println("\nMetodos de pago: \n1 ---> Efectivo \n2 ---> Tarjeta\n");
        System.out.print("Seleccione un metodo (Ej:1): ");
        try {
            int metodo = Integer.parseInt(scanner.nextLine());
            switch (metodo) {
                case 1:
                    return mostrarMenuPago(scanner, total, esReanudacion, new PagoEfectivo());
                case 2:
                    return mostrarMenuPago(scanner, total, esReanudacion, new PagoTarjeta());
                default:
                    System.out.println("\nMetodo invalido\n");
                    return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("\nEntrada invalida\n");
            return false;
        }
    }

    private boolean mostrarMenuPago(Scanner scanner, double total, boolean esReanudacion, MetodoPago metodoPago)
            throws CinemaException {
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
            System.out.println("\n¿Desea reintentar el pago? (S/N): ");
            String r = scanner.nextLine().trim().toLowerCase();
            if (r.equals("s")) {
                return mostrarMenuPago(scanner, total, esReanudacion, metodoPago);
            }
        }
        return false;
    }

    private Map<String, Funcion> agruparFuncionesPorPelicula(List<Funcion> funciones) {
        Map<String, Funcion> funcionesUnificadas = new LinkedHashMap<>();
        for (Funcion f : funciones) {
            if (funcionesUnificadas.containsKey(f.getPelicula())) {
                Funcion existente = funcionesUnificadas.get(f.getPelicula());
                existente.getHorarios().putAll(f.getHorarios());
            } else {
                funcionesUnificadas.put(f.getPelicula(), f);
            }
        }
        return funcionesUnificadas;
    }

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
            double totalPelicula = reservasAgrupadas.get(pelicula).stream().mapToDouble(Reserva::getPrecio).sum();
            System.out.println(cont + " ---> " + pelicula + ", Total de la compra: $" + totalPelicula);
            reservasAgrupadas.get(pelicula).forEach(r -> System.out.println("    - " + r));
            cont++;
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    private void eliminarCuenta(Scanner scanner, ContextoAccion contexto) {
        System.out.print("\n¿Esta seguro de que desea eliminar su cuenta? (S/N): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        if (confirm.equals("s")) {
            liberarAsientosPendientes();
            reservas.clear();
            contexto.getUsuarios().remove(this);
            contexto.getRepoUsuarios().guardarUsuarios(contexto.getUsuarios());
            Reporte reporte = new Reporte();
            reporte.notificarAdministradores(contexto.getUsuarios(),
                    "El cliente " + getUser() + " ha eliminado su cuenta.");
            System.out.println("\nCuenta eliminada con exito. Saliendo del sistema\n");
        } else {
            System.out.println("\nOperacion cancelada\n");
        }
    }
}
