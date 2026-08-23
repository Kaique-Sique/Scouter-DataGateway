package com.scouter.gateway.scout;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.scouter.gateway.auth.AuthService;
import com.scouter.gateway.auth.Authenticator;
import com.scouter.gateway.user.User;

import com.scouter.gateway.scout.auto.AutoScout;
import com.scouter.gateway.scout.auto.AutoScoutRequest;
import com.scouter.gateway.scout.auto.AutoScoutResponse;
import com.scouter.gateway.scout.auto.AutoScoutService;

import com.scouter.gateway.scout.teleop.TeleopScout;
import com.scouter.gateway.scout.teleop.TeleopScoutRequest;
import com.scouter.gateway.scout.teleop.TeleopScoutResponse;
import com.scouter.gateway.scout.teleop.TeleopScoutService;

import com.scouter.gateway.scout.pit_scout.PitScout;
import com.scouter.gateway.scout.pit_scout.PitScoutRequest;
import com.scouter.gateway.scout.pit_scout.PitScoutResponse;
import com.scouter.gateway.scout.pit_scout.PitScoutService;

import com.scouter.gateway.scout.pit_scout.pit_scout_photos.PitScoutPhotosRequest;
import com.scouter.gateway.scout.pit_scout.pit_scout_photos.PitScoutPhotosResponse;
import com.scouter.gateway.scout.pit_scout.pit_scout_photos.PitScoutPhotosService;

@RestController
@RequestMapping("/scout")
public class ScoutController {

    private final Authenticator authenticator;
    private final AutoScoutService autoScoutService;
    private final TeleopScoutService teleopScoutService;
    private final PitScoutService pitScoutService;
    private final PitScoutPhotosService pitScoutPhotosService;

    public ScoutController(
            AuthService authService,
            AutoScoutService autoScoutService,
            TeleopScoutService teleopScoutService,
            PitScoutService pitScoutService,
            PitScoutPhotosService pitScoutPhotosService) {

        this.autoScoutService = autoScoutService;
        this.teleopScoutService = teleopScoutService;
        this.pitScoutService = pitScoutService;
        this.pitScoutPhotosService = pitScoutPhotosService;

        authenticator = new Authenticator(authService);
    }

    // ═══════════════════════════════════════════════════════════════════════
    // AUTO
    // ═══════════════════════════════════════════════════════════════════════

