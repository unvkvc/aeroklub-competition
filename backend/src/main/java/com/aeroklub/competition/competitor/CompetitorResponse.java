package com.aeroklub.competition.competitor;

import java.time.LocalDateTime;

public record CompetitorResponse(
        Long id,
        String firstName,
        String lastName,
        Integer birthYear,
        Gender gender,
        String country,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static CompetitorResponse from(Competitor competitor) {
        return new CompetitorResponse(
                competitor.getId(),
                competitor.getFirstName(),
                competitor.getLastName(),
                competitor.getBirthYear(),
                competitor.getGender(),
                competitor.getCountry(),
                competitor.getCreatedAt(),
                competitor.getUpdatedAt()
        );
    }
}