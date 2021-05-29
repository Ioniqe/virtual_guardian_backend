package app.virtual_guardian.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity(name="anomalies")
@Table(name = "anomalies")
public class Anomaly implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date", nullable = false, unique=true)
    private Date date;

    public Anomaly() {
    }

    public Anomaly(Date date) {
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
