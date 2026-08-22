package com.scouter.gateway.scout.teleop;

import java.io.Serializable;
import java.util.UUID;

public class TeleopScoutId implements Serializable {

    private UUID id;

    public TeleopScoutId() {
    }

    public TeleopScoutId(UUID id) {
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
        if (!(o instanceof TeleopScoutId other)) return false;

        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}