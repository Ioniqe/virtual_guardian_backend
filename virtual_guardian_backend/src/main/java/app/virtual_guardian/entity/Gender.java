package app.virtual_guardian.entity;

import javax.persistence.*;
import java.util.Set;

@Entity(name="gender")
@Table(name = "gender")
public class Gender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "gender", nullable = false)
    private String gender;

    public Gender() {
    }

    public Gender(String gender) {
        this.gender = gender;
    }

    @OneToMany(mappedBy = "gender", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<User> users;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
