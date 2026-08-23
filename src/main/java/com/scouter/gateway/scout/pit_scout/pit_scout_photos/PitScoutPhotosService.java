package com.scouter.gateway.scout.pit_scout.pit_scout_photos;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class PitScoutPhotosService {

    private final PitScoutPhotosRepository pitScoutPhotosRepository;

    public PitScoutPhotosService(PitScoutPhotosRepository pitScoutPhotosRepository) {
        this.pitScoutPhotosRepository = pitScoutPhotosRepository;
    }

    public List<PitScoutPhotosResponse> findByPitScoutId(UUID pitScoutId) {
        return pitScoutPhotosRepository.findByPitScoutId(pitScoutId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public void deleteByPitScoutId(UUID pitScoutId) {
        pitScoutPhotosRepository.deleteByPitScoutId(pitScoutId);
    }

    private PitScoutPhotosResponse toResponse(PitScoutPhotos photo) {
        return new PitScoutPhotosResponse(
                photo.getId(),
                photo.getImgUrl(),
                photo.getDescription(),
                photo.getCreatedAt(),
                photo.getPitScout().getId()
        );
    }
}