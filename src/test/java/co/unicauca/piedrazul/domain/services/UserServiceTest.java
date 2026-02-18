package co.unicauca.piedrazul.domain.services;

import co.unicauca.piedrazul.domain.access.IUserRepository;
import co.unicauca.piedrazul.domain.entities.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 * Pruebas unitarias para la lógica de negocio de Usuarios.
 * @author Santiago Solarte
 */
public class UserServiceTest {

    private UserService service;
    private IUserRepository mockRepository;

    @Before
    public void setUp() {
        // Creamos un repositorio "en memoria" para no afectar la DB real durante las pruebas
        mockRepository = new IUserRepository() {
            private final List<User> users = new ArrayList<>();

            @Override
            public boolean save(User user) {
                return users.add(user);
            }

            @Override
            public User findByUsername(String username) {
                return users.stream()
                        .filter(u -> u.getUsername().equals(username))
                        .findFirst()
                        .orElse(null);
            }

            @Override
            public List<User> findAll() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public boolean update(User user) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        service = new UserService(mockRepository);
    }

    @Test
    public void testRegisterUserSuccess() {
        System.out.println("Caso: Registro Exitoso con Contraseña Fuerte");
        User user = new User();
        user.setUsername("santiago.test");
        
        // Esta clave cumple: >6 caracteres, 1 Mayus, 1 Num, 1 Especial
        String result = service.registerUser(user, "Admin123!");
        
        assertEquals("SUCCESS", result);
    }

    @Test
    public void testRegisterUserWeakPassword() {
        System.out.println("Caso: Registro Fallido por Contraseña Débil");
        User user = new User();
        user.setUsername("user.debil");

        // Falla por no tener carácter especial y ser muy corta
        String result = service.registerUser(user, "12345");
        
        assertTrue(result.contains("La contraseña no cumple los requisitos"));
    }

    @Test
    public void testRegisterDuplicateUsername() {
        System.out.println("Caso: Registro Fallido por Usuario Duplicado");
        User user1 = new User();
        user1.setUsername("duplicado");
        service.registerUser(user1, "Clave123!");

        User user2 = new User();
        user2.setUsername("duplicado");
        String result = service.registerUser(user2, "OtraClave123!");

        assertEquals("El nombre de usuario ya existe.", result);
    }

    @Test
    public void testLoginSuccess() {
        System.out.println("Caso: Login Exitoso");
        User user = new User();
        user.setUsername("juanito");
        user.setFirstName("Juan");
        service.registerUser(user, "Juanito777!");

        User result = service.login("juanito", "Juanito777!");
        
        assertNotNull(result);
        assertEquals("Juan", result.getFirstName());
    }

    @Test
    public void testLoginWrongPassword() {
        System.out.println("Caso: Login Fallido por Contraseña Incorrecta");
        User user = new User();
        user.setUsername("pedro");
        service.registerUser(user, "Pedro123!");

        // Intentamos con la clave equivocada
        User result = service.login("pedro", "incorrecta");
        
        assertNull(result);
    }
}