package app.virtual_guardian.controller;

import app.virtual_guardian.dto.MedicationListDTO;
import app.virtual_guardian.dto.builder.MedicationListBuilder;
import app.virtual_guardian.entity.*;
import app.virtual_guardian.service.MedicationListService;
import app.virtual_guardian.service.MedicationService;
import app.virtual_guardian.service.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*", exposedHeaders = "Authorization")
public class MedicationListController {
    private final MedicationListService medicationListService;
    private final MedicationService medicationService;
    private final TreatmentService treatmentService;

    @Autowired
    public MedicationListController(MedicationListService medicationListService, MedicationService medicationService, TreatmentService treatmentService) {
        this.medicationListService = medicationListService;
        this.medicationService = medicationService;
        this.treatmentService = treatmentService;
    }

    //---------------------------------ADD MEDICATION TO MEDICATION LIST---------------------------------
    @RequestMapping(value = "/treatment/add_medication", method = RequestMethod.POST)
    public ResponseEntity addMedicationToTreatment(@RequestBody MedicationListDTO medicationListDTO){
        Medication medication = medicationService.getMedication(medicationListDTO.getMedicationId());
        Treatment treatment = treatmentService.getTreatment(medicationListDTO.getTreatmentId());
        if(medication == null || treatment == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        MedicationList medicationList = MedicationListBuilder.toEntity(medicationListDTO.getDosage(), medication, treatment);

        medicationListService.insertMedicationList(medicationList);
        return new ResponseEntity(HttpStatus.OK);
    }
}
