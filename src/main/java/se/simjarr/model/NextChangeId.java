package se.simjarr.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class NextChangeId {

    @Id
    @GeneratedValue
    private Long id;

    private String nextId;

    public NextChangeId(String nextId) {
        this.nextId = nextId;
    }

    protected NextChangeId() {}

    public void setNextId(String nextId) {
        this.nextId = nextId;
    }

    public String getNextId() {
        return nextId;
    }
}
