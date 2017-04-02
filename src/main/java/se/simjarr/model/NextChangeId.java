package se.simjarr.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class NextChangeId {

    @Id
    private String id;

    private String nextId;

    public NextChangeId(String nextId) {
        this.id = "next_id";
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
