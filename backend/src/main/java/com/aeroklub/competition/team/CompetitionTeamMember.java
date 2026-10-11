package com.aeroklub.competition.team;

import com.aeroklub.competition.participant.CompetitionParticipant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "competition_team_member")
public class CompetitionTeamMember { //represents one assignment such as "team: crazy ducks; participant: Marko; position: A; displayed identifier: 1A"

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "team_id", nullable = false)
    private CompetitionTeam team;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "participant_id", nullable = false)
    private CompetitionParticipant participant;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_position", nullable = false, length = 1)
    private TeamMemberPosition memberPosition;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void beforeInsert() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public CompetitionTeam getTeam() {
        return team;
    }

    public void setTeam(CompetitionTeam team) {
        this.team = team;
    }

    public CompetitionParticipant getParticipant() {
        return participant;
    }

    public void setParticipant(
            CompetitionParticipant participant
    ) {
        this.participant = participant;
    }

    public TeamMemberPosition getMemberPosition() {
        return memberPosition;
    }

    public void setMemberPosition(
            TeamMemberPosition memberPosition
    ) {
        this.memberPosition = memberPosition;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}