package app.virtual_guardian.dto;

import java.sql.Date;
import java.sql.Time;

public class ActivityDTO {
    private Integer id;
    private String name;
    private Time startTime;
    private Time endTime;
    private Date day;
    private String patientId;

    public ActivityDTO() {
    }

    public ActivityDTO(Integer id, String name, Time startTime, Time endTime, Date day, String patientId) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
        this.patientId = patientId;
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

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
}
