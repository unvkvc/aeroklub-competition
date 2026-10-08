package com.aeroklub.competition.competition;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionRepository
        extends JpaRepository<Competition, Long> {
}