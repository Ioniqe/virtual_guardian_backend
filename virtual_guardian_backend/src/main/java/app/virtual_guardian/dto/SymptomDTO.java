package app.virtual_guardian.dto;

public class SymptomDTO {
    private Integer id;
    private String symptom;

    public SymptomDTO() {
    }

    public SymptomDTO(Integer id, String symptom) {
        this.id = id;
        this.symptom = symptom;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }
}
