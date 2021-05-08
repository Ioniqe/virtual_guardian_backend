package app.virtual_guardian.controller;

import app.virtual_guardian.dto.SideEffectsDTO;
import app.virtual_guardian.dto.builder.SideEffectBuilder;
import app.virtual_guardian.entity.Medication;
import app.virtual_guardian.entity.SideEffect;
import app.virtual_guardian.service.MedicationService;
import app.virtual_guardian.service.SideEffectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
public class SideEffectsController {
    private final SideEffectsService sideEffectsService;
    private final MedicationService medicationService;

    @Autowired
    public SideEffectsController(SideEffectsService sideEffectsService, MedicationService medicationService) {
        this.sideEffectsService = sideEffectsService;
        this.medicationService = medicationService;
    }

    //-------------------------------GET SIDE EFFECTS------------------------------ TODO
    @RequestMapping(value = "/get_side_effects", method = RequestMethod.GET)
    public ResponseEntity<List<SideEffectsDTO>> getListOfSideEffects() {
        List<SideEffect> sideEffects = sideEffectsService.getAllSideEffects();
        List<SideEffectsDTO> sideEffectDTOS = SideEffectBuilder.toSideEffectsDTOListFromSideEffectsList(sideEffects);
        return new ResponseEntity<>(sideEffectDTOS, HttpStatus.OK);
    }

    //-------------------------------GET SIDE EFFECTS OF MEDICATION------------------------------ TODO
    @RequestMapping(value = "/get_side_effects/medication/{medicationId}", method = RequestMethod.GET)
    public ResponseEntity<List<SideEffectsDTO>> getListOfSideEffectsOfMedication(@PathVariable("medicationId") String medicationId) {
        Medication medication = medicationService.getMedication(medicationId);

        if(medication == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Set<SideEffect> sideEffects = medication.getListOfSideEffects();
        List<SideEffectsDTO> sideEffectDTOS = SideEffectBuilder.toSideEffectsDTOListFromSideEffectsSet(sideEffects);
        return new ResponseEntity<>(sideEffectDTOS, HttpStatus.OK);
    }
}
