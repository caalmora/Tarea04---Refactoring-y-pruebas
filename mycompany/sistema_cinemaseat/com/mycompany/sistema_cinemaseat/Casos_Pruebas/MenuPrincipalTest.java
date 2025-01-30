import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.stream.Stream;
import com.mycompany.sistema_cinemaseat.*


@ExtendWith(MockitoExtension.class) //se usa esta libreria para simular objetos y no estar instanciando
class MenuPrincipalTest {

    @Mock
    private Scanner scanner;
    @Mock
    private IPersistenciaUsuarios repoUsuarios;
    @Mock
    private IGestorFunciones gestorFunciones;
    @Mock
    private INotificador notificador;
    @Mock
    private List<Usuario> usuarios;

    @InjectMocks
    private MenuPrincipal menuPrincipal;

    // Tests for mostrarMenu()
    @Test
    void testMostrarMenu_ValidOption() throws CinemaException {
        // Arrange
        when(scanner.nextLine()).thenReturn("4");
        
        // Act
        menuPrincipal.mostrarMenu();
        
        // Assert
        verify(repoUsuarios).guardarUsuarios(usuarios);
    }

    @Test
    void testMostrarMenu_InvalidOption() throws CinemaException {
        // Arrange
        when(scanner.nextLine()).thenReturn("5").thenReturn("4");
        
        // Act
        menuPrincipal.mostrarMenu();
        
        // Assert
        verify(repoUsuarios, times(1)).guardarUsuarios(usuarios);
        verify(scanner, times(2)).nextLine();
    }

    @Test
    void testMostrarMenu_InvalidInput() throws CinemaException {
        // Arrange
        when(scanner.nextLine()).thenReturn("abc").thenReturn("4");
        
        // Act
        menuPrincipal.mostrarMenu();
        
        // Assert
        verify(scanner, times(2)).nextLine();
    }

    @Test
    void testMostrarMenu_ExceptionHandling() throws CinemaException {
        // Arrange
        when(scanner.nextLine()).thenReturn("4");
        doThrow(new CinemaException("Error")).when(repoUsuarios).guardarUsuarios(anyList());

        // Act & Assert
        assertThrows(CinemaException.class, () -> menuPrincipal.mostrarMenu());
    }

    // Tests for gestionarCliente()
    @Test
    void testGestionarCliente_ValidUser() throws CinemaException {
        // Arrange
        Usuario clienteValido = mock(Cliente.class);
        when(scanner.nextLine()).thenReturn("usuario").thenReturn("clave");
        when(usuarios.stream()).thenReturn(Stream.of(clienteValido));

        // Act
        menuPrincipal.gestionarCliente();
        
        // Assert
        verify(clienteValido).manejarAcciones(any());
    }

    @Test
    void testGestionarCliente_InvalidUser() throws CinemaException {
        // Arrange
        when(scanner.nextLine()).thenReturn("usuario").thenReturn("clave");
        when(usuarios.stream()).thenReturn(Stream.empty());
        
        // Act
        menuPrincipal.gestionarCliente();
        
        // Assert
        verifyNoInteractions(gestorFunciones);
    }

    @Test
    void testGestionarCliente_AuthFailure() throws CinemaException {
        // Arrange
        when(scanner.nextLine()).thenReturn("incorrecto").thenReturn("clave");
        
        // Act
        menuPrincipal.gestionarCliente();
        
        // Assert
        verify(gestorFunciones, never()).manejarAcciones(any());
    }

    @Test
    void testGestionarCliente_Exception() throws CinemaException {
        // Arrange
        when(scanner.nextLine()).thenReturn("usuario").thenReturn("clave");
        doThrow(new CinemaException("Error")).when(gestorFunciones).manejarAcciones(any());

        // Act & Assert
        assertThrows(CinemaException.class, () -> menuPrincipal.gestionarCliente());
    }

    // Tests for gestionarAdministrador()
    @Test
    void testGestionarAdministrador_ValidAdmin() throws CinemaException {
        // Arrange
        Usuario adminValido = mock(Administrador.class);
        when(scanner.nextLine()).thenReturn("admin").thenReturn("adminpass");
        when(usuarios.stream()).thenReturn(Stream.of(adminValido));

        // Act
        menuPrincipal.gestionarAdministrador();
        
        // Assert
        verify(adminValido).manejarAcciones(any());
    }

    @Test
    void testGestionarAdministrador_InvalidAdmin() throws CinemaException {
        // Arrange
        when(scanner.nextLine()).thenReturn("admin").thenReturn("adminpass");
        when(usuarios.stream()).thenReturn(Stream.empty());

        // Act
        menuPrincipal.gestionarAdministrador();

        // Assert
        verifyNoInteractions(gestorFunciones);
    }

    @Test
    void testGestionarAdministrador_AuthFailure() throws CinemaException {
        // Arrange
        when(scanner.nextLine()).thenReturn("admin").thenReturn("wrongpass");
        
        // Act
        menuPrincipal.gestionarAdministrador();

        // Assert
        verify(gestorFunciones, never()).manejarAcciones(any());
    }

    @Test
    void testGestionarAdministrador_Exception() throws CinemaException {
        // Arrange
        when(scanner.nextLine()).thenReturn("admin").thenReturn("adminpass");
        doThrow(new CinemaException("Error")).when(gestorFunciones).manejarAcciones(any());

        // Act & Assert
        assertThrows(CinemaException.class, () -> menuPrincipal.gestionarAdministrador());
    }

    // Tests for crearUsuario()
    @Test
    void testCrearUsuario_Success() {
        // Arrange
        when(scanner.nextLine()).thenReturn("1", "Nombre", "correo@domain.com", "usuario1", "clave123");
        
        // Act
        menuPrincipal.crearUsuario();
        
        // Assert
        verify(repoUsuarios).guardarUsuarios(any());
    }

    @Test
    void testCrearUsuario_AdminSuccess() {
        // Arrange
        when(scanner.nextLine()).thenReturn("2", "NombreAdmin", "admin@domain.com", "admin1", "adminpass");

        // Act
        menuPrincipal.crearUsuario();

        // Assert
        verify(repoUsuarios).guardarUsuarios(any());
    }

    @Test
    void testCrearUsuario_InvalidInput() {
        // Arrange
        when(scanner.nextLine()).thenReturn("a", "Nombre", "correo@domain.com", "usuario1", "clave123");
        
        // Act
        menuPrincipal.crearUsuario();
        
        // Assert
        verify(repoUsuarios, never()).guardarUsuarios(any());
    }

    @Test
    void testCrearUsuario_Exception() {
        // Arrange
        when(scanner.nextLine()).thenReturn("1", "Nombre", "correo@domain.com", "usuario1", "clave123");
        doThrow(new RuntimeException("Error")).when(repoUsuarios).guardarUsuarios(anyList());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> menuPrincipal.crearUsuario());
    }
}
