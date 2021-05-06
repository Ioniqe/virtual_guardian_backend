package app.virtual_guardian.controller;

import app.virtual_guardian.dto.SymptomDTO;
import app.virtual_guardian.dto.builder.SymptomBuilder;
import app.virtual_guardian.entity.Disease;
import app.virtual_guardian.entity.Symptom;
import app.virtual_guardian.service.DiseaseService;
import app.virtual_guardian.service.SymptomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
public class SymptomsController {
    private final SymptomsService symptomsService;
    private final DiseaseService diseaseService;

    @Autowired
    public SymptomsController(SymptomsService symptomsService, DiseaseService diseaseService) {
        this.symptomsService = symptomsService;
        this.diseaseService = diseaseService;
    }

    //-------------------------------------GET SYMPTOMS-----------------------------------
    @RequestMapping(value = "/get_symptoms", method = RequestMethod.GET)
    public ResponseEntity<List<SymptomDTO>> getListOfSymptoms() {
        List<Symptom> symptoms = symptomsService.getAllSymptoms();
        List<SymptomDTO> symptomsDTO = SymptomBuilder.toSymptomDTOListFromSymptomList(symptoms);
        return new ResponseEntity<>(symptomsDTO, HttpStatus.OK);
    }

    //-------------------------------GET SYMPTOMS OF DISEASE------------------------------
    @RequestMapping(value = "/get_symptoms/disease/{diseaseId}", method = RequestMethod.GET)
    public ResponseEntity<List<SymptomDTO>> getListOfSymptomsOfDisease(@PathVariable("diseaseId") Integer diseaseId) {
        Disease disease = diseaseService.getDisease(diseaseId);

        if(disease == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Set<Symptom> symptoms = disease.getSymptoms();
        List<SymptomDTO> symptomsDTO = SymptomBuilder.toSymptomDTOListFromSymptomSet(symptoms);
        return new ResponseEntity<>(symptomsDTO, HttpStatus.OK);
    }
}
