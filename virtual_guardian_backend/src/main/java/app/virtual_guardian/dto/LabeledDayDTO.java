package app.virtual_guardian.dto;

import java.sql.Date;

public class LabeledDayDTO {
    private Integer id;
    private Date day;
    private String label;

    public LabeledDayDTO() {
    }

    public LabeledDayDTO(Integer id, Date day, String label) {
        this.id = id;
        this.day = day;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
