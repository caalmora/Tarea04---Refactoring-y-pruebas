/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.sistema_cinemaseat;

import java.util.Scanner;
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
public class PagoEfectivoTest {
    
    public PagoEfectivoTest() {
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

    /**
     * Test of procesarPago method, of class PagoEfectivo.
     */
    @Test
    public void testProcesarPago() throws Exception {
        System.out.println("procesarPago");
        double cantidad = 0.0;
        Scanner scanner = null;
        PagoEfectivo instance = new PagoEfectivo();
        boolean expResult = false;
        boolean result = instance.procesarPago(cantidad, scanner);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDescripcion method, of class PagoEfectivo.
     */
    @Test
    public void testGetDescripcion() {
        System.out.println("getDescripcion");
        PagoEfectivo instance = new PagoEfectivo();
        String expResult = "";
        String result = instance.getDescripcion();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    @Test
public void testRealizarPagoValido() {
    PagoEfectivo pago = new PagoEfectivo();
    assertTrue(pago.realizarPago(100.50));
}

@Test
public void testProcesarPagoMontoInsuficiente() {
    PagoEfectivo pago = new PagoEfectivo();
    Scanner scanner = new Scanner(new ByteArrayInputStream("1\n50.0\n2\n".getBytes()));
    assertFalse(pago.procesarPago(100.0, scanner));
}

@Test
public void testSalirProcesoPago() {
    PagoEfectivo pago = new PagoEfectivo();
    Scanner scanner = new Scanner(new ByteArrayInputStream("2\n".getBytes()));
    assertFalse(pago.procesarPago(100.0, scanner));
}

    
}
