package app.virtual_guardian.dto;

public class SpecialCredentialsDTO {
    private Integer id;
    private String specialCredential;

    public SpecialCredentialsDTO() {
    }

    public SpecialCredentialsDTO(String specialCredential) {
        this.specialCredential = specialCredential;
    }

    public SpecialCredentialsDTO(Integer id, String specialCredential) {
        this.id = id;
        this.specialCredential = specialCredential;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpecialCredential() {
        return specialCredential;
    }

    public void setSpecialCredential(String specialCredential) {
        this.specialCredential = specialCredential;
    }
}
