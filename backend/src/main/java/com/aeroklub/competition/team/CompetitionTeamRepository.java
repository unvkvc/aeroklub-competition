package com.aeroklub.competition.team;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompetitionTeamRepository
        extends JpaRepository<CompetitionTeam, Long> {               //checks for duplicate team numbers and lists teams in numerical order

    boolean existsByCompetition_IdAndTeamNumber(
            Long competitionId,
            Integer teamNumber
    );

    List<CompetitionTeam> findByCompetition_IdOrderByTeamNumber(
            Long competitionId
    );
}