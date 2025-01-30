package com.mycompany.sistema_cinemaseat.Casos_Pruebas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GestorFuncionesTest {
    private GestorFunciones gestor;

    @BeforeEach
    void setUp() {
        gestor = new GestorFunciones();
    }

    @Test
    void testAgregarFuncionNormal() {
        Funcion funcion = new Funcion("Pelicula", "18:00", new Sala(2, 80));
        assertDoesNotThrow(() -> gestor.agregarFuncion(funcion));
    }

    @Test
    void testAgregarFuncionNormal2() {
        Funcion funcion = new Funcion("Pelicula", "18:00", new Sala(2, 80));
        gestor.agregarFuncion(funcion);
        assertTrue(gestor.getFunciones().contains(funcion));
    }

    @Test
    void testAgregarFuncionError() {
        assertThrows(IllegalArgumentException.class, () -> gestor.agregarFuncion(null));
    }

    @Test
    void testAgregarFuncionError2() {
        Funcion funcion = new Funcion("", "18:00", new Sala(2, 80));
        assertThrows(IllegalArgumentException.class, () -> gestor.agregarFuncion(funcion));
    }
}
