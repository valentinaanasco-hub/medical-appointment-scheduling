package co.unicauca.piedrazul.domain.services.validators;

import co.unicauca.piedrazul.domain.entities.Appointment;
import co.unicauca.piedrazul.domain.entities.Doctor;
import co.unicauca.piedrazul.domain.entities.Patient;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


/**
 * @author Valentina Añasco 
 * @author Camila Dorado
 * @author Felipe Gutierrez
 * @author Ginner Ortega
 * @author Santiago Solarte 
 */

public class ManualAppointmentValidator implements IAppointmentValidator {
  @Override
    public void validate(Appointment appointment, Doctor doctor,
                          Patient patient, List<Appointment> existing) {
        validateDate(appointment.getDate());
        validateTimes(appointment.getStartTime(), appointment.getEndTime());
        validateDoctor(doctor);
        validatePatient(patient);
        validateTimeConflict(appointment, existing);
    }

    private void validateDate(LocalDate date) {
        if (date == null)
            throw new IllegalArgumentException("La fecha es obligatoria");
        if (date.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("La fecha no puede ser en el pasado");
    }

    private void validateTimes(LocalTime start, LocalTime end) {
        if (start == null || end == null)
            throw new IllegalArgumentException("Las horas son obligatorias");
        if (start.isAfter(end))
            throw new IllegalArgumentException("La hora de inicio debe ser antes que la de fin");
    }

    private void validateDoctor(Doctor doctor) {
        if (doctor == null)
            throw new IllegalArgumentException("El médico no existe");
    }

    private void validatePatient(Patient patient) {
        if (patient == null)
            throw new IllegalArgumentException("El paciente no existe");
    }

    private void validateTimeConflict(Appointment appointment, List<Appointment> existing) {
        for (Appointment a : existing) {
            if (a.getAppointmentId() == appointment.getAppointmentId())
                continue;
            // Solo verifica que no haya otra cita con la misma hora de inicio
            if (appointment.getStartTime().equals(a.getStartTime()))
                throw new IllegalArgumentException("El médico ya tiene una cita en ese horario");
        }
    }
}
