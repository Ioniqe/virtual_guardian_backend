package app.virtual_guardian.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(name="patient_appointments")
@Table(name = "patient_appointments")
public class Appointment implements Serializable {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "appointment", nullable = false)
    private LocalDateTime appointment;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "info")
    private String info;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    public Appointment() {
    }

    public Appointment(LocalDateTime appointment, String title, Patient patient) {
        this.appointment = appointment;
        this.title = title;
        this.patient = patient;
        this.info = null;
    }

    public Appointment(LocalDateTime appointment, String title, String info, Patient patient) {
        this.appointment = appointment;
        this.title = title;
        this.info = info;
        this.patient = patient;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getAppointment() {
        return appointment;
    }

    public void setAppointment(LocalDateTime appointment) {
        this.appointment = appointment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
