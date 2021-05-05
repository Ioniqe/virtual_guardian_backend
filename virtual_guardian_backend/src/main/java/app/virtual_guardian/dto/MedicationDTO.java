package app.virtual_guardian.dto;

public class MedicationDTO {
    private String id;
    private String name;
    private String dosage;

    public MedicationDTO() {
    }

    public MedicationDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public MedicationDTO(String id, String name, String dosage) {
        this.id = id;
        this.name = name;
        this.dosage = dosage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }
}
