package com.aeroklub.competition.competition;

import java.time.LocalDate;

public record CreateCompetitionRequest(
        String name,
        LocalDate startDate,
        LocalDate endDate,
        Integer teamSize,
        Boolean publicResultsEnabled
) {
}