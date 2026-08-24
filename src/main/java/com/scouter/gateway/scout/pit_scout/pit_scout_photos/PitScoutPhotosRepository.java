package com.scouter.gateway.scout.pit_scout.pit_scout_photos;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PitScoutPhotosRepository extends JpaRepository<PitScoutPhotos, UUID> {

    List<PitScoutPhotos> findByPitScoutId(UUID pitScoutId);

    void deleteByPitScoutId(UUID pitScoutId);
}