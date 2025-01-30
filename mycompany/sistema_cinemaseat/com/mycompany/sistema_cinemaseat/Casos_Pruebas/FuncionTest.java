import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.mycompany.sistema_cinemaseat.*

class FuncionTest {
    private Funcion funcion;
    private Sala sala;

    @BeforeEach
    void setUp() {
        funcion = new Funcion("Matrix");
        sala = new Sala(5, 5);
    }

    @Test
    void testAgregarHorario() {
        funcion.agregarHorario("18:00", sala);
        Map<String, Sala> horarios = funcion.getHorarios();
        assertTrue(horarios.containsKey("18:00"));
        assertEquals(sala, horarios.get("18:00"));
    }

    @Test
    void testObtenerSalaValida() throws CinemaException {
        funcion.agregarHorario("18:00", sala);
        Sala obtenida = funcion.obtenerSala("18:00");
        assertEquals(sala, obtenida);
    }

    @Test
    void testObtenerSalaInvalida() {
        CinemaException exception = assertThrows(CinemaException.class, () -> {
            funcion.obtenerSala("20:00");
        });
        assertEquals("Horario no valido para la funcion seleccionada", exception.getMessage());
    }

    @Test
    void testLiberarAsientos() throws CinemaException {
        funcion.agregarHorario("18:00", sala);
        List<Reserva> reservas = new ArrayList<>();
        reservas.add(new Reserva(2, 3));
        funcion.liberarAsientos("18:00", reservas);
        assertFalse(sala.estaOcupado(1, 2)); // Verificar que el asiento fue liberado
    }

    @Test
    void testLiberarAsientosHorarioInvalido() {
        List<Reserva> reservas = new ArrayList<>();
        reservas.add(new Reserva(2, 3));
        CinemaException exception = assertThrows(CinemaException.class, () -> {
            funcion.liberarAsientos("20:00", reservas);
        });
        assertEquals("Horario no valido para la funcion seleccionada", exception.getMessage());
    }
}
