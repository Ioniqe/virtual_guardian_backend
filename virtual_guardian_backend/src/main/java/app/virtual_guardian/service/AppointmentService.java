package app.virtual_guardian.service;

import app.virtual_guardian.entity.Appointment;
import app.virtual_guardian.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public void insertAppointment(Appointment appointment){
        appointmentRepository.save(appointment);
    }

    public List<Appointment> getAllAppointmentsOfPatient(String userId){
        return appointmentRepository.getListOfPatientAppointments(userId);
    }

    public Appointment getAppointmentById(Integer appointmentId){
        Optional<Appointment> appointment =  appointmentRepository.findById(appointmentId);
        return appointment.orElse(null);
    }

    public void deleteAppointment(Appointment appointment){
        appointmentRepository.delete(appointment);
    }
}
