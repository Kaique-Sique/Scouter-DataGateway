package com.scouter.gateway.scout.pit_scout.pit_scout_photos;

import java.io.Serializable;
import java.util.UUID;

public class PitScoutPhotosId implements Serializable {

    private UUID id;

    public PitScoutPhotosId() {
    }

    public PitScoutPhotosId(UUID id) {
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
        if (!(o instanceof PitScoutPhotosId other)) return false;

        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}