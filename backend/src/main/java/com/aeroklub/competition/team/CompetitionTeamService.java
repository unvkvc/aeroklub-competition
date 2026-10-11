package com.aeroklub.competition.team;

import com.aeroklub.competition.competition.Competition;
import com.aeroklub.competition.competition.CompetitionRepository;
import com.aeroklub.competition.participant.CompetitionParticipant;
import com.aeroklub.competition.participant.CompetitionParticipantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class CompetitionTeamService {

    private final CompetitionTeamRepository teamRepository;
    private final CompetitionTeamMemberRepository memberRepository;
    private final CompetitionRepository competitionRepository;
    private final CompetitionParticipantRepository participantRepository;

    public CompetitionTeamService(
            CompetitionTeamRepository teamRepository,
            CompetitionTeamMemberRepository memberRepository,
            CompetitionRepository competitionRepository,
            CompetitionParticipantRepository participantRepository
    ) {
        this.teamRepository = teamRepository;
        this.memberRepository = memberRepository;
        this.competitionRepository = competitionRepository;
        this.participantRepository = participantRepository;
    }

    public CompetitionTeamResponse create(
            Long competitionId,
            CreateCompetitionTeamRequest request
    ) {
        Competition competition = competitionRepository
                .findById(competitionId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Competition not found"
                ));

        validateTeamRequest(request);

        if (teamRepository.existsByCompetition_IdAndTeamNumber(
                competitionId,
                request.teamNumber()
        )) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Team number already exists in this competition"
            );
        }

        CompetitionTeam team = new CompetitionTeam();
        team.setCompetition(competition);
        team.setTeamNumber(request.teamNumber());
        team.setName(request.name().trim());

        CompetitionTeam savedTeam = teamRepository.save(team);

        return CompetitionTeamResponse.from(
                savedTeam,
                List.of()
        );
    }

    public CompetitionTeamResponse addMember(
            Long competitionId,
            Long teamId,
            AddCompetitionTeamMemberRequest request
    ) {
        if (request.participantId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Participant ID is required"
            );
        }

        if (request.memberPosition() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Member position is required"
            );
        }

        CompetitionTeam team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Team not found"
                ));

        if (!Objects.equals(
                team.getCompetition().getId(),
                competitionId
        )) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Team does not belong to this competition"
            );
        }

        CompetitionParticipant participant =
                participantRepository.findById(request.participantId())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Participant not found"
                        ));

        if (!Objects.equals(
                participant.getCompetition().getId(),
                competitionId
        )) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Participant does not belong to this competition"
            );
        }

        int teamSize = team.getCompetition().getTeamSize();

        if (memberRepository.countByTeam_Id(teamId) >= teamSize) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Team already has the maximum number of members"
            );
        }

        if (request.memberPosition().ordinal() >= teamSize) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "This member position is not allowed for the team size"
            );
        }

        if (memberRepository.existsByParticipant_Id(
                request.participantId()
        )) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Participant is already assigned to a team"
            );
        }

        if (memberRepository.existsByTeam_IdAndMemberPosition(
                teamId,
                request.memberPosition()
        )) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Member position is already occupied"
            );
        }

        CompetitionTeamMember member = new CompetitionTeamMember();
        member.setTeam(team);
        member.setParticipant(participant);
        member.setMemberPosition(request.memberPosition());

        memberRepository.save(member);

        List<CompetitionTeamMember> members =
                memberRepository.findByTeam_IdOrderByMemberPosition(teamId);

        return CompetitionTeamResponse.from(team, members);
    }

    @Transactional(readOnly = true)
    public List<CompetitionTeamResponse> findByCompetition(
            Long competitionId
    ) {
        if (!competitionRepository.existsById(competitionId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Competition not found"
            );
        }

        List<CompetitionTeam> teams =
                teamRepository
                        .findByCompetition_IdOrderByTeamNumber(
                                competitionId
                        );

        List<CompetitionTeamMember> allMembers =
                memberRepository
                        .findByTeam_Competition_IdOrderByTeam_TeamNumberAscMemberPositionAsc(
                                competitionId
                        );

        Map<Long, List<CompetitionTeamMember>> membersByTeam =
                allMembers.stream()
                        .collect(Collectors.groupingBy(
                                member -> member.getTeam().getId()
                        ));

        return teams.stream()
                .map(team -> CompetitionTeamResponse.from(
                        team,
                        membersByTeam.getOrDefault(
                                team.getId(),
                                List.of()
                        )
                ))
                .toList();
    }

    private void validateTeamRequest(
            CreateCompetitionTeamRequest request
    ) {
        if (request.teamNumber() == null ||
                request.teamNumber() < 1 ||
                request.teamNumber() > 20) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Team number must be between 1 and 20"
            );
        }

        if (request.name() == null || request.name().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Team name is required"
            );
        }
    }
}