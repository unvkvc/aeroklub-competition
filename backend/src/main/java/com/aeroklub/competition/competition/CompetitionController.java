package com.aeroklub.competition.competition;

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
@RequestMapping("/api/competitions")
public class CompetitionController {

    private final CompetitionService competitionService;

    public CompetitionController(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompetitionResponse create(
            @RequestBody CreateCompetitionRequest request
    ) {
        return competitionService.create(request);
    }

    @GetMapping
    public List<CompetitionResponse> findAll() {
        return competitionService.findAll();
    }

    @GetMapping("/{id}")
    public CompetitionResponse findById(@PathVariable Long id) {
        return competitionService.findById(id);
    }
}