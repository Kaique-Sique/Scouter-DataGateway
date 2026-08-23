package com.scouter.gateway.scout.auto;

import jakarta.persistence.*;

import org.hibernate.annotations.Generated;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
    name = "auto_scout_reefscape",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_auto_match_team", columnNames = "match_team_id")
    }
)
public class AutoScout {

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

    // REGIOES (jsonb cru como texto - sem dependencia extra)
    @Column(name = "region_scored", columnDefinition = "jsonb")
    private String regionScored;

    // SCORE
    @Column(name = "score")
    private int score = 0;

    // AUTO
    @Column(name = "startline")
    private boolean startline = false;

    @Column(name = "notes")
    private String notes;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public AutoScout() {
    }

    public AutoScout(
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
            String regionScored,
            int score,
            boolean startline,
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
        this.regionScored = regionScored;
        this.score = score;
        this.startline = startline;
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

    public String getRegionScored() {
        return regionScored;
    }

    public int getScore() {
        return score;
    }

    public boolean isStartline() {
        return startline;
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

    public void setRegionScored(String regionScored) {
        this.regionScored = regionScored;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setStartline(boolean startline) {
        this.startline = startline;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}