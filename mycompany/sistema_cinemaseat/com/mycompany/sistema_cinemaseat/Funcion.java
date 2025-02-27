package com.mycompany.sistema_cinemaseat;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author User
 */

 public class Funcion {

    private String pelicula;
    private Map<String, Sala> horarios;   

    public Funcion(String pelicula) {
        this.pelicula = pelicula;
        this.horarios = new HashMap<>();
    }

    public String getPelicula() {
        return pelicula;
    }

    public Map<String, Sala> getHorarios() {
        return horarios;
    }

    public void agregarHorario(String horario, Sala sala) {
        horarios.put(horario, sala);
    }

    public Sala obtenerSala(String horario) throws CinemaException {
        Sala sala = horarios.get(horario);
        if (sala == null) {
            throw new CinemaException("Horario no valido para la funcion seleccionada");
        }
        return sala;
    }

    public void mostrarHorarios() {
        System.out.println("=== Horarios disponibles para " + pelicula.toUpperCase() + " ===");
        if (horarios.isEmpty()) {
            System.out.println("No hay horarios disponibles");
        } else {
            List<String> listaHorarios = new ArrayList<>(horarios.keySet());
            Collections.sort(listaHorarios);
            listaHorarios.forEach(h -> System.out.println(h));
        }
    }

    public void liberarAsientos(String horario, List<Reserva> reservas) throws CinemaException {
        Sala sala = obtenerSala(horario);
        for (Reserva r : reservas) {
            sala.liberarAsiento(r.getFila() - 1, r.getColumna() - 1);
        }
    }
}
