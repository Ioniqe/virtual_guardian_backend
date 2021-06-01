package app.virtual_guardian.dto;

import java.sql.Timestamp;

public class EmergencyDTO {
    private Integer id;
    private Timestamp date;
    private String patientName;

    public EmergencyDTO() {
    }

    public EmergencyDTO(Integer id, Timestamp date, String patientName) {
        this.id = id;
        this.date = date;
        this.patientName = patientName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
}

