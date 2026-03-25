package co.unicauca.piedrazul.domain.services.validators;

import co.unicauca.piedrazul.domain.entities.Doctor;
import co.unicauca.piedrazul.domain.entities.User;

/**
 * @author Valentina Añasco 
 * @author Camila Dorado
 * @author Felipe Gutierrez
 * @author Ginner Ortega
 * @author Santiago Solarte 
 */

public class DoctorValidator {
    private final UserValidator userValidator;
    public DoctorValidator(UserValidator userValidator){
        this.userValidator = userValidator;
    }
    public void validate(Doctor doctor) {
        userValidator.validate((User)doctor);
        if (doctor.getProfessionalId() == null || doctor.getProfessionalId().trim().isEmpty())
            throw new IllegalArgumentException("El id profesional es obligatorio");
    }
}