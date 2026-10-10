package com.aeroklub.competition.competitor;

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
@RequestMapping("/api/competitors")
public class CompetitorController {

    private final CompetitorService competitorService;

    public CompetitorController(CompetitorService competitorService) {
        this.competitorService = competitorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompetitorResponse create(
            @RequestBody CreateCompetitorRequest request
    ) {
        return competitorService.create(request);
    }

    @GetMapping
    public List<CompetitorResponse> findAll() {
        return competitorService.findAll();
    }

    @GetMapping("/{id}")
    public CompetitorResponse findById(@PathVariable Long id) {
        return competitorService.findById(id);
    }
}