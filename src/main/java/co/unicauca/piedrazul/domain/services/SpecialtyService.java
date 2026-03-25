package co.unicauca.piedrazul.domain.services;

import co.unicauca.piedrazul.domain.access.ISpecialtyRepository;
import co.unicauca.piedrazul.domain.entities.Specialty;
import java.util.List;

/**
 * @author Valentina Añasco
 * @author Camila Dorado
 * @author Felipe Gutierrez
 * @author Ginner Ortega
 * @author Santiago Solarte
 */
public class SpecialtyService {

    private final ISpecialtyRepository specialtyRepository;

    public SpecialtyService(ISpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }
    
    //Registra 
    public Specialty findByName(String name) {
        Specialty specialty = specialtyRepository.findByName(name);
        if (specialty == null)
            throw new IllegalArgumentException("Especialidad no encontrada");
        return specialty;
    }

    public Specialty findSpecialty(int id) {
        Specialty specialty = specialtyRepository.findById(id);
        if (specialty == null)
            throw new IllegalArgumentException("Especialidad no encontrada");
        return specialty;
    }

    public List<Specialty> listSpecialties() {
        List<Specialty> specialties = specialtyRepository.findAll();
        if (specialties.isEmpty())
            throw new IllegalArgumentException("No hay especialidades registradas");
        return specialties;
    }

    public boolean assignSpecialtyToDoctor(int doctorId, int specialtyId) {
        if (specialtyRepository.findById(specialtyId) == null)
            throw new IllegalArgumentException("Especialidad no encontrada");
        return specialtyRepository.assignSpecialtyToDoctor(doctorId, specialtyId);
    }

    public List<Specialty> findByDoctorId(int doctorId) {
        return specialtyRepository.findSpecialtiesByDoctorId(doctorId);
    }
}
