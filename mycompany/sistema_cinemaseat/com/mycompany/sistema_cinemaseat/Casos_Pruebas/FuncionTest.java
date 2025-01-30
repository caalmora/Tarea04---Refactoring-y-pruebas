import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.mycompany.sistema_cinemaseat.*

class FuncionTest {
    private Funcion funcion;

    @BeforeEach
    void setUp() {
        funcion = new Funcion("Pelicula", "20:00", new Sala(1, 100));
    }

    @Test
    void testObtenerHorarioNormal() {
        assertEquals("20:00", funcion.getHorario());
    }

    @Test
    void testObtenerHorarioNormal2() {
        assertTrue(funcion.getHorario().matches("\\d{2}:\\d{2}"));
    }

    @Test
    void testObtenerHorarioError() {
        assertNotEquals("22:00", funcion.getHorario());
    }

    @Test
    void testObtenerHorarioError2() {
        funcion = new Funcion("Pelicula", "25:00", new Sala(1, 100));
        assertThrows(IllegalArgumentException.class, () -> funcion.getHorario());
    }
}
