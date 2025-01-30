package com.mycompany.sistema_cinemaseat.Casos_Pruebas;
import com.mycompany.sistema_cinemaseat.Contrasena;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ContrasenaTest {

    @Test
    void testContrasenaValida() {
        Contrasena contrasena = new Contrasena("MiSecreta123");
        assertNotNull(contrasena);
    }

  
    
    @Test
    void testContrasenaNullLanzaExcepcion() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Contrasena(null);
        });
        assertEquals("La contraseña no puede estar vacía", exception.getMessage());
    }

   
    @Test
    void testContrasenaVaciaLanzaExcepcion() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Contrasena("");
        });
        assertEquals("La contraseña no puede estar vacía", exception.getMessage());
    }

  
    @Test
    void testHashGeneradoNoEsNuloNiVacio() {
        Contrasena contrasena = new Contrasena("MiSecreta123");
        assertNotNull(contrasena.getHash());
        assertFalse(contrasena.getHash().isEmpty());
    }
    
  
/**
     * Identificador: TC05
     * Propósito: Verificar que el método validar() devuelve true cuando la contraseña es correcta.
     * Precondiciones: Se debe proporcionar una contraseña válida y validar con la misma contraseña.
     * Entradas: "MiSecreta123"
     * Salidas esperadas: true
     */
    @Test
    void testValidarContrasenaCorrecta() {
        Contrasena contrasena = new Contrasena("MiSecreta123");
        assertTrue(contrasena.validar("MiSecreta123"));
    }

    
    
    /**
     * Identificador: TC06
     * Propósito: Verificar que el método validar() devuelve false cuando la contraseña es incorrecta.
     * Precondiciones: Se debe proporcionar una contraseña válida y validar con una diferente.
     * Entradas: "MiSecreta123" y "OtraClave456"
     * Salidas esperadas: false
     */
    @Test
    void testValidarContrasenaIncorrecta() {
        Contrasena contrasena = new Contrasena("MiSecreta123");
        assertFalse(contrasena.validar("OtraClave456"));
    }

    
    
    /**
     * Identificador: TC07
     * Propósito: Verificar que el método validar() lanza una excepción cuando la contraseña es null.
     * Precondiciones: Se debe intentar validar con una contraseña null.
     * Entradas: null
     * Salidas esperadas: Exception
     */
    @Test
    void testValidarContrasenaNullLanzaExcepcion() {
        Contrasena contrasena = new Contrasena("MiSecreta123");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            contrasena.validar(null);
        });
        assertNotNull(exception);
    }
}
