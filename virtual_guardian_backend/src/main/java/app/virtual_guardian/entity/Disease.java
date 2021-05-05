package app.virtual_guardian.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity(name="diseases")
@Table(name = "diseases")
public class Disease implements Serializable {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "disease", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Symptom> symptoms;

    @ManyToMany(mappedBy = "listOfDiseases")
    private Set<Patient> patientsWithThisDisease;

    public Disease() {
    }

    public Disease(String name, Set<Symptom> symptoms, Set<Patient> patientsWithThisDisease) {
        this.name = name;
        this.symptoms = symptoms;
        this.patientsWithThisDisease = patientsWithThisDisease;
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

    public Set<Symptom> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(Set<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    public Set<Patient> getPatientsWithThisDisease() {
        return patientsWithThisDisease;
    }

    public void setPatientsWithThisDisease(Set<Patient> patientsWithThisDisease) {
        this.patientsWithThisDisease = patientsWithThisDisease;
    }
}
