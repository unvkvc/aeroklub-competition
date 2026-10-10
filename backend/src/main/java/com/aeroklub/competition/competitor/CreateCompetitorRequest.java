package com.aeroklub.competition.competitor;

public record CreateCompetitorRequest(
        String firstName,
        String lastName,
        Integer birthYear,
        Gender gender,
        String country
) {                                            //describes the data that the frontend will send when creating a competitor
} 