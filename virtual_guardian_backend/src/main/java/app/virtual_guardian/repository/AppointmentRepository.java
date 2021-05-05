package app.virtual_guardian.repository;

import app.virtual_guardian.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Query(value = "SELECT * FROM patient_appointments WHERE patient_id = :patientId", nativeQuery = true)
    List<Appointment> getListOfPatientAppointments(@Param("patientId") String patientId);
}
