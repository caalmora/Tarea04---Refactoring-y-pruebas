import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.mycompany.sistema_cinemaseat.*

class ReservaTest {
    private Reserva reserva;

    @BeforeEach
    void setUp() {
        reserva = new Reserva("Usuario", new Funcion("Pelicula", "19:00", new Sala(3, 60)));
    }

    @Test
    void testConfirmarReservaNormal() {
        assertDoesNotThrow(() -> reserva.confirmar());
    }

    @Test
    void testConfirmarReservaNormal2() {
        reserva.confirmar();
        assertTrue(reserva.estaConfirmada());
    }

    @Test
    void testConfirmarReservaError() {
        reserva = new Reserva("", new Funcion("Pelicula", "19:00", new Sala(3, 60)));
        assertThrows(IllegalArgumentException.class, () -> reserva.confirmar());
    }

    @Test
    void testConfirmarReservaError2() {
        reserva = new Reserva(null, new Funcion("Pelicula", "19:00", new Sala(3, 60)));
        assertThrows(IllegalArgumentException.class, () -> reserva.confirmar());
    }
}
