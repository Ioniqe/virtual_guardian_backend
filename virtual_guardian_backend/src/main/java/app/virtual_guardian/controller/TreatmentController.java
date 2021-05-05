package app.virtual_guardian.controller;

import app.virtual_guardian.dto.TreatmentDTO;
import app.virtual_guardian.dto.builder.TreatmentBuilder;
import app.virtual_guardian.entity.Patient;
import app.virtual_guardian.entity.Treatment;
import app.virtual_guardian.service.PatientService;
import app.virtual_guardian.service.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*", exposedHeaders = "Authorization")
@CrossOrigin(origins = "*")
public class TreatmentController {
    private final TreatmentService treatmentService;
    private final PatientService patientService;

    @Autowired
    public TreatmentController(TreatmentService treatmentService, PatientService patientService) {
        this.treatmentService = treatmentService;
        this.patientService = patientService;
    }

    //---------------------------------CREATE---------------------------------
    @RequestMapping(value = "/treatment/new/{userId}", method = RequestMethod.POST)
    public ResponseEntity createTreatment(@PathVariable("userId") String userId, @RequestBody TreatmentDTO treatmentDTO) {
        Patient patient = patientService.getPatient(userId);

        if(patient == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Treatment treatment = TreatmentBuilder.toEntity(treatmentDTO, patient);
        treatmentService.insertTreatment(treatment);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //---------------------------------DELETE---------------------------------
    @RequestMapping(value = "/treatment/delete/{treatmentId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTreatment(@PathVariable("treatmentId") Integer treatmentId) {
        Treatment treatment = treatmentService.getTreatment(treatmentId);

        if(treatment == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        treatmentService.deleteTreatment(treatment);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
