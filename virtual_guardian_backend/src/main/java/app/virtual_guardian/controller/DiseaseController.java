package app.virtual_guardian.controller;

import app.virtual_guardian.dto.DiseaseDTO;
import app.virtual_guardian.dto.builder.DiseaseBuilder;
import app.virtual_guardian.entity.Disease;
import app.virtual_guardian.entity.Patient;
import app.virtual_guardian.service.DiseaseService;
import app.virtual_guardian.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*", exposedHeaders = "Authorization")
public class DiseaseController {
    private final DiseaseService diseaseService;
    private final PatientService patientService;

    @Autowired
    public DiseaseController(DiseaseService diseaseService, PatientService patientService) {
        this.diseaseService = diseaseService;
        this.patientService = patientService;
    }

    //---------------------------------ADD DISEASE TO PATIENT---------------------------------
    @RequestMapping(value = "/patient/add_disease/{patientUserId}/{diseaseId}", method = RequestMethod.POST)
    public ResponseEntity addDiseaseToPatient(@PathVariable("patientUserId") String userId, @PathVariable("diseaseId") Integer diseaseId){
        Patient patient = patientService.getPatient(userId);
        Disease disease = diseaseService.getDisease(diseaseId);
        if(patient == null || disease == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        Set<Patient> patients = disease.getPatientsWithThisDisease();
        patients.add(patient);
        diseaseService.insertDisease(disease);

        Set<Disease> diseases = patient.getListOfDiseases();
        diseases.add(disease);
        patientService.insertPatient(patient);

        return new ResponseEntity(HttpStatus.OK);
    }

    //---------------------------------REMOVE DISEASE FROM PATIENT---------------------------------
    @RequestMapping(value = "/patient/remove_disease/{patientUserId}/{diseaseId}", method = RequestMethod.PUT)
    public ResponseEntity removeDiseaseFromPatient(@PathVariable("patientUserId") String userId, @PathVariable("diseaseId") Integer diseaseId){
        Patient patient = patientService.getPatient(userId);
        if(patient == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        Disease disease = diseaseService.getDisease(diseaseId);
        if(disease == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        Set<Patient> patients = disease.getPatientsWithThisDisease();
        patients.remove(patient);
        diseaseService.insertDisease(disease);

        Set<Disease> diseases = patient.getListOfDiseases();
        diseases.remove(disease);
        patientService.insertPatient(patient);

        return new ResponseEntity(HttpStatus.OK);
    }

    //---------------------------------GET DISEASES OF PATIENT---------------------------------
    @RequestMapping(value = "/patient/get_diseases/{patientUserId}", method = RequestMethod.GET)
    public ResponseEntity getDiseasesOfPatient(@PathVariable("patientUserId") String userId){
        Patient patient = patientService.getPatient(userId);
        if(patient == null)
            return new ResponseEntity(HttpStatus.NOT_FOUND);

        Set<Disease> diseases = patient.getListOfDiseases();
        List<DiseaseDTO> diseasesDTO = DiseaseBuilder.toDiseaseDTOListFromDiseaseSet(diseases);
        return new ResponseEntity<>(diseasesDTO, HttpStatus.OK);
    }

    //---------------------------------GET DISEASES LIST---------------------------------
    @RequestMapping(value = "/get_diseases", method = RequestMethod.GET)
    public ResponseEntity<List<DiseaseDTO>> getDiseasesList(){
        List<Disease> diseases = diseaseService.getDiseases();
        List<DiseaseDTO> diseaseDTOS = DiseaseBuilder.toDiseaseDTOListFromDiseaseList(diseases);
        return new ResponseEntity<>(diseaseDTOS, HttpStatus.OK);
    }
}
