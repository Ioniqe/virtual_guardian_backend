package app.virtual_guardian.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity(name="doctor")
@Table(name = "doctor")
public class Doctor  implements Serializable {
    @Id
    @Column(name="user_id")
    private String userId;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name="user_id", referencedColumnName="id")
    private User user;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Patient> listOfPatients;

    public Doctor() {
    }

    public Doctor(Set<Patient> listOfPatients, User user) {
        this.listOfPatients = listOfPatients;
        this.user = user;
        this.userId = user.getId();
    }

    public Set<Patient> getListOfPatients() {
        return listOfPatients;
    }

    public void setListOfPatients(Set<Patient> listOfPatients) {
        this.listOfPatients = listOfPatients;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
