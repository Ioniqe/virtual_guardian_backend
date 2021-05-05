package app.virtual_guardian.entity;

import javax.persistence.*;
import java.util.Set;

@Entity(name="user_type")
@Table(name = "user_type")
public class UserType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "type", nullable = false)
    private String type;

    @OneToMany(mappedBy = "type")
    private Set<User> users;

    public UserType() {
    }

    public UserType(String type, Set<User> users) {
        this.type = type;
        this.users = users;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
