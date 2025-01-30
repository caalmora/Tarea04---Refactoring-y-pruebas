package com.mycompany.sistema_cinemaseat.Casos_Pruebas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CineTest {
    private Cine cine;

    @BeforeEach
    void setUp() {
        cine = new Cine(new MenuPrincipal());
    }

    @Test
    void testIniciarSistemaNormal() {
        assertDoesNotThrow(() -> cine.iniciarSistema());
    }

    @Test
    void testIniciarSistemaNormal2() {
        cine.iniciarSistema();
        assertNotNull(cine.getMenu());
    }

    @Test
    void testIniciarSistemaError() {
        cine = new Cine(null);
        assertThrows(NullPointerException.class, () -> cine.iniciarSistema());
    }

    @Test
    void testIniciarSistemaError2() {
        cine = new Cine(null);
        assertNull(cine.getMenu());
    }
}
