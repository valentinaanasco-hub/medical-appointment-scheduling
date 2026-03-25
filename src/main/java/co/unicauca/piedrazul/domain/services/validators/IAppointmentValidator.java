
package co.unicauca.piedrazul.domain.services.validators;

import co.unicauca.piedrazul.domain.entities.Appointment;
import co.unicauca.piedrazul.domain.entities.Doctor;
import co.unicauca.piedrazul.domain.entities.Patient;
import java.util.List;

/**
 *
 * @author santi
 */
public interface IAppointmentValidator {
    void validate(Appointment appointment, Doctor doctor,Patient patient, List<Appointment> existing);
}
