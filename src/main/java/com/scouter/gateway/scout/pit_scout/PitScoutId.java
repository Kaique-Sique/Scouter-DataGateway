package com.scouter.gateway.scout.pit_scout;

import java.io.Serializable;
import java.util.UUID;

public class PitScoutId implements Serializable {

    private UUID id;

    public PitScoutId() {
    }

    public PitScoutId(UUID id) {
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
        if (!(o instanceof PitScoutId other)) return false;

        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}