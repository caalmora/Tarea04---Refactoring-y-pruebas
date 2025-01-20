package com.mycompany.sistema_cinemaseat;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author User
 */

public class ArchivoCorreo {

    private static final String RUTA_CLIENTES = "src/main/java/Correos/Clientes/";
    private static final String RUTA_ADMINISTRADORES = "src/main/java/Correos/Administradores/";

    private static void verificarDirectorio(String ruta) {
        File directorio = new File(ruta);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
    }

    public static void escribirCliente(String correo, String mensaje) {
        String nombreArchivo = RUTA_CLIENTES + correo + ".txt";
        verificarDirectorio(RUTA_CLIENTES);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo, true))) {
            writer.write(mensaje);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("\nError al escribir en el archivo de correo 'Cliente': " + e.getMessage());
            System.out.println();
        }
    }

    public static void escribirAdministrador(String correo, String mensaje) {
        String nombreArchivo = RUTA_ADMINISTRADORES + correo + ".txt";
        verificarDirectorio(RUTA_ADMINISTRADORES);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo, true))) {
            writer.write(mensaje);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("\nError al escribir en el archivo de correo 'Administrador': " + e.getMessage());
            System.out.println();
        }
    }

    public static void notificarAClientes(List<Cliente> clientes, String mensaje) {
        for (Cliente cliente : clientes) {
            escribirCliente(cliente.getCorreo(), mensaje);
        }
    }
}

