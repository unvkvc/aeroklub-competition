package com.aeroklub.competition.team;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/competitions/{competitionId}/teams")
public class CompetitionTeamController {

    private final CompetitionTeamService teamService;

    public CompetitionTeamController(
            CompetitionTeamService teamService
    ) {
        this.teamService = teamService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompetitionTeamResponse create(
            @PathVariable Long competitionId,
            @RequestBody CreateCompetitionTeamRequest request
    ) {
        return teamService.create(competitionId, request);
    }

    @GetMapping
    public List<CompetitionTeamResponse> findByCompetition(
            @PathVariable Long competitionId
    ) {
        return teamService.findByCompetition(competitionId);
    }

    @PostMapping("/{teamId}/members")
    @ResponseStatus(HttpStatus.CREATED)
    public CompetitionTeamResponse addMember(
            @PathVariable Long competitionId,
            @PathVariable Long teamId,
            @RequestBody AddCompetitionTeamMemberRequest request
    ) {
        return teamService.addMember(
                competitionId,
                teamId,
                request
        );
    }
}