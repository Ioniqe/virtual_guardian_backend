package app.virtual_guardian.dto;


import java.time.LocalDateTime;

public class AppointmentDTO {
    private Integer id;
    private LocalDateTime appointment;
    private String title;
    private String info;

    public AppointmentDTO() {
    }

    public AppointmentDTO(LocalDateTime appointment, String title, String info) {
        this.appointment = appointment;
        this.title = title;
        this.info = info;
    }

    public AppointmentDTO(Integer id, LocalDateTime appointment, String title, String info) {
        this.id = id;
        this.appointment = appointment;
        this.title = title;
        this.info = info;
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
}
