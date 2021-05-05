package app.virtual_guardian.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name="admin")
@Table(name = "admin")
public class Admin implements Serializable {
    @Id
    @Column(name="user_id")
    private String userId;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name="user_id", referencedColumnName="id")
    private User user;

    public Admin() {
    }

    public Admin(User user) {
        this.user = user;
        this.userId = user.getId();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
