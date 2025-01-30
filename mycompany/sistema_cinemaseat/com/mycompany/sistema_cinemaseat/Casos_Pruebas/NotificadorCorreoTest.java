import com.mycompany.sistema_cinemaseat.*
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.Arrays;
import java.util.List;

class NotificadorCorreoTest {

    private NotificadorCorreo notificadorCorreo;

    @BeforeEach
    void setUp() {
        notificadorCorreo = new NotificadorCorreo();
    }

    // Prueba normal para enviarNotificacion
    @Test
    void testEnviarNotificacion() {
        // Configurar mock de la clase ArchivoCorreo
        ArchivoCorreo archivoCorreoMock = mock(ArchivoCorreo.class);
        notificadorCorreo.enviarNotificacion("test@correo.com", "Mensaje de prueba");

        // Verificar que se llamó a escribirNotificacion
        verify(archivoCorreoMock).escribirNotificacion("test@correo.com", "Mensaje de prueba");
    }

    // Prueba normal para notificar
    @Test
    void testNotificar() {
        // Configurar mock de la clase ArchivoCorreo
        ArchivoCorreo archivoCorreoMock = mock(ArchivoCorreo.class);
        List<Cliente> clientes = Arrays.asList(new Cliente("Juan"), new Cliente("Maria"));

        notificadorCorreo.notificar(clientes, "Mensaje para clientes");

        // Verificar que se llamó a notificarAClientes con la lista de clientes y el mensaje
        verify(archivoCorreoMock).notificarAClientes(clientes, "Mensaje para clientes");
    }

    // Prueba de error para enviarNotificacion con correo nulo
    @Test
    void testEnviarNotificacionCorreoNulo() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            notificadorCorreo.enviarNotificacion(null, "Mensaje con correo nulo");
        });
    }

    // Prueba de error para notificar con lista de clientes nula
    @Test
    void testNotificarListaNula() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            notificadorCorreo.notificar(null, "Mensaje con lista nula");
        });
    }
}

