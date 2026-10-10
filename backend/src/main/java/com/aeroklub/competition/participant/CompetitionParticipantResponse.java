package com.aeroklub.competition.participant;

import com.aeroklub.competition.competitor.Gender;

import java.time.LocalDateTime;

public record CompetitionParticipantResponse(
        Long id,
        Long competitionId,
        Long competitorId,
        String firstName,
        String lastName,
        Integer birthYear,
        Gender gender,
        String country,
        LocalDateTime createdAt
) {
    public static CompetitionParticipantResponse from(
            CompetitionParticipant participant
    ) {
        return new CompetitionParticipantResponse(
                participant.getId(),
                participant.getCompetition().getId(),
                participant.getCompetitor().getId(),
                participant.getCompetitor().getFirstName(),
                participant.getCompetitor().getLastName(),
                participant.getCompetitor().getBirthYear(),
                participant.getCompetitor().getGender(),
                participant.getCompetitor().getCountry(),
                participant.getCreatedAt()
        );
    }
}