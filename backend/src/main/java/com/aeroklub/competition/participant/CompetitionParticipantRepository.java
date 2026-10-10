package com.aeroklub.competition.participant;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompetitionParticipantRepository
        extends JpaRepository<CompetitionParticipant, Long> {

    boolean existsByCompetition_IdAndCompetitor_Id(
            Long competitionId,
            Long competitorId
    );

    List<CompetitionParticipant> findByCompetition_Id(
            Long competitionId
    );
}