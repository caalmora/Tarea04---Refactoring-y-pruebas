import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.mycompany.sistema_cinemaseat.*

class SalaTest {
    private Sala sala;

    @BeforeEach
    void setUp() {
        sala = new Sala(4, 120);
    }

    @Test
    void testObtenerCapacidadNormal() {
        assertEquals(120, sala.getCapacidad());
    }

    @Test
    void testObtenerCapacidadNormal2() {
        assertTrue(sala.getCapacidad() > 0);
    }

    @Test
    void testObtenerCapacidadError() {
        assertNotEquals(100, sala.getCapacidad());
    }

    @Test
    void testObtenerCapacidadError2() {
        sala = new Sala(5, -20);
        assertThrows(IllegalArgumentException.class, () -> sala.getCapacidad());
    }
}
