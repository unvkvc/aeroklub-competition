package com.aeroklub.competition.competition;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CompetitionResponse(
        Long id,
        String name,
        LocalDate startDate,
        LocalDate endDate,
        Integer teamSize,
        Integer baseRoundCount,
        CompetitionStatus status,
        Boolean publicResultsEnabled,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static CompetitionResponse from(Competition competition) {
        return new CompetitionResponse(
                competition.getId(),
                competition.getName(),
                competition.getStartDate(),
                competition.getEndDate(),
                competition.getTeamSize(),
                competition.getBaseRoundCount(),
                competition.getStatus(),
                competition.isPublicResultsEnabled(),
                competition.getCreatedAt(),
                competition.getUpdatedAt()
        );
    }
}