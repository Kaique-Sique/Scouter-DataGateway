package com.scouter.gateway.scout.teleop;

import jakarta.persistence.*;

import org.hibernate.annotations.Generated;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
    name = "teleop_scout_reefscape",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_teleop_match_team", columnNames = "match_team_id")
    }
)
public class TeleopScout {

    // id vem do DEFAULT gen_random_uuid() do Postgres, nao do Hibernate.
    // insertable = false tira a coluna do INSERT (o banco preenche sozinho),
    // @Generated(INSERT) fala pro Hibernate reler o valor depois do insert.
    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @Generated
    private UUID id;

    @Column(name = "match_team_id", nullable = false, length = 100)
    private String matchTeamId;

    @Column(name = "event_key", nullable = false, length = 50)
    private String eventKey;

    @Column(name = "match_key", nullable = false, length = 50)
    private String matchKey;

    @Column(name = "team_key", nullable = false, length = 50)
    private String teamKey;

    @Column(name = "year", nullable = false)
    private int year;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    // CORAL
    @Column(name = "l1")
    private int l1 = 0;

    @Column(name = "l2")
    private int l2 = 0;

    @Column(name = "l3")
    private int l3 = 0;

    @Column(name = "l4")
    private int l4 = 0;

    @Column(name = "coral_misseds")
    private int coralMisseds = 0;

    @Column(name = "coral_precision", precision = 5, scale = 2)
    private BigDecimal coralPrecision = BigDecimal.ZERO;

    // ALGAE
    @Column(name = "algae_removed")
    private int algaeRemoved = 0;

    @Column(name = "algae_net")
    private int algaeNet = 0;

    @Column(name = "algae_processor")
    private int algaeProcessor = 0;

    // ENDGAME
    @Column(name = "climb", length = 30)
    private String climb;

    // COLETA
    @Column(name = "collected_coral_floor")
    private boolean collectedCoralFloor = false;

    @Column(name = "collected_coral_station")
    private boolean collectedCoralStation = false;

    @Column(name = "collected_algae_reef")
    private boolean collectedAlgaeReef = false;

    // DEFESA
    @Column(name = "defended")
    private boolean defended = false;

    @Column(name = "defended_effectiveness")
    private Integer defendedEffectiveness;

    @Column(name = "was_defended")
    private boolean wasDefended = false;

    @Column(name = "defense_effectiveness")
    private Integer defenseEffectiveness;

    // CONFIABILIDADE
    @Column(name = "disabled")
    private boolean disabled = false;

    @Column(name = "tipped")
    private boolean tipped = false;

    @Column(name = "immobilized")
    private boolean immobilized = false;

    // PROBLEMAS
    @Column(name = "issues")
    private boolean issues = false;

    @Column(name = "issues_notes")
    private String issuesNotes;

    // DRIVER
    @Column(name = "driver_rating")
    private Integer driverRating;

    // SCORE
    @Column(name = "score")
    private int score = 0;

    @Column(name = "notes")
    private String notes;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public TeleopScout() {
    }

    public TeleopScout(
            String matchTeamId,
            String eventKey,
            String matchKey,
            String teamKey,
            int year,
            UUID userId,
            int l1,
            int l2,
            int l3,
            int l4,
            int coralMisseds,
            BigDecimal coralPrecision,
            int algaeRemoved,
            int algaeNet,
            int algaeProcessor,
            String climb,
            boolean collectedCoralFloor,
            boolean collectedCoralStation,
            boolean collectedAlgaeReef,
            boolean defended,
            Integer defendedEffectiveness,
            boolean wasDefended,
            Integer defenseEffectiveness,
            boolean disabled,
            boolean tipped,
            boolean immobilized,
            boolean issues,
            String issuesNotes,
            Integer driverRating,
            int score,
            String notes) {

        this.matchTeamId = matchTeamId;
        this.eventKey = eventKey;
        this.matchKey = matchKey;
        this.teamKey = teamKey;
        this.year = year;
        this.userId = userId;
        this.l1 = l1;
        this.l2 = l2;
        this.l3 = l3;
        this.l4 = l4;
        this.coralMisseds = coralMisseds;
        this.coralPrecision = coralPrecision;
        this.algaeRemoved = algaeRemoved;
        this.algaeNet = algaeNet;
        this.algaeProcessor = algaeProcessor;
        this.climb = climb;
        this.collectedCoralFloor = collectedCoralFloor;
        this.collectedCoralStation = collectedCoralStation;
        this.collectedAlgaeReef = collectedAlgaeReef;
        this.defended = defended;
        this.defendedEffectiveness = defendedEffectiveness;
        this.wasDefended = wasDefended;
        this.defenseEffectiveness = defenseEffectiveness;
        this.disabled = disabled;
        this.tipped = tipped;
        this.immobilized = immobilized;
        this.issues = issues;
        this.issuesNotes = issuesNotes;
        this.driverRating = driverRating;
        this.score = score;
        this.notes = notes;
    }

