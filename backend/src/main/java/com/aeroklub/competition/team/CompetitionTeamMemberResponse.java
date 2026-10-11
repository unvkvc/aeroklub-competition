package com.aeroklub.competition.team;

public record CompetitionTeamMemberResponse(
        Long id,
        Long participantId,
        Long competitorId,
        TeamMemberPosition memberPosition,
        String participantCode,
        String firstName,
        String lastName,
        String country
) {
    public static CompetitionTeamMemberResponse from(
            CompetitionTeamMember member
    ) {
        return new CompetitionTeamMemberResponse(
                member.getId(),
                member.getParticipant().getId(),
                member.getParticipant().getCompetitor().getId(),
                member.getMemberPosition(),
                member.getTeam().getTeamNumber()
                        + member.getMemberPosition().name(),
                member.getParticipant().getCompetitor().getFirstName(),
                member.getParticipant().getCompetitor().getLastName(),
                member.getParticipant().getCompetitor().getCountry()
        );
    }
}