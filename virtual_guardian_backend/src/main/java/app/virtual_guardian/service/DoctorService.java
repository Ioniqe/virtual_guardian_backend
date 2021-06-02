package app.virtual_guardian.service;

import app.virtual_guardian.dto.builder.DoctorBuilder;
import app.virtual_guardian.entity.Doctor;
import app.virtual_guardian.entity.User;
import app.virtual_guardian.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public void insertDoctor(User user){
        Doctor doctor = DoctorBuilder.toEntity(user);
        doctorRepository.save(doctor);
    }

    private Optional<Doctor> verifyDoctorExistence(String id) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(id);
        if (!doctorOptional.isPresent()) {
            return Optional.empty();
        }
        return doctorOptional;
    }

    public Doctor getDoctorById(String id) {
        Optional<Doctor> doctorOptional = verifyDoctorExistence(id);
        return doctorOptional.orElse(null);
    }

}
