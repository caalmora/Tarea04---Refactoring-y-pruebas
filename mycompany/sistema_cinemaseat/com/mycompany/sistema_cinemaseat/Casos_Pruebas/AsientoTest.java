import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.mycompany.sistema_cinemaseat.*

class AsientoTest {
    private Asiento asiento;

    @BeforeEach
    void setUp() {
        asiento = new AsientoEstandar(100.0);
    }

    @Test
    void testGetPrecioNormal() {
        assertEquals(100.0, asiento.getPrecio());
    }

    @Test
    void testGetPrecioNormal2() {
        assertTrue(asiento.getPrecio() > 0);
    }

    @Test
    void testGetPrecioError() {
        assertNotEquals(50.0, asiento.getPrecio());
    }

    @Test
    void testGetPrecioError2() {
        asiento = new AsientoEstandar(-10.0);
        assertThrows(IllegalArgumentException.class, () -> asiento.getPrecio());
    }

    @Test
    void testCambiarEstadoNormal() {
        asiento.setEstado(EstadoAsiento.RESERVADO);
        assertEquals(EstadoAsiento.RESERVADO, asiento.getEstado());
    }

    @Test
    void testCambiarEstadoNormal2() {
        asiento.setEstado(EstadoAsiento.DISPONIBLE);
        assertEquals(EstadoAsiento.DISPONIBLE, asiento.getEstado());
    }

    @Test
    void testCambiarEstadoError() {
        assertThrows(IllegalArgumentException.class, () -> asiento.setEstado(null));
    }

    @Test
    void testCambiarEstadoError2() {
        asiento.setEstado(EstadoAsiento.RESERVADO);
        assertNotEquals(EstadoAsiento.DISPONIBLE, asiento.getEstado());
    }
}
