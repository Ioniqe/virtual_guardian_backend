package app.virtual_guardian.controller;

import app.virtual_guardian.dto.AppointmentDTO;
import app.virtual_guardian.dto.builder.AppointmentBuilder;
import app.virtual_guardian.entity.Appointment;
import app.virtual_guardian.entity.Patient;
import app.virtual_guardian.service.AppointmentService;
import app.virtual_guardian.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*", exposedHeaders = "Authorization")
public class AppointmentController {

    private final PatientService patientService;
    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(PatientService patientService, AppointmentService appointmentService) {
        this.patientService = patientService;
        this.appointmentService = appointmentService;
    }

    //----------------------------------CREATE-----------------------------
    @RequestMapping(value = "/appointment/new/{userId}", method = RequestMethod.POST)
    public ResponseEntity createAppointment(@PathVariable("userId") String userId, @RequestBody AppointmentDTO appointmentDTO) {
        Patient patient = patientService.getPatient(userId);

        if(patient == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Appointment appointment = AppointmentBuilder.toEntity(appointmentDTO, patient);
        appointmentService.insertAppointment(appointment);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //----------------------------------READ-----------------------------
    @RequestMapping(value = "/appointments/get/{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsList(@PathVariable("userId") String userId) {
        Patient patient = patientService.getPatient(userId);

        if(patient == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<Appointment> appointmentList = appointmentService.getAllAppointmentsOfPatient(userId);
        List<AppointmentDTO> appointmentListDTO = AppointmentBuilder.toAppointmentDTOsList(appointmentList);
        return new ResponseEntity<>(appointmentListDTO, HttpStatus.OK);
    }

    //----------------------------------UPDATE-----------------------------
    @RequestMapping(value = "/appointment/update", method = RequestMethod.PUT)
    public ResponseEntity updateAppointment(@RequestBody AppointmentDTO newAppointmentDTO) {
        Appointment appointment = appointmentService.getAppointmentById(newAppointmentDTO.getId());
        if(appointment == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        appointment.setAppointment(newAppointmentDTO.getAppointment());
        appointment.setTitle(newAppointmentDTO.getTitle());
        appointment.setInfo(newAppointmentDTO.getInfo());

        appointmentService.insertAppointment(appointment);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //----------------------------------DELETE-----------------------------
    @RequestMapping(value = "/appointment/delete/{appointmentId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteAppointment(@PathVariable("appointmentId") Integer appointmentId) {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);

        if(appointment == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        appointmentService.deleteAppointment(appointment);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
