package com.mycompany.sistema_cinemaseat.Casos_Pruebas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class FuncionTest {

    @Test
    void testAgregarHorarioNormal() {
        Funcion funcion = new Funcion("Inception");
        Sala sala = new Sala(10, 10);
        funcion.agregarHorario("12:00", sala);
        assertTrue(funcion.getHorarios().containsKey("12:00"));
    }

    @Test
    void testAgregarHorarioNormalMultiple() {
        Funcion funcion = new Funcion("Inception");
        Sala sala1 = new Sala(10, 10);
        Sala sala2 = new Sala(15, 15);
        funcion.agregarHorario("12:00", sala1);
        funcion.agregarHorario("15:00", sala2);
        assertTrue(funcion.getHorarios().containsKey("12:00"));
        assertTrue(funcion.getHorarios().containsKey("15:00"));
    }

    @Test
    void testAgregarHorarioError() {
        Funcion funcion = new Funcion("Inception");
        Sala sala = new Sala(10, 10);
        funcion.agregarHorario("12:00", sala);
        assertFalse(funcion.getHorarios().containsKey("15:00"));
    }

    @Test
    void testAgregarHorarioErrorMultiple() {
        Funcion funcion = new Funcion("Inception");
        Sala sala1 = new Sala(10, 10);
        Sala sala2 = new Sala(15, 15);
        funcion.agregarHorario("12:00", sala1);
        funcion.agregarHorario("15:00", sala2);
        assertFalse(funcion.getHorarios().containsKey("18:00"));
    }

    @Test
    void testObtenerSalaNormal() throws CinemaException {
        Funcion funcion = new Funcion("Inception");
        Sala sala = new Sala(10, 10);
        funcion.agregarHorario("12:00", sala);
        Sala result = funcion.obtenerSala("12:00");
        assertNotNull(result);
        assertEquals(sala, result);
    }

    @Test
    void testObtenerSalaNormalConMultiplesHorarios() throws CinemaException {
        Funcion funcion = new Funcion("Inception");
        Sala sala1 = new Sala(10, 10);
        Sala sala2 = new Sala(15, 15);
        funcion.agregarHorario("12:00", sala1);
        funcion.agregarHorario("15:00", sala2);
        Sala result = funcion.obtenerSala("15:00");
        assertNotNull(result);
        assertEquals(sala2, result);
    }

    @Test
    void testObtenerSalaError() {
        Funcion funcion = new Funcion("Inception");
        Sala sala = new Sala(10, 10);
        funcion.agregarHorario("12:00", sala);
        assertThrows(CinemaException.class, () -> {
            funcion.obtenerSala("15:00");
        });
    }

    @Test
    void testObtenerSalaErrorConVariosHorarios() {
        Funcion funcion = new Funcion("Inception");
        Sala sala1 = new Sala(10, 10);
        Sala sala2 = new Sala(15, 15);
        funcion.agregarHorario("12:00", sala1);
        funcion.agregarHorario("15:00", sala2);
        assertThrows(CinemaException.class, () -> {
            funcion.obtenerSala("18:00");
        });
    }

    @Test
    void testMostrarHorariosNormal() {
        Funcion funcion = new Funcion("Inception");
        Sala sala = new Sala(10, 10);
        funcion.agregarHorario("12:00", sala);
        // Aquí solo verificamos que no arroje excepciones y que se imprime algo
        assertDoesNotThrow(() -> funcion.mostrarHorarios());
    }

    @Test
    void testMostrarHorariosVacio() {
        Funcion funcion = new Funcion("Inception");
        assertDoesNotThrow(() -> funcion.mostrarHorarios());
    }

    @Test
    void testMostrarHorariosConVariosHorarios() {
        Funcion funcion = new Funcion("Inception");
        Sala sala1 = new Sala(10, 10);
        Sala sala2 = new Sala(15, 15);
        funcion.agregarHorario("12:00", sala1);
        funcion.agregarHorario("15:00", sala2);
        assertDoesNotThrow(() -> funcion.mostrarHorarios());
    }

    @Test
    void testMostrarHorariosError() {
        Funcion funcion = new Funcion("Inception");
        assertDoesNotThrow(() -> funcion.mostrarHorarios());
    }

    @Test
    void testLiberarAsientosNormal() throws CinemaException {
        Funcion funcion = new Funcion("Inception");
        Sala sala = new Sala(10, 10);
        funcion.agregarHorario("12:00", sala);
        Reserva reserva = new Reserva(1, 1);
        List<Reserva> reservas = new ArrayList<>();
        reservas.add(reserva);
        assertDoesNotThrow(() -> funcion.liberarAsientos("12:00", reservas));
    }

    @Test
    void testLiberarAsientosNormalConVarios() throws CinemaException {
        Funcion funcion = new Funcion("Inception");
        Sala sala = new Sala(10, 10);
        funcion.agregarHorario("12:00", sala);
        Reserva reserva1 = new Reserva(1, 1);
        Reserva reserva2 = new Reserva(2, 2);
        List<Reserva> reservas = new ArrayList<>();
        reservas.add(reserva1);
        reservas.add(reserva2);
        assertDoesNotThrow(() -> funcion.liberarAsientos("12:00", reservas));
    }

    @Test
    void testLiberarAsientosErrorHorarioInvalido() {
        Funcion funcion = new Funcion("Inception");
        Sala sala = new Sala(10, 10);
        funcion.agregarHorario("12:00", sala);
        Reserva reserva = new Reserva(1, 1);
        List<Reserva> reservas = new ArrayList<>();
        reservas.add(reserva);
        assertThrows(CinemaException.class, () -> {
            funcion.liberarAsientos("15:00", reservas);
        });
    }

    @Test
    void testLiberarAsientosErrorAsientoNoExistente() {
        Funcion funcion = new Funcion("Inception");
        Sala sala = new Sala(2, 2); // Sala con solo 2x2 asientos
        funcion.agregarHorario("12:00", sala);
        Reserva reserva = new Reserva(3, 3); // Asiento fuera de los límites
        List<Reserva> reservas = new ArrayList<>();
        reservas.add(reserva);
        assertThrows(IndexOutOfBoundsException.class, () -> {
            funcion.liberarAsientos("12:00", reservas);
        });
    }
}
