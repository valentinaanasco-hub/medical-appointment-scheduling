
package co.unicauca.piedrazul.main;

import co.unicauca.piedrazul.domain.access.IUserRepository;
import co.unicauca.piedrazul.domain.entities.User;
import co.unicauca.piedrazul.infrastructure.persistence.SqliteUserRepository;
import co.unicauca.piedrazul.infrastructure.persistence.SqliteConnection;
import co.unicauca.piedrazul.domain.services.UserService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Valentina Añasco 
 * @author Camila Dorado
 * @author Felipe Gutierrez
 * @author Ginner Ortega
 * @author Santiago Solarte 
 */

public class Main {

    public static void main(String[] args) {

        System.out.println("=== INICIANDO PRUEBA DE CONEXIÓN ===");

        // 🔎 1️⃣ Verificar conexión directa
        try (Connection conn = SqliteConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            if (conn != null && !conn.isClosed()) {
                System.out.println("✔ Conexión establecida correctamente.");
            }

            // 🔎 2️⃣ Verificar que la tabla USER exista
            ResultSet rs = stmt.executeQuery(
                "SELECT name FROM sqlite_master WHERE type='table' AND name='USER';"
            );

            if (rs.next()) {
                System.out.println("✔ Tabla USER existe.");
            } else {
                System.out.println("❌ Tabla USER no existe.");
            }

        } catch (Exception e) {
            System.out.println("❌ Error en conexión: " + e.getMessage());
            return;
        }

        System.out.println("\n=== PRUEBAS DE DOMINIO ===");

        // 3️⃣ Instanciamos la infraestructura
        IUserRepository repository = new SqliteUserRepository();

        // 4️⃣ Inyectamos en el servicio (DIP)
        UserService service = new UserService(repository);

        // 5️⃣ PRUEBA DE REGISTRO
        System.out.println("\n--- Probando Registro de Usuario ---");

        User newUser = new User();
        newUser.setId(1); // IMPORTANTE: tu tabla no es AUTOINCREMENT
        newUser.setUsername("ssolarte");
        newUser.setFirstName("Santiago");
        newUser.setMiddleName("Andrés");
        newUser.setFirstSurname("Solarte");
        newUser.setLastName("Apellido2");
        newUser.setUserTypeId("CC");
        newUser.setState("ACTIVE");
        newUser.setRoleId(1);

        String respuesta = service.registerUser(newUser, "Clave123!");
        System.out.println("Resultado Registro: " + respuesta);

        // 6️⃣ PRUEBA DE LOGIN
        System.out.println("\n--- Probando Login ---");

        User loggedUser = service.login("ssolarte", "Clave123!");

        if (loggedUser != null) {
            System.out.println("✔ Login exitoso. Bienvenido: " 
                               + loggedUser.getFullName());
        } else {
            System.out.println("❌ Login fallido.");
        }
    }
}