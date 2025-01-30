package com.mycompany.sistema_cinemaseat.Casos_Pruebas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SalaTest {
    private Sala sala;

    @BeforeEach
    void setUp() {
        sala = new Sala("Sala 1", 5, 5);
        sala.inicializarAsientos(10.0, 15.0, 20.0);
    }

    @Test
    void testSeleccionarAsientoExitoso() throws CinemaException {
        double precio = sala.seleccionarAsiento(0, 0);
        assertEquals(20.0, precio);
        assertEquals(EstadoAsiento.EN_PROCESO_COMPRA, sala.getAsientos()[0][0].getEstado());
    }

    @Test
    void testSeleccionarAsientoYaOcupado() throws CinemaException {
        sala.seleccionarAsiento(0, 0);
        CinemaException exception = assertThrows(CinemaException.class, () -> sala.seleccionarAsiento(0, 0));
        assertEquals("\nEl asiento ya esta ocupado o en proceso de compra\n", exception.getMessage());
    }

    @Test
    void testSeleccionarAsientoCoordenadasInvalidas() {
        CinemaException exception = assertThrows(CinemaException.class, () -> sala.seleccionarAsiento(10, 10));
        assertEquals("\nCoordenadas invalidas para el asiento\n", exception.getMessage());
    }

    @Test
    void testLiberarAsientoExitoso() throws CinemaException {
        sala.seleccionarAsiento(1, 1);
        sala.liberarAsiento(1, 1);
        assertEquals(EstadoAsiento.DISPONIBLE, sala.getAsientos()[1][1].getEstado());
    }

    @Test
    void testLiberarAsientoCoordenadasInvalidas() {
        CinemaException exception = assertThrows(CinemaException.class, () -> sala.liberarAsiento(10, 10));
        assertEquals("\nCoordenadas invalidas para el asiento\n", exception.getMessage());
    }
}
