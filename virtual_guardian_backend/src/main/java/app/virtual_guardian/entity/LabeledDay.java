package app.virtual_guardian.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity(name="labeled_days")
@Table(name = "labeled_days")
public class LabeledDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "day", nullable = false)
    private Date day;

    @Column(name = "label", nullable = false)
    private String label;

    public LabeledDay() {
    }

    public LabeledDay(Date day, String label) {
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
