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

public class RepositorioFuncionesArchivo implements IPersistenciaFunciones {

    private static final String FUNCIONES_FILE = "src/main/java/Repositorios/funciones.txt";

    @Override
    public List<Funcion> cargarFunciones() {
        List<Funcion> funciones = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FUNCIONES_FILE))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                Funcion funcion = new Funcion(partes[0]);
                for (int i = 1; i < partes.length; i += 2) {
                    String horario = partes[i];
                    String salaNombre = partes[i + 1];
                    Sala sala = RepositorioSalasArchivo.buscarPorNombre(salaNombre);
                    if (sala == null) {
                        // Si no existe la sala, creamos una básica
                        sala = new Sala(salaNombre, 5, 5);
                        sala.inicializarAsientos(10.0, 20.0, 30.0);
                        RepositorioSalasArchivo.agregarSala(sala);
                    }
                    funcion.agregarHorario(horario, sala);
                }
                funciones.add(funcion);
            }
        } catch (IOException e) {
            System.out.println("\nNo se encontraron funciones persistidas\n");
        }
        return funciones;
    }

    @Override
    public void guardarFunciones(List<Funcion> funciones) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FUNCIONES_FILE))) {
            for (Funcion funcion : funciones) {
                StringBuilder sb = new StringBuilder(funcion.getPelicula());
                funcion.getHorarios().forEach((horario, sala)
                        -> sb.append(",").append(horario).append(",").append(sala.getNombre())
                );
                writer.write(sb.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("\nError al guardar funciones: " + e.getMessage()+ "\n");
        }
    }
}
