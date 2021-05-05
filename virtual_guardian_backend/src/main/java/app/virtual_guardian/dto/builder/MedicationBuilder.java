package app.virtual_guardian.dto.builder;

import app.virtual_guardian.dto.MedicationDTO;
import app.virtual_guardian.entity.Medication;
import app.virtual_guardian.entity.MedicationList;

import java.util.ArrayList;
import java.util.List;

public class MedicationBuilder {
    public static MedicationDTO toMedicationDTO(Medication medication){
        return new MedicationDTO(medication.getId(), medication.getName());
    }

    public static MedicationDTO toMedicationDTOFromMedicationList(MedicationList medicationList){
        return new MedicationDTO(medicationList.getMedication().getId(), medicationList.getMedication().getName(), medicationList.getDosage());
    }

    public static List<MedicationDTO> toMedicationDTOListFromMedicationList(List<Medication> meds){
        List<MedicationDTO> medicationDTOS = new ArrayList<>();
        meds.forEach(medication -> medicationDTOS.add(toMedicationDTO(medication)));
        return medicationDTOS;
    }
}
