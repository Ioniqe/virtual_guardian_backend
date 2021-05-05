package app.virtual_guardian.service;

import app.virtual_guardian.dto.PatientDTO;
import app.virtual_guardian.dto.builder.PatientBuilder;
import app.virtual_guardian.entity.Caregiver;
import app.virtual_guardian.entity.Doctor;
import app.virtual_guardian.entity.Patient;
import app.virtual_guardian.entity.User;
import app.virtual_guardian.repository.CaregiverRepository;
import app.virtual_guardian.repository.DoctorRepository;
import app.virtual_guardian.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final CaregiverRepository caregiverRepository;

    @Autowired
    public PatientService(PatientRepository patientRepository, DoctorRepository doctorRepository, CaregiverRepository caregiverRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.caregiverRepository = caregiverRepository;
    }

    public void saveUpdatedPatient(Patient patient){
        patientRepository.save(patient);
    }

    public void insertPatient(User user, Doctor doctor, String address){
        Patient patient = PatientBuilder.toEntity(user, doctor, address);
        patient = patientRepository.save(patient);
        System.out.println("Patient with id {} was inserted in db" + patient.getUserId());

        Set<Patient> newPatientList = doctor.getListOfPatients();
        newPatientList.add(patient);
        doctor.setListOfPatients(newPatientList);
        doctor = doctorRepository.save(doctor);
        System.out.println("Doctor with id {} has inserted the patient with id {} in its patients list" + doctor.getUserId() + patient.getUserId());
    }

    public void insertPatient(Patient patient){
        patientRepository.save(patient);
    }

    public Patient getPatient(String id){
        return patientRepository.findPatientByUserId(id);
    }

    public void updatePatient(PatientDTO patientDTO){
        Patient patient = patientRepository.findPatientByUserId(patientDTO.getId());
        if(!patient.getAddress().equals(patientDTO.getAddress())){
            patient.setAddress(patientDTO.getAddress());
            patientRepository.save(patient);
        }
    }

    public void setCaregiverToPatient(Patient patient, Caregiver caregiver){
        patient.setCaregiver(caregiver);
        patient = patientRepository.save(patient);
        System.out.println("Patient with id " +  patient.getUserId() +  " has saved caregiver with id " + caregiver.getUserId());

        Set<Patient> newListOfPatients = caregiver.getListOfPatients();
        newListOfPatients.add(patient);
        caregiver.setListOfPatients(newListOfPatients);
        caregiver = caregiverRepository.save(caregiver);
        System.out.println("Caregiver with id " + caregiver.getUserId() + "  has saved patient with id {} " +  patient.getUserId());
    }
}
