package com.aeroklub.competition.team;

import java.time.LocalDateTime;
import java.util.List;

public record CompetitionTeamResponse(
        Long id,
        Long competitionId,
        Integer teamNumber,
        String name,
        List<CompetitionTeamMemberResponse> members,
        LocalDateTime createdAt
) {
    public static CompetitionTeamResponse from(
            CompetitionTeam team,
            List<CompetitionTeamMember> members
    ) {
        return new CompetitionTeamResponse(
                team.getId(),
                team.getCompetition().getId(),
                team.getTeamNumber(),
                team.getName(),
                members.stream()
                        .map(CompetitionTeamMemberResponse::from)
                        .toList(),
                team.getCreatedAt()
        );
    }
}