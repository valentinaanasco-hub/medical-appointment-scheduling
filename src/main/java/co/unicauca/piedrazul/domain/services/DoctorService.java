package co.unicauca.piedrazul.domain.services;

import co.unicauca.piedrazul.domain.access.IDoctorRepository;
import co.unicauca.piedrazul.domain.entities.Doctor;
import co.unicauca.piedrazul.domain.services.validators.DoctorValidator;
import java.util.List;

/**
 * @author Valentina Añasco 
 * @author Camila Dorado
 * @author Felipe Gutierrez
 * @author Ginner Ortega
 * @author Santiago Solarte 
 */

public class DoctorService {
    
    private final IDoctorRepository doctorRepository;
    private final DoctorValidator doctorValidator;

    public DoctorService(IDoctorRepository doctorRepository, DoctorValidator doctorValidator) {
        this.doctorRepository = doctorRepository;
        this.doctorValidator = doctorValidator;
    }

    public boolean registerDoctor(Doctor doctor) {
        doctorValidator.validate(doctor);
        return doctorRepository.save(doctor);
    }

    public Doctor findDoctor(int id) {
        Doctor doctor = doctorRepository.findById(id);
        if (doctor == null){
            throw new IllegalArgumentException("Médico no encontrado");
        }
        return doctor;
    }

    public List<Doctor> listActiveDoctors() {
        
        List<Doctor> doctors = doctorRepository.findAllActive();
        if(doctors.isEmpty()){
            throw new IllegalArgumentException("No hay registros de medicos");
        }
        
        return doctors;
    }

    public boolean modifyDoctor(Doctor doctor) {
        if (doctorRepository.findById(doctor.getId()) == null)
            throw new IllegalArgumentException("Médico no encontrado");
        return doctorRepository.update(doctor);
    }

    public boolean deactivateDoctor(int id) {
        // Desactiva en lugar de eliminar para conservar historial de citas
        if (doctorRepository.findById(id) == null)
            throw new IllegalArgumentException("Médico no encontrado");
        return doctorRepository.deactivate(id);
    }
}
