package se.simjarr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class User extends AbstractEntity {

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<CurrencyStash> stashTabs;

    private String username;

    public User(String username) {
        this.username = username;
    }

    protected User() {}

    public void setStashTabs(List<CurrencyStash> stashTabs) {
        this.stashTabs = stashTabs;
    }

    public String getUsername() {
        return username;
    }

    public List<CurrencyStash> getStashTabs() {
        return stashTabs;
    }
}
