package app.virtual_guardian.dto.builder;

import app.virtual_guardian.entity.Medication;
import app.virtual_guardian.entity.MedicationList;
import app.virtual_guardian.entity.Treatment;

public class MedicationListBuilder {
    public static MedicationList toEntity(String dosage, Medication medication, Treatment treatment){
        return new MedicationList(dosage, medication, treatment);
    }
}
