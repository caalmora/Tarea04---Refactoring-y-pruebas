/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.sistema_cinemaseat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author naomi
 */
public class CorreoTest {
    
    public CorreoTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    @Test
    public void testCorreoValido() {
        Correo correo = new Correo("usuario@correo.com");
        assertEquals("usuario@correo.com", correo.getDireccion());
    }
    
     @Test
    public void testCorreoSinArroba() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Correo("usuariocorreo.com");
        });
        assertEquals("Correo no válido", exception.getMessage());
    }
    
    @Test
    public void testCorreoNulo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Correo(null);
        });
        assertEquals("Correo no válido", exception.getMessage());
    }
}
