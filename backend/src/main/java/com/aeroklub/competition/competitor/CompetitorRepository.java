package com.aeroklub.competition.competitor;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitorRepository
        extends JpaRepository<Competitor, Long> {           //provides db operations such as save, findAll, findById
}