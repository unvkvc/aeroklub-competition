package com.aeroklub.competition.team;

public record AddCompetitionTeamMemberRequest(
        Long participantId,
        TeamMemberPosition memberPosition
) {                                                                 //{
                                                                    //"participantId": 1,
                                                                    //"memberPosition": "A"
                                                                    //?
}