package co.unicauca.piedrazul.domain.services;
import co.unicauca.piedrazul.domain.access.IAppointmentRepository;
import co.unicauca.piedrazul.domain.access.IDoctorRepository;
import co.unicauca.piedrazul.domain.access.IPatientRepository;
import co.unicauca.piedrazul.domain.entities.Appointment;
import co.unicauca.piedrazul.domain.entities.Doctor;
import co.unicauca.piedrazul.domain.entities.Patient;
import co.unicauca.piedrazul.domain.entities.enums.AppointmentState;
import co.unicauca.piedrazul.domain.services.validators.IAppointmentValidator;
import java.util.List;

public class AppointmentService {

    private final IAppointmentRepository appointmentRepository;
    private final IDoctorRepository doctorRepository;
    private final IPatientRepository patientRepository;
    private final IAppointmentValidator validator; 

    public AppointmentService(IAppointmentRepository appointmentRepository,
                               IDoctorRepository doctorRepository,
                               IPatientRepository patientRepository,
                               IAppointmentValidator validator) { 
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.validator = validator;
    }

    public boolean scheduleAppointment(Appointment appointment) {
        Doctor doctor = doctorRepository.findById(appointment.getDoctor().getId());
        Patient patient = patientRepository.findById(appointment.getPatient().getId());
        List<Appointment> existing = appointmentRepository
            .findByDoctorAndDate(appointment.getDoctor().getId(),
                                  appointment.getDate().toString());

        validator.validate(appointment, doctor, patient, existing); 

        appointment.setStatus(AppointmentState.AGENDADA); 
        return appointmentRepository.save(appointment);
    }

    public boolean rescheduleAppointment(Appointment appointment) {
        if (appointmentRepository.findById(appointment.getAppointmentId()) == null)
            throw new IllegalArgumentException("Cita no encontrada");

        Doctor doctor = doctorRepository.findById(appointment.getDoctor().getId());
        Patient patient = patientRepository.findById(appointment.getPatient().getId());
        List<Appointment> existing = appointmentRepository.findByDoctorAndDate(appointment.getDoctor().getId(),
                                  appointment.getDate().toString());

        validator.validate(appointment, doctor, patient, existing); 

        appointment.setStatus(AppointmentState.REAGENDADA); 
        return appointmentRepository.update(appointment);
    }

    public boolean cancelAppointment(int id) {
        Appointment appointment = findAppointment(id);
        appointment.setStatus(AppointmentState.CANCELADA);
        return appointmentRepository.update(appointment);
    }

    public boolean markAsAttended(int id) {
        Appointment appointment = findAppointment(id);
        appointment.setStatus(AppointmentState.ATENDIDA); 
        return appointmentRepository.update(appointment);
    }

    public Appointment findAppointment(int id) {
        Appointment appointment = appointmentRepository.findById(id);
        if (appointment == null)
            throw new IllegalArgumentException("Cita no encontrada");
        return appointment;
    }

    public List<Appointment> listAppointments() {
        return appointmentRepository.findAll();
    }
}