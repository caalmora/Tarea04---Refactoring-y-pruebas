/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_cinemaseat;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */
public class RepositorioUsuariosArchivo implements IPersistenciaUsuarios {

    private static final String USUARIOS_FILE = "src/main/java/Repositorios/usuarios.txt";

    @Override
    public List<Usuario> cargarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USUARIOS_FILE))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes[0].equals("Cliente")) {
                    usuarios.add(new Cliente(partes[1], partes[2], partes[3], Integer.parseInt(partes[4])));
                } else {
                    usuarios.add(new Administrador(partes[1], partes[2], partes[3], Integer.parseInt(partes[4])));
                }
            }
        } catch (IOException e) {
            System.out.println("\nNo se encontraron usuarios persistidos\n");
        }
        return usuarios;
    }

    @Override
    public void guardarUsuarios(List<Usuario> usuarios) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USUARIOS_FILE))) {
            for (Usuario usuario : usuarios) {
                String tipo = (usuario instanceof Cliente) ? "Cliente" : "Administrador";
                writer.write(tipo + "," + usuario.getUser() + "," + usuario.getNombre() + ","
                        + usuario.getCorreo() + "," + usuario.getContrasena());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("\nError al guardar usuarios: " + e.getMessage());
            System.out.println();
        }
    }
}