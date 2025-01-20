/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_cinemaseat;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 *
 * @author User
 */

public class Reporte {

    private static final String REPORTE_FILE = "src/main/java/Repositorios/reportes.txt";

    public void registrarReporte(String usuario, String tipoProblema) {
        String reporte = "Usuario: " + usuario + ", Tipo de problema: " + tipoProblema;
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(REPORTE_FILE, true))) {
            escritor.write(reporte);
            escritor.newLine();
            System.out.println("\nReporte registrado: " + reporte + "\n");
        } catch (IOException e) {
            System.err.println("\nError al registrar el reporte: " + e.getMessage() + "\n");
        }
    }

    public void notificarAdministradores(List<Usuario> usuarios, String mensaje) {
        List<Usuario> admins = usuarios.stream()
                .filter(u -> u instanceof Administrador)
                .toList();

        if (admins.isEmpty()) {
            System.out.println("\nNo hay administradores para notificar\n");
            return;
        }

        Random rand = new Random();
        int index = rand.nextInt(admins.size());
        Usuario adminAleatorio = admins.get(index);
        ArchivoCorreo.escribirAdministrador(
                adminAleatorio.getCorreo(),
                "[REPORTE AUTOMÁTICO] " + mensaje
        );
        System.out.println("\nReporte enviado a " + adminAleatorio.getNombre() + "\n");
    }
}