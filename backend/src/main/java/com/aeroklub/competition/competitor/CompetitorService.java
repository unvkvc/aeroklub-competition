package com.aeroklub.competition.competitor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Year;
import java.util.List;

@Service
@Transactional
public class CompetitorService {

    private final CompetitorRepository competitorRepository;

    public CompetitorService(CompetitorRepository competitorRepository) {
        this.competitorRepository = competitorRepository;
    }

    public CompetitorResponse create(CreateCompetitorRequest request) {
        validate(request);

        Competitor competitor = new Competitor();
        competitor.setFirstName(request.firstName().trim());
        competitor.setLastName(request.lastName().trim());
        competitor.setBirthYear(request.birthYear());
        competitor.setGender(request.gender());
        competitor.setCountry(request.country().trim());

        Competitor savedCompetitor =
                competitorRepository.save(competitor);

        return CompetitorResponse.from(savedCompetitor);
    }

    @Transactional(readOnly = true)
    public List<CompetitorResponse> findAll() {
        return competitorRepository.findAll()
                .stream()
                .map(CompetitorResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public CompetitorResponse findById(Long id) {
        Competitor competitor = competitorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Competitor not found"
                ));

        return CompetitorResponse.from(competitor);
    }

    private void validate(CreateCompetitorRequest request) {
        if (request.firstName() == null ||
                request.firstName().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "First name is required"
            );
        }

        if (request.lastName() == null ||
                request.lastName().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Last name is required"
            );
        }

        int currentYear = Year.now().getValue();

        if (request.birthYear() == null ||
                request.birthYear() < 1900 ||
                request.birthYear() > currentYear) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Birth year is invalid"
            );
        }

        if (request.gender() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Gender is required"
            );
        }

        if (request.country() == null ||
                request.country().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Country is required"
            );
        }
    }
}