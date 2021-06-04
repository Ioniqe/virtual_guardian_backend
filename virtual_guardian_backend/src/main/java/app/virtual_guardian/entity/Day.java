package app.virtual_guardian.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

@Entity(name="days")
@Table(name = "days")
public class Day implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "day", nullable = false, unique = true)
    private Date day;

    @Column(name = "result", nullable = false)
    private String result;

    @OneToMany(mappedBy = "day", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Activity> listOfActivities;

    public Day() {
    }

    public Day(Date day, String result) {
        this.day = day;
        this.result = result;
    }

    public Day(Date day, String result, Set<Activity> listOfActivities) {
        this.day = day;
        this.result = result;
        this.listOfActivities = listOfActivities;
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

    public Set<Activity> getListOfActivities() {
        return listOfActivities;
    }

    public void setListOfActivities(Set<Activity> listOfActivities) {
        this.listOfActivities = listOfActivities;
    }
}
