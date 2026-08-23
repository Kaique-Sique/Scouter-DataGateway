package com.scouter.gateway.scout.pit_scout.pit_scout_photos;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.scouter.gateway.scout.pit_scout.PitScout;
import com.scouter.gateway.scout.pit_scout.PitScoutService;

@Service
public class PitScoutPhotosService {

    private final PitScoutPhotosRepository pitScoutPhotosRepository;
    private final PitScoutService pitScoutService;

    public PitScoutPhotosService(
            PitScoutPhotosRepository pitScoutPhotosRepository,
            PitScoutService pitScoutService) {

        this.pitScoutPhotosRepository = pitScoutPhotosRepository;
        this.pitScoutService = pitScoutService;
    }

    public PitScoutPhotosResponse create(UUID pitScoutId, String imgUrl, String description) {
        PitScout pitScout = pitScoutService.getEntityById(pitScoutId);

        PitScoutPhotos photo = new PitScoutPhotos();
        photo.setImgUrl(imgUrl);
        photo.setDescription(description);
        photo.setPitScout(pitScout);

        return toResponse(pitScoutPhotosRepository.save(photo));
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