package co.unicauca.piedrazul.domain.services;

import co.unicauca.piedrazul.domain.entities.User;
import java.util.List;
import co.unicauca.piedrazul.domain.access.IUserRepository;
import co.unicauca.piedrazul.domain.entities.enums.UserState;

/**
 * @author Valentina Añasco 
 * @author Camila Dorado
 * @author Felipe Gutierrez
 * @author Ginner Ortega
 * @author Santiago Solarte 
 */

public class UserService {
    private final IUserRepository userRepository;

    // Inyección de dependencia por constructor
    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean registerUser(User user) {

        // Verifica que el username no esté en uso
        if (userRepository.findByUsername(user.getUsername()) != null)
            throw new IllegalArgumentException("El nombre de usuario ya existe");

        return userRepository.save(user);
    }

    public User login(String username, String password) {
        // Busca el usuario por su nombre de usuario
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new IllegalArgumentException("Usuario no encontrado");

        // Verifica que la contraseña coincida
        if (!user.getPassword().equals(password))
            throw new IllegalArgumentException("Contraseña incorrecta");

        // Verifica que el usuario esté activo
        if (user.getState().equals(UserState.INACTIVO))
            throw new IllegalArgumentException("Usuario inactivo");

        return user;
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public User findUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new IllegalArgumentException("Usuario no encontrado");
        return user;
    }

    public boolean modifyUser(User user) {
        // Verifica que el usuario exista antes de modificarlo
        if (userRepository.findByUsername(user.getUsername()) == null)
            throw new IllegalArgumentException("Usuario no encontrado");
        return userRepository.update(user);
    }

    public boolean deactivateUser(int id) {
        return userRepository.deactivate(id);
    }
}
