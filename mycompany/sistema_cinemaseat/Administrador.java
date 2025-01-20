package com.mycompany.sistema_cinemaseat;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author User
 */

public class Administrador extends Usuario {

    public Administrador(String user, String nombre, String correo, int contrasena) {
        super(user, nombre, correo, contrasena);
    }

    public void manejarAcciones(
            Scanner scanner,IGestorFunciones gestorFunciones,List<Usuario> usuarios,IPersistenciaUsuarios repoUsuarios
    ) {
        System.out.println("\nBIENVENIDO/A " + getNombre().toUpperCase());
        boolean activo = true;
        while (activo) {
            System.out.println("\n----- Menu Administrador -----");
            System.out.println("1 ----> Agregar / eliminar funcion");
            System.out.println("2 ----> Agregar / eliminar sala");
            System.out.println("3 ----> Cambiar precio de asientos");
            System.out.println("4 ----> Eliminar Usuario");
            System.out.println("5 ----> Salir");
            System.out.print("\nSeleccione una opcion numerica (Ej:1): ");
            System.out.println();

            try {
                int opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> gestionarFunciones(scanner, gestorFunciones);
                    case 2 -> gestionarSalas(scanner);
                    case 3 -> cambiarPrecioAsientos(scanner);
                    case 4 -> eliminarUsuario(scanner, usuarios, repoUsuarios);
                    case 5 -> activo = false;
                    default -> System.out.println("\nOpcion no valida\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nEntrada invalida\n");
            }
        }
    }

