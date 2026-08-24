package com.scouter.gateway.scout.auto;

import java.io.Serializable;
import java.util.UUID;

public class AutoScoutId implements Serializable {

    private UUID id;

    public AutoScoutId() {
    }

    public AutoScoutId(UUID id) {
        this.id = id;
    }

    // getters/setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AutoScoutId other)) return false;

        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}