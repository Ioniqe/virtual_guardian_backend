package app.virtual_guardian.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity(name="patient_treatments")
@Table(name = "patient_treatments")
public class Treatment implements Serializable {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @OneToMany(mappedBy = "treatment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MedicationList> treatmentMedications;

    public Treatment() {
    }

    public Treatment(String name, Patient patient) {
        this.name = name;
        this.patient = patient;
        this.treatmentMedications = new HashSet<>();
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

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Set<MedicationList> getTreatmentMedications() {
        return treatmentMedications;
    }

    public void setTreatmentMedications(Set<MedicationList> treatmentMedications) {
        this.treatmentMedications = treatmentMedications;
    }
}
