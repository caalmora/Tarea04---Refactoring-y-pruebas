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
public class AdministradorTest {
    private Administrador administrador;
    private ContextoAccion contextoMock;
    private Scanner scanner;
    
    public AdministradorTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        administrador = new Administrador("admin123", "Admin", "admin@correo.com", "password123");
        contextoMock = new ContextoAccion();
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of manejarAcciones method, of class Administrador.
     */
    @Test
    public void testManejarAcciones() throws Exception {
        System.out.println("manejarAcciones");
        ContextoAccion contexto = null;
        Administrador instance = null;
        instance.manejarAcciones(contexto);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
     @Test
    public void testAgregarFuncion() {
        scanner = new Scanner("1\nPelicula Prueba\n18:00-12/05/2025\n1\n");
        contextoMock.setScanner(scanner);
        
        administrador.gestionarFunciones(scanner, contextoMock);
        
        assertFalse(contextoMock.getGestorFunciones().obtenerFunciones().isEmpty());
    }
    
    @Test
    public void testAgregarFuncionSinSalasDisponibles() {
        scanner = new Scanner("1\nPelicula Sin Salas\n18:00-12/05/2025\n");
        contextoMock.setScanner(scanner);
        
        administrador.gestionarFunciones(scanner, contextoMock);
        
        assertTrue(contextoMock.getGestorFunciones().obtenerFunciones().isEmpty());
    }
    
      @Test
    public void testEliminarFuncion() {
        Funcion funcion = new Funcion("Pelicula Prueba");
        contextoMock.getGestorFunciones().agregarFuncion(funcion);
        scanner = new Scanner("2\n1\n");
        contextoMock.setScanner(scanner);
        
        administrador.gestionarFunciones(scanner, contextoMock);
        
        assertTrue(contextoMock.getGestorFunciones().obtenerFunciones().isEmpty());
    }
    
    
    
}