    @PostMapping("/auto")
    public ResponseEntity<Void> createAuto(
            @RequestHeader("X-Credentials") String credentials,
            @RequestBody AutoScoutRequest request) {

        Optional<User> userOpt = authenticator.authenticate(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        AutoScout scout = new AutoScout(
                request.matchTeamId(),
                request.eventKey(),
                request.matchKey(),
                request.teamKey(),
                request.year(),
                userOpt.get().getId(),
                request.l1(),
                request.l2(),
                request.l3(),
                request.l4(),
                request.coralMisseds(),
                request.coralPrecision(),
                request.algaeRemoved(),
                request.algaeNet(),
                request.algaeProcessor(),
                request.regionScored(),
                request.score(),
                request.startline(),
                request.notes()
        );

        autoScoutService.create(scout);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/auto/{matchTeamId}")
    public ResponseEntity<AutoScoutResponse> getAutoByMatchTeamId(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable String matchTeamId) {

        Optional<User> userOpt = authenticator.authenticate(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return autoScoutService.findByMatchTeamId(matchTeamId)
                .map(scout -> ResponseEntity.ok(toResponse(scout)))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/auto/user/{userId}")
    public ResponseEntity<List<AutoScoutResponse>> getAutoByUserId(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable UUID userId) {

        Optional<User> userOpt = authenticator.authenticate(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(
                autoScoutService.findByUserId(userId).stream().map(this::toResponse).toList());
    }

    @GetMapping("/auto/team/{teamKey}")
    public ResponseEntity<List<AutoScoutResponse>> getAutoByTeamKey(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable String teamKey) {

        Optional<User> userOpt = authenticator.authenticate(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(
                autoScoutService.findByTeamKey(teamKey).stream().map(this::toResponse).toList());
    }

    @GetMapping("/auto/event/{eventKey}")
    public ResponseEntity<List<AutoScoutResponse>> getAutoByEventKey(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable String eventKey) {

        Optional<User> userOpt = authenticator.authenticate(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(
                autoScoutService.findByEventKey(eventKey).stream().map(this::toResponse).toList());
    }

    @GetMapping("/auto/match/{matchKey}")
    public ResponseEntity<List<AutoScoutResponse>> getAutoByMatchKey(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable String matchKey) {

        Optional<User> userOpt = authenticator.authenticate(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(
                autoScoutService.findByMatchKey(matchKey).stream().map(this::toResponse).toList());
    }

    @GetMapping("/auto/team/{teamKey}/match/{matchKey}")
    public ResponseEntity<List<AutoScoutResponse>> getAutoByTeamKeyAndMatchKey(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable String teamKey,
            @PathVariable String matchKey) {

        Optional<User> userOpt = authenticator.authenticate(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(autoScoutService.findByTeamKeyAndMatchKey(teamKey, matchKey)
                .stream().map(this::toResponse).toList());
    }

    @DeleteMapping("/auto/{matchTeamId}")
    public ResponseEntity<Void> deleteAuto(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable String matchTeamId) {

        Optional<User> userOpt = authenticator.authenticateAdmin(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (!autoScoutService.existsByMatchTeamId(matchTeamId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        autoScoutService.deleteByMatchTeamId(matchTeamId);
        return ResponseEntity.noContent().build();
    }

    // ═══════════════════════════════════════════════════════════════════════
    // TELEOP
    // ═══════════════════════════════════════════════════════════════════════

    @PostMapping("/teleop")
    public ResponseEntity<Void> createTeleop(
            @RequestHeader("X-Credentials") String credentials,
            @RequestBody TeleopScoutRequest request) {

        Optional<User> userOpt = authenticator.authenticate(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        TeleopScout scout = new TeleopScout(
                request.matchTeamId(),
                request.eventKey(),
                request.matchKey(),
                request.teamKey(),
                request.year(),
                userOpt.get().getId(),
                request.l1(),
                request.l2(),
                request.l3(),
                request.l4(),
                request.coralMisseds(),
                request.coralPrecision(),
                request.algaeRemoved(),
                request.algaeNet(),
                request.algaeProcessor(),
                request.climb(),
                request.collectedCoralFloor(),
                request.collectedCoralStation(),
                request.collectedAlgaeReef(),
                request.defended(),
                request.defendedEffectiveness(),
                request.wasDefended(),
                request.defenseEffectiveness(),
                request.disabled(),
                request.tipped(),
                request.immobilized(),
                request.issues(),
                request.issuesNotes(),
                request.driverRating(),
                request.score(),
                request.notes()
        );

        teleopScoutService.create(scout);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/teleop/{matchTeamId}")
    public ResponseEntity<TeleopScoutResponse> getTeleopByMatchTeamId(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable String matchTeamId) {

        Optional<User> userOpt = authenticator.authenticate(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return teleopScoutService.findByMatchTeamId(matchTeamId)
                .map(scout -> ResponseEntity.ok(toResponse(scout)))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/teleop/user/{userId}")
    public ResponseEntity<List<TeleopScoutResponse>> getTeleopByUserId(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable UUID userId) {

        Optional<User> userOpt = authenticator.authenticate(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(
                teleopScoutService.findByUserId(userId).stream().map(this::toResponse).toList());
    }

    @GetMapping("/teleop/team/{teamKey}")
    public ResponseEntity<List<TeleopScoutResponse>> getTeleopByTeamKey(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable String teamKey) {

        Optional<User> userOpt = authenticator.authenticate(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(
                teleopScoutService.findByTeamKey(teamKey).stream().map(this::toResponse).toList());
    }

    @GetMapping("/teleop/event/{eventKey}")
    public ResponseEntity<List<TeleopScoutResponse>> getTeleopByEventKey(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable String eventKey) {

        Optional<User> userOpt = authenticator.authenticate(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(
                teleopScoutService.findByEventKey(eventKey).stream().map(this::toResponse).toList());
    }

    @GetMapping("/teleop/match/{matchKey}")
    public ResponseEntity<List<TeleopScoutResponse>> getTeleopByMatchKey(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable String matchKey) {

        Optional<User> userOpt = authenticator.authenticate(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(
                teleopScoutService.findByMatchKey(matchKey).stream().map(this::toResponse).toList());
    }

    @GetMapping("/teleop/team/{teamKey}/match/{matchKey}")
    public ResponseEntity<List<TeleopScoutResponse>> getTeleopByTeamKeyAndMatchKey(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable String teamKey,
            @PathVariable String matchKey) {

        Optional<User> userOpt = authenticator.authenticate(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(teleopScoutService.findByTeamKeyAndMatchKey(teamKey, matchKey)
                .stream().map(this::toResponse).toList());
    }

    @DeleteMapping("/teleop/{matchTeamId}")
    public ResponseEntity<Void> deleteTeleop(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable String matchTeamId) {

        Optional<User> userOpt = authenticator.authenticateAdmin(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (!teleopScoutService.existsByMatchTeamId(matchTeamId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        teleopScoutService.deleteByMatchTeamId(matchTeamId);
        return ResponseEntity.noContent().build();
    }

    // ═══════════════════════════════════════════════════════════════════════
    // PIT SCOUT
    // ═══════════════════════════════════════════════════════════════════════

    @PostMapping("/pit")
    public ResponseEntity<PitScoutResponse> createPit(
            @RequestHeader("X-Credentials") String credentials,
            @RequestBody PitScoutRequest request) {

        Optional<User> userOpt = authenticator.authenticate(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        PitScout pitScout = new PitScout();
        pitScout.setTeamKey(request.teamKey());
        pitScout.setEventKey(request.eventKey());
        pitScout.setDescription(request.description());
        pitScout.setUserId(userOpt.get().getId());

        return new ResponseEntity<>(pitScoutService.create(pitScout), HttpStatus.CREATED);
    }

    @GetMapping("/pit/user/{userId}")
    public ResponseEntity<List<PitScoutResponse>> getPitByUserId(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable UUID userId) {

        Optional<User> userOpt = authenticator.authenticate(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(pitScoutService.findByUserId(userId));
    }

    @GetMapping("/pit/team/{teamKey}")
    public ResponseEntity<List<PitScoutResponse>> getPitByTeamKey(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable String teamKey) {

        Optional<User> userOpt = authenticator.authenticate(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(pitScoutService.findByTeamKey(teamKey));
    }

    @GetMapping("/pit/event/{eventKey}")
    public ResponseEntity<List<PitScoutResponse>> getPitByEventKey(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable String eventKey) {

        Optional<User> userOpt = authenticator.authenticate(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(pitScoutService.findByEventKey(eventKey));
    }

    @GetMapping("/pit/team/{teamKey}/event/{eventKey}")
    public ResponseEntity<List<PitScoutResponse>> getPitByTeamKeyAndEventKey(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable String teamKey,
            @PathVariable String eventKey) {

        Optional<User> userOpt = authenticator.authenticate(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(pitScoutService.findByTeamKeyAndEventKey(teamKey, eventKey));
    }

    @DeleteMapping("/pit/team/{teamKey}/event/{eventKey}")
    public ResponseEntity<Void> deletePit(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable String teamKey,
            @PathVariable String eventKey) {

        Optional<User> userOpt = authenticator.authenticateAdmin(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (!pitScoutService.existsByTeamKeyAndEventKey(teamKey, eventKey)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        pitScoutService.deleteByTeamKeyAndEventKey(teamKey, eventKey);
        return ResponseEntity.noContent().build();
    }

    // ═══════════════════════════════════════════════════════════════════════
    // PIT SCOUT PHOTOS
    // ═══════════════════════════════════════════════════════════════════════

    @PostMapping("/pit/{pitScoutId}/photos")
    public ResponseEntity<PitScoutPhotosResponse> createPitPhoto(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable UUID pitScoutId,
            @RequestBody PitScoutPhotosRequest request) {

        Optional<User> userOpt = authenticator.authenticate(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            PitScoutPhotosResponse response = pitScoutPhotosService.create(
                    pitScoutId, request.imgUrl(), request.description());

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/pit/{pitScoutId}/photos")
    public ResponseEntity<List<PitScoutPhotosResponse>> getPitPhotos(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable UUID pitScoutId) {

        Optional<User> userOpt = authenticator.authenticate(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok(pitScoutPhotosService.findByPitScoutId(pitScoutId));
    }

    @DeleteMapping("/pit/{pitScoutId}/photos")
    public ResponseEntity<Void> deletePitPhotos(
            @RequestHeader("X-Credentials") String credentials,
            @PathVariable UUID pitScoutId) {

        Optional<User> userOpt = authenticator.authenticateAdmin(credentials);
        if (userOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        pitScoutPhotosService.deleteByPitScoutId(pitScoutId);
        return ResponseEntity.noContent().build();
    }

    // ═══════════════════════════════════════════════════════════════════════
    // Mappers
    // ═══════════════════════════════════════════════════════════════════════

    private AutoScoutResponse toResponse(AutoScout scout) {
        return new AutoScoutResponse(
                scout.getMatchTeamId(),
                scout.getEventKey(),
                scout.getMatchKey(),
                scout.getTeamKey(),
                scout.getYear(),
                scout.getL1(),
                scout.getL2(),
                scout.getL3(),
                scout.getL4(),
                scout.getCoralMisseds(),
                scout.getCoralPrecision(),
                scout.getAlgaeRemoved(),
                scout.getAlgaeNet(),
                scout.getAlgaeProcessor(),
                scout.getRegionScored(),
                scout.getScore(),
                scout.isStartline(),
                scout.getNotes()
        );
    }

    private TeleopScoutResponse toResponse(TeleopScout scout) {
        return new TeleopScoutResponse(
                scout.getMatchTeamId(),
                scout.getEventKey(),
                scout.getMatchKey(),
                scout.getTeamKey(),
                scout.getYear(),
                scout.getL1(),
                scout.getL2(),
                scout.getL3(),
                scout.getL4(),
                scout.getCoralMisseds(),
                scout.getCoralPrecision(),
                scout.getAlgaeRemoved(),
                scout.getAlgaeNet(),
                scout.getAlgaeProcessor(),
                scout.getClimb(),
                scout.isCollectedCoralFloor(),
                scout.isCollectedCoralStation(),
                scout.isCollectedAlgaeReef(),
                scout.isDefended(),
                scout.getDefendedEffectiveness(),
                scout.isWasDefended(),
                scout.getDefenseEffectiveness(),
                scout.isDisabled(),
                scout.isTipped(),
                scout.isImmobilized(),
                scout.isIssues(),
                scout.getIssuesNotes(),
                scout.getDriverRating(),
                scout.getScore(),
                scout.getNotes()
        );
    }
}