    private void gestionarFunciones(Scanner scanner, IGestorFunciones gestorFunciones) {
        System.out.println("1 ---> Agregar");
        System.out.println("2 ---> Eliminar");
        System.out.println("3 ---> Regresar");
        System.out.print("Seleccione una opcion numerica (Ej:1): ");
        System.out.println();
        try {
            int opcion = Integer.parseInt(scanner.nextLine());
            if (opcion == 1) {
                System.out.print("Ingrese el nombre de la pelicula: ");
                String pelicula = scanner.nextLine();
                System.out.print("Ingrese el horario (ej: 18:00-12/05/2025): ");
                String horario = scanner.nextLine();
                System.out.println();
                List<Sala> salasDisponibles = RepositorioSalasArchivo.getSalas();
                if (salasDisponibles.isEmpty()) {
                    System.out.println("No existen salas disponibles\n");
                    return;
                }
                System.out.println("Salas disponibles: ");
                for (int i = 0; i < salasDisponibles.size(); i++) {
                    Sala s = salasDisponibles.get(i);
                    System.out.println((i + 1) + " ---> " + s.getNombre());
                    System.out.println();
                }
                System.out.print("\nSeleccione el numero de la sala que desea usar (Ej:1): ");
                System.out.println();
                int opcionSala = Integer.parseInt(scanner.nextLine());
                if (opcionSala < 1 || opcionSala > salasDisponibles.size()) {
                    System.out.println("\nOpcion de sala invalida. Se cancelara el proceso\n");
                    return;
                }
                Sala salaSeleccionada = salasDisponibles.get(opcionSala - 1);
                Funcion funcion = new Funcion(pelicula);
                funcion.agregarHorario(horario, salaSeleccionada);
                gestorFunciones.agregarFuncion(funcion);
                System.out.println("\nFuncion agregada con exito\n");
            } else if (opcion == 2) {
                List<Funcion> funciones = gestorFunciones.obtenerFunciones();
                if (funciones.isEmpty()) {
                    System.out.println("\nNo hay funciones disponibles\n");
                    return;
                }
                System.out.println("\nFunciones disponibles:");
                System.out.println();
                for (int i = 0; i < funciones.size(); i++) {
                    System.out.println((i + 1) + " ---> " + funciones.get(i).getPelicula());
                }
                System.out.print("\nIngrese el numero de la funcion a eliminar: ");
                int num = Integer.parseInt(scanner.nextLine());
                System.out.println();
                if (num >= 1 && num <= funciones.size()) {
                    Funcion funcionEliminada = funciones.remove(num - 1);
                    List<Cliente> listaClientes = new ArrayList<>();
                    ArchivoCorreo.notificarAClientes(
                        listaClientes,
                        "La función " + funcionEliminada.getPelicula()
                        + " ha sido cancelada por el administrador " + getNombre()
                    );
                    System.out.println("\nFuncion eliminada y clientes notificados\n");
                } else {
                    System.out.println("\nNumero invalido\n");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("\nEntrada invalida\n");
        }
    }

    private void gestionarSalas(Scanner scanner) {
        System.out.println("1. Agregar");
        System.out.println("2. Eliminar");
        System.out.println("3. Regresar");
        System.out.print("Seleccione una opcion numerica (Ej:1): ");
        System.out.println();
        try {
            int opcion = Integer.parseInt(scanner.nextLine());
            if (opcion == 1) {
                System.out.print("Nombre de la nueva sala: ");
                String nombreSala = scanner.nextLine();
                System.out.print("Numero de filas de la sala: ");
                int filas = Integer.parseInt(scanner.nextLine());
                System.out.print("Numero de columnas de la sala: ");
                int columnas = Integer.parseInt(scanner.nextLine());
                Sala sala = new Sala(nombreSala, filas, columnas);
                sala.inicializarAsientos(10.0, 20.0, 30.0);
                RepositorioSalasArchivo.agregarSala(sala);
                System.out.println("\nSala agregada con exito\n");
            } else if (opcion == 2) {
                System.out.print("Ingrese el nombre de la sala a eliminar: ");
                String nombreSala = scanner.nextLine();
                Sala salaEliminada = RepositorioSalasArchivo.eliminarSala(nombreSala);
                if (salaEliminada != null) {
                    List<Cliente> listaClientes = new ArrayList<>();
                    ArchivoCorreo.notificarAClientes(
                        listaClientes,
                        "La sala " + nombreSala
                        + " se encuentra en reparaciones. Se ha reasignado a los clientes afectados."
                    );
                    System.out.println("\nSala eliminada y clientes notificados\n");
                } else {
                    System.out.println("\nSala no encontrada\n");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("\nEntrada invalida\n");
        }
    }

    private void cambiarPrecioAsientos(Scanner scanner) {
        System.out.println("Cambiar precio de asientos:");
        System.out.println("1 ---> VIP");
        System.out.println("2 ---> Estandar");
        System.out.println("3 ---> 4D");
        System.out.print("Seleccione una opcion numerica (Ej:1): ");
        try {
            int opcion = Integer.parseInt(scanner.nextLine());
            System.out.print("\nIngrese el nuevo precio: ");
            double nuevoPrecio = Double.parseDouble(scanner.nextLine());
            for (Sala s : RepositorioSalasArchivo.getSalas()) {
                Asiento[][] matriz = s.getAsientos();
                for (int i = 0; i < matriz.length; i++) {
                    for (int j = 0; j < matriz[i].length; j++) {
                        if (matriz[i][j].getEstado() == EstadoAsiento.DISPONIBLE) {
                            String tipo = matriz[i][j].getTipo();
                            if ((opcion == 1 && tipo.equals("VIP"))
                                    || (opcion == 2 && tipo.equals("Estandar"))
                                    || (opcion == 3 && tipo.equals("4D"))) {
                                matriz[i][j].setPrecio(nuevoPrecio);
                            }
                        }
                    }
                }
            }
            RepositorioSalasArchivo.guardarSalas();
            System.out.println("\nPrecio actualizado exitosamente\n");
        } catch (NumberFormatException e) {
            System.out.println("\nEntrada invalida\n");
        }
    }

    private void eliminarUsuario(Scanner scanner, List<Usuario> usuarios, IPersistenciaUsuarios repoUsuarios) {
        System.out.println("---- Eliminar Usuario ----");
        int index = 1;
        for (Usuario u : usuarios) {
            String tipo = (u instanceof Cliente) ? "Cliente" : "Administrador";
            System.out.println(index + " ---> " + u.getUser() + " (" + tipo + ")");
            index++;
        }
        System.out.print("\nIngrese el numero del usuario a eliminar: ");
        System.out.println();
        try {
            int opcion = Integer.parseInt(scanner.nextLine());
            if (opcion < 1 || opcion > usuarios.size()) {
                System.out.println("\nOpcion invalida\n");
                return;
            }
            Usuario userAEliminar = usuarios.get(opcion - 1);
            System.out.print("\n¿Esta seguro de que desea eliminar a " + userAEliminar.getUser() + "? (S/N): ");
            System.out.println();
            String confirm = scanner.nextLine().trim().toLowerCase();
            if (confirm.equals("s")) {
                usuarios.remove(userAEliminar);
                repoUsuarios.guardarUsuarios(usuarios);

                Reporte reporte = new Reporte();
                reporte.notificarAdministradores(
                    usuarios,
                    "Se ha eliminado al usuario " + userAEliminar.getUser()
                );
                System.out.println("\nUsuario eliminado con exito\n");
            }
        } catch (NumberFormatException e) {
            System.out.println("\nEntrada invalida\n");
        }
    }
}
