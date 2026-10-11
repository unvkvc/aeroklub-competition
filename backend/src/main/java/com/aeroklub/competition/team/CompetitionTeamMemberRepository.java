package com.aeroklub.competition.team;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompetitionTeamMemberRepository
        extends JpaRepository<CompetitionTeamMember, Long> {

    boolean existsByParticipant_Id(Long participantId);

    boolean existsByTeam_IdAndMemberPosition(
            Long teamId,
            TeamMemberPosition memberPosition
    );

    long countByTeam_Id(Long teamId);

    List<CompetitionTeamMember> findByTeam_IdOrderByMemberPosition(
            Long teamId
    );

    List<CompetitionTeamMember>
        findByTeam_Competition_IdOrderByTeam_TeamNumberAscMemberPositionAsc(
                Long competitionId
        );
}