package com.aeroklub.competition.participant;

import com.aeroklub.competition.competition.Competition;
import com.aeroklub.competition.competition.CompetitionRepository;
import com.aeroklub.competition.competitor.Competitor;
import com.aeroklub.competition.competitor.CompetitorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class CompetitionParticipantService {                    //PERFORMS REGISTRATION

    private final CompetitionParticipantRepository participantRepository;
    private final CompetitionRepository competitionRepository;
    private final CompetitorRepository competitorRepository;

    public CompetitionParticipantService(
            CompetitionParticipantRepository participantRepository,
            CompetitionRepository competitionRepository,
            CompetitorRepository competitorRepository
    ) {
        this.participantRepository = participantRepository;
        this.competitionRepository = competitionRepository;
        this.competitorRepository = competitorRepository;
    }

    public CompetitionParticipantResponse register(
            Long competitionId,
            RegisterParticipantRequest request
    ) {
        if (request.competitorId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Competitor ID is required"
            );
        }

        Competition competition = competitionRepository
                .findById(competitionId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Competition not found"
                ));

        Competitor competitor = competitorRepository
                .findById(request.competitorId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Competitor not found"
                ));

        boolean alreadyRegistered =
                participantRepository
                        .existsByCompetition_IdAndCompetitor_Id(
                                competitionId,
                                request.competitorId()
                        );

        if (alreadyRegistered) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Competitor is already registered for this competition"
            );
        }

        CompetitionParticipant participant =
                new CompetitionParticipant();

        participant.setCompetition(competition);
        participant.setCompetitor(competitor);

        CompetitionParticipant savedParticipant =
                participantRepository.save(participant);

        return CompetitionParticipantResponse.from(savedParticipant);
    }

    @Transactional(readOnly = true)
    public List<CompetitionParticipantResponse> findByCompetition(
            Long competitionId
    ) {
        if (!competitionRepository.existsById(competitionId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Competition not found"
            );
        }

        return participantRepository
                .findByCompetition_Id(competitionId)
                .stream()
                .map(CompetitionParticipantResponse::from)
                .toList();
    }
}