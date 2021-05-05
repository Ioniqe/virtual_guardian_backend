package app.virtual_guardian.entity;

import javax.persistence.*;

@Entity(name="special_user_credentials")
@Table(name = "special_user_credentials")
public class SpecialCredentials {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_credentials", nullable = false)
    private String userCredentials;

    public SpecialCredentials() {
    }

    public SpecialCredentials(String userCredentials) {
        this.userCredentials = userCredentials;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserCredentials() {
        return userCredentials;
    }

    public void setUserCredentials(String userCredentials) {
        this.userCredentials = userCredentials;
    }
}
