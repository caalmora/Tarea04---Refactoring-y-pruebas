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
public class ProcesoPagoTest {
    
    
    public ProcesoPagoTest() {
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
     * Test of isPagoExitoso method, of class ProcesoPago.
     */
    @Test
    public void testIsPagoExitoso() {
        System.out.println("isPagoExitoso");
        ProcesoPago instance = null;
        boolean expResult = false;
        boolean result = instance.isPagoExitoso();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isTiempoAgotado method, of class ProcesoPago.
     */
    @Test
    public void testIsTiempoAgotado() {
        System.out.println("isTiempoAgotado");
        ProcesoPago instance = null;
        boolean expResult = false;
        boolean result = instance.isTiempoAgotado();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of iniciarProcesoPago method, of class ProcesoPago.
     */
    @Test
    public void testIniciarProcesoPago() throws Exception {
        System.out.println("iniciarProcesoPago");
        double cantidad = 0.0;
        Scanner scanner = null;
        ProcesoPago instance = null;
        instance.iniciarProcesoPago(cantidad, scanner);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
     private ProcesoPago procesoPago;
    
    /**
     * ID: TC020
     * Propósito: Verificar que un pago exitoso se registra correctamente.
     * Precondiciones: Método de pago válido y pago aprobado.
     * Entradas: cantidad = 100.0, Scanner válido.
     * Salidas esperadas: pagoExitoso = true.
     */
    @Test
    public void testPagoExitoso() throws CinemaException {
        MetodoPago metodoPago = (cantidad, scanner) -> true;
        procesoPago = new ProcesoPago(metodoPago);
        
        procesoPago.iniciarProcesoPago(100.0, new Scanner(System.in));
        
        assertTrue(procesoPago.isPagoExitoso());
        assertFalse(procesoPago.isTiempoAgotado());
    }
    
    /**
     * ID: TC021
     * Propósito: Verificar que un pago fallido no se registra como exitoso.
     * Precondiciones: Método de pago válido y pago denegado.
     * Entradas: cantidad = 100.0, Scanner válido.
     * Salidas esperadas: pagoExitoso = false.
     */
    @Test
    public void testPagoFallido() throws CinemaException {
        MetodoPago metodoPago = (cantidad, scanner) -> false;
        procesoPago = new ProcesoPago(metodoPago);
        
        procesoPago.iniciarProcesoPago(100.0, new Scanner(System.in));
        
        assertFalse(procesoPago.isPagoExitoso());
    }
    
    /**
     * ID: TC022
     * Propósito: Verificar que si el tiempo se agota, el pago no se registra como exitoso.
     * Precondiciones: Método de pago válido pero tiempo agotado antes de completar el pago.
     * Entradas: cantidad = 100.0, Scanner válido.
     * Salidas esperadas: pagoExitoso = false, tiempoAgotado = true.
     */
    @Test
    public void testTiempoAgotado() throws CinemaException {
        MetodoPago metodoPago = (cantidad, scanner) -> {
            try {
                Thread.sleep(16000); // Simula una demora que excede el tiempo límite
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        };
        
        procesoPago = new ProcesoPago(metodoPago);
        procesoPago.iniciarProcesoPago(100.0, new Scanner(System.in));
        
        assertFalse(procesoPago.isPagoExitoso());
        assertTrue(procesoPago.isTiempoAgotado());
    }
}