    // getters

    public UUID getId() {
        return id;
    }

    public String getMatchTeamId() {
        return matchTeamId;
    }

    public String getEventKey() {
        return eventKey;
    }

    public String getMatchKey() {
        return matchKey;
    }

    public String getTeamKey() {
        return teamKey;
    }

    public int getYear() {
        return year;
    }

    public UUID getUserId() {
        return userId;
    }

    public int getL1() {
        return l1;
    }

    public int getL2() {
        return l2;
    }

    public int getL3() {
        return l3;
    }

    public int getL4() {
        return l4;
    }

    public int getCoralMisseds() {
        return coralMisseds;
    }

    public BigDecimal getCoralPrecision() {
        return coralPrecision;
    }

    public int getAlgaeRemoved() {
        return algaeRemoved;
    }

    public int getAlgaeNet() {
        return algaeNet;
    }

    public int getAlgaeProcessor() {
        return algaeProcessor;
    }

    public String getClimb() {
        return climb;
    }

    public boolean isCollectedCoralFloor() {
        return collectedCoralFloor;
    }

    public boolean isCollectedCoralStation() {
        return collectedCoralStation;
    }

    public boolean isCollectedAlgaeReef() {
        return collectedAlgaeReef;
    }

    public boolean isDefended() {
        return defended;
    }

    public Integer getDefendedEffectiveness() {
        return defendedEffectiveness;
    }

    public boolean isWasDefended() {
        return wasDefended;
    }

    public Integer getDefenseEffectiveness() {
        return defenseEffectiveness;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public boolean isTipped() {
        return tipped;
    }

    public boolean isImmobilized() {
        return immobilized;
    }

    public boolean isIssues() {
        return issues;
    }

    public String getIssuesNotes() {
        return issuesNotes;
    }

    public Integer getDriverRating() {
        return driverRating;
    }

    public int getScore() {
        return score;
    }

    public String getNotes() {
        return notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // setters (campos que fazem sentido editar depois de criado)

    public void setL1(int l1) {
        this.l1 = l1;
    }

    public void setL2(int l2) {
        this.l2 = l2;
    }

    public void setL3(int l3) {
        this.l3 = l3;
    }

    public void setL4(int l4) {
        this.l4 = l4;
    }

    public void setCoralMisseds(int coralMisseds) {
        this.coralMisseds = coralMisseds;
    }

    public void setCoralPrecision(BigDecimal coralPrecision) {
        this.coralPrecision = coralPrecision;
    }

    public void setAlgaeRemoved(int algaeRemoved) {
        this.algaeRemoved = algaeRemoved;
    }

    public void setAlgaeNet(int algaeNet) {
        this.algaeNet = algaeNet;
    }

    public void setAlgaeProcessor(int algaeProcessor) {
        this.algaeProcessor = algaeProcessor;
    }

    public void setClimb(String climb) {
        this.climb = climb;
    }

    public void setCollectedCoralFloor(boolean collectedCoralFloor) {
        this.collectedCoralFloor = collectedCoralFloor;
    }

    public void setCollectedCoralStation(boolean collectedCoralStation) {
        this.collectedCoralStation = collectedCoralStation;
    }

    public void setCollectedAlgaeReef(boolean collectedAlgaeReef) {
        this.collectedAlgaeReef = collectedAlgaeReef;
    }

    public void setDefended(boolean defended) {
        this.defended = defended;
    }

    public void setDefendedEffectiveness(Integer defendedEffectiveness) {
        this.defendedEffectiveness = defendedEffectiveness;
    }

    public void setWasDefended(boolean wasDefended) {
        this.wasDefended = wasDefended;
    }

    public void setDefenseEffectiveness(Integer defenseEffectiveness) {
        this.defenseEffectiveness = defenseEffectiveness;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public void setTipped(boolean tipped) {
        this.tipped = tipped;
    }

    public void setImmobilized(boolean immobilized) {
        this.immobilized = immobilized;
    }

    public void setIssues(boolean issues) {
        this.issues = issues;
    }

    public void setIssuesNotes(String issuesNotes) {
        this.issuesNotes = issuesNotes;
    }

    public void setDriverRating(Integer driverRating) {
        this.driverRating = driverRating;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}