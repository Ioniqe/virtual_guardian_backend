package app.virtual_guardian.dto.builder;

import app.virtual_guardian.dto.SymptomDTO;
import app.virtual_guardian.entity.Symptom;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SymptomBuilder {
    public static SymptomDTO toSymptomDTO(Symptom symptom){
        return new SymptomDTO(symptom.getId(), symptom.getSymptom());
    }

    public static List<SymptomDTO> toSymptomDTOListFromSymptomSet(Set<Symptom> symptoms){
        List<SymptomDTO> symptomsDTO = new ArrayList<>();
        symptoms.forEach(symptom -> symptomsDTO.add(toSymptomDTO(symptom)));
        return symptomsDTO;
    }

    public static List<SymptomDTO> toSymptomDTOListFromSymptomList(List<Symptom> symptoms){
        List<SymptomDTO> symptomsDTO = new ArrayList<>();
        symptoms.forEach(symptom -> symptomsDTO.add(toSymptomDTO(symptom)));
        return symptomsDTO;
    }
}
