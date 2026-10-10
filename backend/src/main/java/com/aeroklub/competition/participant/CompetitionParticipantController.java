package com.aeroklub.competition.participant;

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
@RequestMapping("/api/competitions/{competitionId}/participants")
public class CompetitionParticipantController {

    private final CompetitionParticipantService participantService;

    public CompetitionParticipantController(
            CompetitionParticipantService participantService
    ) {
        this.participantService = participantService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompetitionParticipantResponse register(
            @PathVariable Long competitionId,
            @RequestBody RegisterParticipantRequest request
    ) {
        return participantService.register(competitionId, request);
    }

    @GetMapping
    public List<CompetitionParticipantResponse> findByCompetition(
            @PathVariable Long competitionId
    ) {
        return participantService.findByCompetition(competitionId);
    }
}