package co.unicauca.piedrazul.domain.entities;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pruebas unitarias para la entidad User. Verifica la lógica de manipulación de
 * nombres y estados.
 *
 * * @author Santiago Solarte
 */
public class UserTest {

    @Test
    public void testGetFullNameComplete() {
        System.out.println("Caso: Nombre Completo con todos los campos");
        User user = new User();
        user.setFirstName("Juan");
        user.setMiddleName("Carlos");
        user.setFirstSurname("Perez");
        user.setLastName("Rodriguez");

        String result = user.getFullName();
        assertEquals("Juan Carlos Perez Rodriguez", result);
    }

    @Test
    public void testGetFullNameOnlyRequiredFields() {
        User user = new User();
        user.setFirstName("Santiago");
        user.setFirstSurname("Solarte");
        // No seteamos los segundos campos

        String result = user.getFullName();

        String cleanResult = result.trim().replaceAll("\\s+", " ");

        assertEquals("Santiago Solarte", cleanResult);
    }

    @Test
    public void testGetFullNameHandlesNulls() {
        System.out.println("Caso: Manejo de nulos en nombres para evitar excepciones");
        User user = new User();
        // Solo seteamos el primer nombre y apellido
        user.setFirstName("Maria");
        user.setFirstSurname("Lopez");

        try {
            String result = user.getFullName();
            assertNotNull(result);
            assertTrue(result.contains("Maria"));
            assertTrue(result.contains("Lopez"));
        } catch (NullPointerException e) {
            fail("El método getFullName() no debería lanzar NullPointerException con campos nulos.");
        }
    }

    @Test
    public void testSetAndGetProperties() {
        System.out.println("Caso: Prueba de Getters y Setters básicos");
        User user = new User();
        user.setId(100);
        user.setUsername("santiago2026");
        user.setState("ACTIVE");

        assertEquals(100, user.getId());
        assertEquals("santiago2026", user.getUsername());
        assertEquals("ACTIVE", user.getState());
    }
}
