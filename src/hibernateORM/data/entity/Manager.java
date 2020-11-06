package hibernateORM.data.entity;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;

@NamedQuery(name = "selectByUsername", query = "select password from Manager where username =: username")
@Entity
public class Manager extends User {
    private String password;
    private String username;

    public Manager() {}

    public Manager(String password, String username) {
        setUsername(username);
        setPassword(password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
