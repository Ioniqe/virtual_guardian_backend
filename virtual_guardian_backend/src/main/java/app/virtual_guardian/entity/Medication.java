package app.virtual_guardian.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name="medication")
@Table(name = "medication")
public class Medication implements Serializable {
    @Id
    @Column(name="id")
    private String id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "medication", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MedicationList> treatmentsThatHaveThisMedication;

    @OneToMany(mappedBy = "medication", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SideEffect> listOfSideEffects;

    public Medication() {
    }

    public Medication(String id, String name) {
        this.id = id;
        this.name = name;
        this.treatmentsThatHaveThisMedication = new HashSet<>();
        this.listOfSideEffects = new HashSet<>();

    }

    public Medication(String id, String name, Set<MedicationList> treatmentsThatHaveThisMedication, Set<SideEffect> listOfSideEffect) {
        this.id = id;
        this.name = name;
        this.treatmentsThatHaveThisMedication = treatmentsThatHaveThisMedication;
        this.listOfSideEffects = listOfSideEffect;
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

    public Set<MedicationList> getTreatmentsThatHaveThisMedication() {
        return treatmentsThatHaveThisMedication;
    }

    public void setTreatmentsThatHaveThisMedication(Set<MedicationList> treatmentsThatHaveThisMedication) {
        this.treatmentsThatHaveThisMedication = treatmentsThatHaveThisMedication;
    }

    public Set<SideEffect> getListOfSideEffects() {
        return listOfSideEffects;
    }

    public void setListOfSideEffects(Set<SideEffect> listOfSideEffects) {
        this.listOfSideEffects = listOfSideEffects;
    }
}
