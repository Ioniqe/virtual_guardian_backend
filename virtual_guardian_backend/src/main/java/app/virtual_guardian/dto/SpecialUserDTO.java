package app.virtual_guardian.dto;

import java.time.LocalDate;

public class SpecialUserDTO {
    private String id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private LocalDate birthday;

    private String type;
    private String gender;

    private String secretCredential;

    public SpecialUserDTO() {
    }

    public SpecialUserDTO(String username, String password, String firstname, String lastname, LocalDate birthday, String type, String gender, String secretDoctorId) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.type = type;
        this.gender = gender;
        this.secretCredential = secretDoctorId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSecretCredential() {
        return secretCredential;
    }

    public void setSecretCredential(String secretCredential) {
        this.secretCredential = secretCredential;
    }
}
