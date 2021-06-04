package app.virtual_guardian.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name="patient")
@Table(name = "patient")
public class Patient  implements Serializable{
    @Id
    @Column(name="user_id")
    private String userId;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name="user_id", referencedColumnName="id")
    private User user;

    @Column(name = "address")
    private String address;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="doctor_user_id", nullable = false)
    private Doctor doctor;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="caregiver_user_id")
    private Caregiver caregiver;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Treatment> listOfTreatments;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Emergency> listOfEmergencies;

    @ManyToMany
    @JoinTable(
            name = "patient_disease",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "disease_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Disease> listOfDiseases;

    public Patient() {
    }

    public Patient(User user, String address, Doctor doctor, Caregiver caregiver) {
        this.user = user;
        this.address = address;
        this.doctor = doctor;
        this.caregiver = caregiver;
        this.listOfTreatments = new HashSet<>();
        this.listOfDiseases = new HashSet<>();
        this.listOfEmergencies = new HashSet<>();
    }

    public Patient(User user, String address, Doctor doctor) {
        this.user = user;
        this.address = address;
        this.doctor = doctor;
        this.userId = user.getId();
        this.listOfTreatments = new HashSet<>();
        this.listOfDiseases = new HashSet<>();
        this.listOfEmergencies = new HashSet<>();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Caregiver getCaregiver() {
        return caregiver;
    }

    public void setCaregiver(Caregiver caregiver) {
        this.caregiver = caregiver;
    }

    public Set<Treatment> getListOfTreatments() {
        return listOfTreatments;
    }

    public void setListOfTreatments(Set<Treatment> listOfTreatments) {
        this.listOfTreatments = listOfTreatments;
    }

    public Set<Disease> getListOfDiseases() {
        return listOfDiseases;
    }

    public void setListOfDiseases(Set<Disease> listOfDiseases) {
        this.listOfDiseases = listOfDiseases;
    }

    public Set<Emergency> getListOfEmergencies() {
        return listOfEmergencies;
    }

    public void setListOfEmergencies(Set<Emergency> listOfEmergencies) {
        this.listOfEmergencies = listOfEmergencies;
    }
}
