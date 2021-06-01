package app.virtual_guardian.dto;

import java.sql.Date;

public class AnomalyDTO {
    private Integer id;
    private Date date;

    public AnomalyDTO() {
    }

    public AnomalyDTO(Integer id, Date date) {
        this.id = id;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}