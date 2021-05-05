package app.virtual_guardian.dto;

import java.time.LocalDate;

public class PatientDTO {
    private String id;
    private String doctorId; //doctorUserId
    private String caregiverId; //caregiverUserId
    private String address;

    //--------------------------------
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private LocalDate birthday;

    private String type;
    private String gender;
    //--------------------------------

    public PatientDTO() {
    }

    public PatientDTO(String id, String doctorId, String caregiverId, String address) {
        this.id = id;
        this.doctorId = doctorId;
        this.caregiverId = caregiverId;
        this.address = address;
    }

    public PatientDTO(String id, String doctorId, String address) {
        this.id = id;
        this.doctorId = doctorId;
        this.address = address;
        this.caregiverId = null;
    }

    public PatientDTO(String address, String username, String password, String firstname, String lastname, LocalDate birthday, String type, String gender) {
        this.address = address;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.type = type;
        this.gender = gender;
        this.caregiverId = null;
    }

    public PatientDTO(String id, String doctorId, String caregiverId, String address, String username, String password, String firstname, String lastname, LocalDate birthday, String type, String gender) {
        this.id = id;
        this.doctorId = doctorId;
        this.caregiverId = caregiverId;
        this.address = address;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.type = type;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getCaregiverId() {
        return caregiverId;
    }

    public void setCaregiverId(String caregiverId) {
        this.caregiverId = caregiverId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    //-----------------------------------------------------------------------------------

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
}
