package app.virtual_guardian.dto;

public class TreatmentDTO {
    private Integer id;
    private String name;

    public TreatmentDTO() {
    }

    public TreatmentDTO(String name) {
        this.name = name;
    }

    public TreatmentDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
