package com.aeroklub.competition.team;

public record CreateCompetitionTeamRequest(
        Integer teamNumber,
        String name
) {                                    //{
                                       //"teamNumber": 1,
                                       //"name": "Crazy Ducks"
}
