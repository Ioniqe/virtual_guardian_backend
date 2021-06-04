package app.virtual_guardian.dto;

import java.sql.Date;

public class DayDTO {
    private Integer id;
    private Date day;
    private String result;

    public DayDTO() {
    }

    public DayDTO(Date day, String result) {
        this.day = day;
        this.result = result;
    }

    public DayDTO(Integer id, Date day, String result) {
        this.id = id;
        this.day = day;
        this.result = result;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
