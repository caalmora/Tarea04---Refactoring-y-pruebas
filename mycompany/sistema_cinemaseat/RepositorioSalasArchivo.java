/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_cinemaseat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author User
 */

public class RepositorioSalasArchivo {
    private static final String SALAS_FILE = "src/main/java/Repositorios/salas.txt";
    private static List<Sala> salas = new ArrayList<>();

    public static void cargarSalas() {
        salas.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(SALAS_FILE))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                String nombre = partes[0];
                int filas = Integer.parseInt(partes[1]);
                int columnas = Integer.parseInt(partes[2]);
                Sala sala = new Sala(nombre, filas, columnas);
                sala.inicializarAsientos(10.0, 20.0, 30.0);
                salas.add(sala);
            }
        } catch (IOException e) {
            System.out.println("\nNo se encontraron salas persistidas, iniciando con repositorio vacio\n");
        }
    }

    public static List<Sala> getSalas() {
        return salas;
    }

    public static void agregarSala(Sala sala) {
        salas.add(sala);
        guardarSalas();
    }

    public static Sala eliminarSala(String nombre) {
        Sala salaEliminada = null;
        for (Sala s : salas) {
            if (s.getNombre().equalsIgnoreCase(nombre)) {
                salaEliminada = s;
                break;
            }
        }
        if (salaEliminada != null) {
            salas.remove(salaEliminada);
            guardarSalas();
        }
        return salaEliminada;
    }

    public static Sala buscarPorNombre(String nombre) {
        for (Sala s : salas) {
            if (s.getNombre().equalsIgnoreCase(nombre)) {
                return s;
            }
        }
        return null;
    }

    public static void guardarSalas() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SALAS_FILE))) {
            for (Sala s : salas) {
                String linea = s.getNombre() + ","
                               + s.getAsientos().length + ","
                               + s.getAsientos()[0].length;
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("\nError al guardar salas: " + e.getMessage());
            System.out.println();
        }
    }
}
