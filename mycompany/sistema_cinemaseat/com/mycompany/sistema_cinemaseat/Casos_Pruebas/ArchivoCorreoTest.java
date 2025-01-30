/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.sistema_cinemaseat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
public class ArchivoCorreoTest {
     private static final String CLIENTES_PATH = "src/main/java/Correos/Clientes/";
    private static final String ADMIN_PATH = "src/main/java/Correos/Administradores/";
    public ArchivoCorreoTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
          new File(CLIENTES_PATH).mkdirs();
        new File(ADMIN_PATH).mkdirs();
    }
    
    @AfterEach
     public void tearDown() throws IOException {
        Files.walk(Paths.get(CLIENTES_PATH)).map(Path::toFile).forEach(File::delete);
        Files.walk(Paths.get(ADMIN_PATH)).map(Path::toFile).forEach(File::delete);
    }

    /**
     * Test of escribirCliente method, of class ArchivoCorreo.
     */
    @Test
    public void testEscribirCliente() {
        System.out.println("escribirCliente");
        String correo = "";
        String mensaje = "";
        ArchivoCorreo.escribirCliente(correo, mensaje);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of escribirAdministrador method, of class ArchivoCorreo.
     */
    @Test
    public void testEscribirAdministrador() {
        System.out.println("escribirAdministrador");
        String correo = "";
        String mensaje = "";
        ArchivoCorreo.escribirAdministrador(correo, mensaje);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of escribirNotificacion method, of class ArchivoCorreo.
     */
    @Test
    public void testEscribirNotificacion() {
        System.out.println("escribirNotificacion");
        String correo = "";
        String mensaje = "";
        ArchivoCorreo.escribirNotificacion(correo, mensaje);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of notificarAClientes method, of class ArchivoCorreo.
     */
    @Test
    public void testNotificarAClientes() {
        System.out.println("notificarAClientes");
        List<Cliente> clientes = null;
        String mensaje = "";
        ArchivoCorreo.notificarAClientes(clientes, mensaje);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    @Test
    public void testNotificarAClientes1() throws IOException {
        Cliente cliente = new Cliente(new Correo("cliente@correo.com"));
        List<Cliente> clientes = Arrays.asList(cliente);
        String mensaje = "Mensaje de prueba";
        
        ArchivoCorreo.notificarAClientes(clientes, mensaje);
        
        Path path = Paths.get(CLIENTES_PATH + "cliente@correo.com.txt");
        assertTrue(Files.exists(path));
        List<String> lines = Files.readAllLines(path);
        assertTrue(lines.contains(mensaje));
        
        Files.delete(path);
    }
    
    @Test
    public void testNotificarAClientesListaNula() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            ArchivoCorreo.notificarAClientes(null, "Mensaje de prueba");
        });
    }
    
     @Test
    public void testNotificarAClientesListaVacia() throws IOException {
        List<Cliente> clientes = new ArrayList<>();
        String mensaje = "Mensaje de prueba";
        
        ArchivoCorreo.notificarAClientes(clientes, mensaje);
        
        File dir = new File(CLIENTES_PATH);
        assertEquals(0, Objects.requireNonNull(dir.list()).length);
    }
}
