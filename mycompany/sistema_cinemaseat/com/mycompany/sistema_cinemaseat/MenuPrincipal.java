package com.mycompany.sistema_cinemaseat;

import java.util.List;
import java.util.Scanner;

/**
 *
 * @author User
 */

public class MenuPrincipal {

    private Scanner scanner;
    private IPersistenciaUsuarios repositorioUsuarios;
    private IGestorFunciones gestorFunciones;
    private INotificador notificador;
    private List<Usuario> usuarios;

    public MenuPrincipal(Scanner scanner, IPersistenciaUsuarios repositorioUsuarios,
            IGestorFunciones gestorFunciones, INotificador notificador, List<Usuario> usuarios) {
        this.scanner = scanner;
        this.repositorioUsuarios = repositorioUsuarios;
        this.gestorFunciones = gestorFunciones;
        this.notificador = notificador;
        this.usuarios = usuarios;
    }

    public void mostrarMenu() throws CinemaException {
        boolean sistemaActivo = true;
        while (sistemaActivo) {
            System.out.println("\n=== CinemaSeat ===");
            System.out.println("1 ---> Cliente");
            System.out.println("2 ---> Administrador");
            System.out.println("3 ---> Crear Usuario");
            System.out.println("4 ---> Salir");
            System.out.print("\nSeleccione una opcion nuemrica (Ej:1): ");
            try {
                int opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 ->
                        gestionarCliente();
                    case 2 ->
                        gestionarAdministrador();
                    case 3 ->
                        crearUsuario();
                    case 4 -> {
                        repositorioUsuarios.guardarUsuarios(usuarios);
                        sistemaActivo = false;
                        System.out.println("\nGracias por usar CinemaSeat...");
                    }
                    default ->
                        System.out.println("\nOpcion no valida");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nEntrada invalida de datos");
            }
        }
    }

    private void gestionarCliente() throws CinemaException {
        Usuario clienteValido = autenticarCliente();
        if (clienteValido != null) {
            ((Cliente) clienteValido).manejarAcciones(scanner, gestorFunciones.obtenerFunciones(), usuarios, repositorioUsuarios);
        }
    }

    private Usuario autenticarCliente() {
        System.out.print("Usuario: ");
        String user = scanner.nextLine();
        System.out.print("Clave: ");
        int pass = Integer.parseInt(scanner.nextLine());
        return usuarios.stream()
                .filter(u -> u.getUser().equals(user) && u.getContrasena() == pass && (u instanceof Cliente))
                .findFirst().orElse(null);
    }
    
    private void gestionarAdministrador() throws CinemaException {
        Usuario adminValido = autenticarAdministrador();
        if (adminValido != null) {
            ((Cliente) adminValido).manejarAcciones(scanner, gestorFunciones.obtenerFunciones(), usuarios, repositorioUsuarios);
        }
    }
    
    private Usuario autenticarAdministrador() {
        System.out.print("Usuario: ");
        String user = scanner.nextLine();
        System.out.print("Clave: ");
        int pass = Integer.parseInt(scanner.nextLine());
        return usuarios.stream()
                .filter(u -> u.getUser().equals(user) && u.getContrasena() == pass && (u instanceof Administrador))
                .findFirst().orElse(null);
    }

    private void crearUsuario() {
        System.out.println("[NOTA] Administrador su correo debe contener 'admin'");
        System.out.println("Tipos de Usuarios");
        System.out.println("1 ---> Cliente\n2 ---> Administrador\n");
        System.out.print("Ingrese una opcion numerica (Ej:1): ");
        try {
            int tipo = Integer.parseInt(scanner.nextLine());
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Correo: ");
            String correo = scanner.nextLine();
            System.out.print("Usuario: ");
            String user = scanner.nextLine();
            System.out.print("Clave (solo numeros): ");
            int contrasena = Integer.parseInt(scanner.nextLine());
            Usuario nuevoUsuario = (tipo == 1)
                    ? new Cliente(user, nombre, correo, contrasena)
                    : new Administrador(user, nombre, correo, contrasena);
            usuarios.add(nuevoUsuario);
            repositorioUsuarios.guardarUsuarios(usuarios);
            if (nuevoUsuario instanceof Cliente) {
                com.mycompany.sistema_cinemaseat.ArchivoCorreo.escribirCliente(
                        correo,
                        "Bienvenido/a " + nombre + ", su usuario ha sido creado."
                );
            } else {
                com.mycompany.sistema_cinemaseat.ArchivoCorreo.escribirAdministrador(
                        correo,
                        "Bienvenido/a " + nombre + ", su usuario ha sido creado."
                );
            }
            System.out.println("Usuario creado con exito");
        } catch (NumberFormatException e) {
            System.out.println("Entrada invalida");
        }
    }
}
