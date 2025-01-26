package com.mycompany.sistema_cinemaseat;

import java.util.List;
import java.util.Scanner;

/**
 *
 * @author User
 */

 public class MenuPrincipal {

    private Scanner scanner;
    private IPersistenciaUsuarios repoUsuarios;
    private IGestorFunciones gestorFunciones;
    private INotificador notificador;
    private List<Usuario> usuarios;

    public MenuPrincipal(Scanner scanner, IPersistenciaUsuarios repoUsuarios, IGestorFunciones gestorFunciones, INotificador notificador, List<Usuario> usuarios) {
        this.scanner = scanner;
        this.repoUsuarios = repoUsuarios;
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
                        repoUsuarios.guardarUsuarios(usuarios);
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
        Usuario clienteValido = autenticarUsuario(Cliente.class);
        if (clienteValido == null) {
            System.out.println("\nDatos ingresados inválidos");
            return;
        }
        ((Cliente) clienteValido).manejarAcciones(this.getContexto());
    }

    private void gestionarAdministrador() throws CinemaException {
        Usuario adminValido = autenticarUsuario(Administrador.class);
        if (adminValido == null) {
            System.out.println("\nDatos ingresados inválidos");
            return;
        }
        ((Administrador) adminValido).manejarAcciones(this.getContexto());
    }

    private Usuario autenticarUsuario(Class<?> tipoUsuario) {
        System.out.print("\nUsuario: ");
        String user = scanner.nextLine();
        System.out.print("Clave: ");
        String clave = scanner.nextLine();

        return usuarios.stream()
                .filter(u -> u.getUser().equals(user)
                && u.getContrasena().validar(clave)
                && tipoUsuario.isInstance(u))
                .findFirst()
                .orElse(null);
    }

    private void crearUsuario() {
        System.out.println("[NOTA] Administrador su correo debe contener 'admin'");
        System.out.println("\nTipos de Usuarios\n");
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
            System.out.print("Clave: ");
            String clave = scanner.nextLine();
            Usuario nuevoUsuario = (tipo == 1) ? new Cliente(user, nombre, correo, clave) : new Administrador(user, nombre, correo, clave);
            usuarios.add(nuevoUsuario);
            repoUsuarios.guardarUsuarios(usuarios);
            ArchivoCorreo.escribirNotificacion(correo, "Bienvenido/a " + nombre + ", su usuario ha sido creado.");
            System.out.println("\nUsuario creado con exito\n");
        } catch (NumberFormatException e) {
            System.out.println("\nEntrada invalida\n");
        }
    }

    private ContextoAccion getContexto() {
        return new ContextoAccion(scanner, gestorFunciones, usuarios, repoUsuarios);
    }
}
