/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.piedrazul.domain.services.validators;

import co.unicauca.piedrazul.domain.entities.User;
import co.unicauca.piedrazul.domain.entities.enums.UserState;

/**
 *
 * @author santi
 */
public class UserValidator {
    public void validate(User user){
        // Valida que los campos obligatorios no estén vacíos
        if (user.getUsername() == null || user.getUsername().trim().isEmpty())
            throw new IllegalArgumentException("El nombre de usuario es obligatorio");
        if (user.getPassword() == null || user.getPassword().trim().isEmpty())
            throw new IllegalArgumentException("La contraseña es obligatoria");
        if (user.getFirstName() == null || user.getFirstName().trim().isEmpty())
            throw new IllegalArgumentException("El nombre es obligatorio");
        if (user.getFirstSurname() == null || user.getFirstSurname().trim().isEmpty())
            throw new IllegalArgumentException("El apellido es obligatorio");
        if(user.getRole().isEmpty()){
            throw new IllegalArgumentException("El usuario debe tener almenos un rol");
        }
            
    }
}
