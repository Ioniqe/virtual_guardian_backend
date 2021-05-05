package app.virtual_guardian.dto.builder;

import app.virtual_guardian.dto.TreatmentDTO;
import app.virtual_guardian.entity.Patient;
import app.virtual_guardian.entity.Treatment;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TreatmentBuilder {
    public static Treatment toEntity(TreatmentDTO treatmentDTO, Patient patient){
        return new Treatment(treatmentDTO.getName(), patient);
    }

    public static TreatmentDTO toTreatmentDTO(Treatment treatment){
        return new TreatmentDTO(treatment.getId(), treatment.getName());
    }

    public static List<TreatmentDTO> toTreatmentDTOListFromTreatmentSet(Set<Treatment> treatments){
        List<TreatmentDTO> treatmentDTOS = new ArrayList<>();
        treatments.forEach(treatment -> treatmentDTOS.add(toTreatmentDTO(treatment)));
        return treatmentDTOS;
    }
}
