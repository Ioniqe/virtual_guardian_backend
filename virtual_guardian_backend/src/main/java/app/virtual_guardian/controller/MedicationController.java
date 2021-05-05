package app.virtual_guardian.controller;

import app.virtual_guardian.dto.MedicationDTO;
import app.virtual_guardian.dto.builder.MedicationBuilder;
import app.virtual_guardian.entity.MedicationList;
import app.virtual_guardian.entity.Treatment;
import app.virtual_guardian.service.MedicationService;
import app.virtual_guardian.service.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
//@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*", exposedHeaders = "Authorization")
@CrossOrigin(origins = "*")
public class MedicationController {
    private final MedicationService medicationService;
    private final TreatmentService treatmentService;

    @Autowired
    public MedicationController(MedicationService medicationService, TreatmentService treatmentService) {
        this.medicationService = medicationService;
        this.treatmentService = treatmentService;
    }

    //----------------------GET MEDICATIONS OF TREATMENT----------------------------
    @RequestMapping(value = "/get_medications/{treatmentId}", method = RequestMethod.GET)
    public ResponseEntity getMedicationsOfTreatment(@PathVariable("treatmentId") Integer treatmentId) {
        Treatment treatment = treatmentService.getTreatment(treatmentId);

        if(treatment == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        Set<MedicationList> medsList = treatment.getTreatmentMedications();
        List<MedicationDTO> meds = new ArrayList<>();
        medsList.forEach(medicationList -> meds.add(MedicationBuilder.toMedicationDTOFromMedicationList(medicationList)));
        return new ResponseEntity<>(meds, HttpStatus.CREATED);
    }
}
