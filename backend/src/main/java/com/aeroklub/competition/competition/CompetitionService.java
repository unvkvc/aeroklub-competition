package com.aeroklub.competition.competition;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class CompetitionService {

    private final CompetitionRepository competitionRepository;

    public CompetitionService(CompetitionRepository competitionRepository) {
        this.competitionRepository = competitionRepository;
    }

    public CompetitionResponse create(CreateCompetitionRequest request) {
        validate(request);

        Competition competition = new Competition();
        competition.setName(request.name());
        competition.setStartDate(request.startDate());
        competition.setEndDate(request.endDate());
        competition.setTeamSize(request.teamSize());
        competition.setBaseRoundCount(8);
        competition.setStatus(CompetitionStatus.DRAFT);
        competition.setPublicResultsEnabled(
                Boolean.TRUE.equals(request.publicResultsEnabled())
        );

        Competition savedCompetition =
                competitionRepository.save(competition);

        return CompetitionResponse.from(savedCompetition);
    }

    @Transactional(readOnly = true)
    public List<CompetitionResponse> findAll() {
        return competitionRepository.findAll()
                .stream()
                .map(CompetitionResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public CompetitionResponse findById(Long id) {
        Competition competition = competitionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Competition not found"
                ));

        return CompetitionResponse.from(competition);
    }

    private void validate(CreateCompetitionRequest request) {
        if (request.name() == null || request.name().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Competition name is required"
            );
        }

        if (request.startDate() == null || request.endDate() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Start date and end date are required"
            );
        }

        if (request.endDate().isBefore(request.startDate())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "End date cannot be before start date"
            );
        }

        if (request.teamSize() == null ||
                (request.teamSize() != 4 && request.teamSize() != 5)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Team size must be 4 or 5"
            );
        }
    }
}