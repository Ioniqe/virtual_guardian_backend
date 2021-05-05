package app.virtual_guardian.dto.builder;

import app.virtual_guardian.dto.AppointmentDTO;
import app.virtual_guardian.entity.Appointment;
import app.virtual_guardian.entity.Patient;

import java.util.ArrayList;
import java.util.List;

public class AppointmentBuilder {
    public static Appointment toEntity(AppointmentDTO appointmentDTO, Patient patient){
        return new Appointment(appointmentDTO.getAppointment(), appointmentDTO.getTitle(), appointmentDTO.getInfo(), patient);
    }

    public static AppointmentDTO toAppointmentDTO(Appointment appointment){
        return new AppointmentDTO(appointment.getId(), appointment.getAppointment(),
                appointment.getTitle(), appointment.getInfo());
    }

    public static List<AppointmentDTO> toAppointmentDTOsList(List<Appointment> appointmentsOfPatient){
        List<AppointmentDTO> appointmentsOfPatientDTO = new ArrayList<>();
        appointmentsOfPatient.forEach(appointment -> appointmentsOfPatientDTO.add(toAppointmentDTO(appointment)));
        return appointmentsOfPatientDTO;

    }
}
