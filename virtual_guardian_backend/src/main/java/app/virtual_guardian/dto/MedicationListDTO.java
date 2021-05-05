package app.virtual_guardian.dto;

public class MedicationListDTO {
    private String medicationId;
    private Integer treatmentId;
    private String dosage;

    public MedicationListDTO() {
    }

    public MedicationListDTO(String medicationId, Integer treatmentId, String dosage) {
        this.medicationId = medicationId;
        this.treatmentId = treatmentId;
        this.dosage = dosage;
    }

    public String getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(String medicationId) {
        this.medicationId = medicationId;
    }

    public Integer getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(Integer treatmentId) {
        this.treatmentId = treatmentId;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }
}
