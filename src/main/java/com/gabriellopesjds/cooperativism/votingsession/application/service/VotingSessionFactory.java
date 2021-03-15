package com.gabriellopesjds.cooperativism.votingsession.application.service;

import com.gabriellopesjds.api.model.VotingSessionRequestDTO;
import com.gabriellopesjds.api.model.VotingSessionResponseDTO;
import com.gabriellopesjds.api.model.VotingSessionResultBaseResponseDTO;
import com.gabriellopesjds.api.model.VotingSessionResultResponseDTO;
import com.gabriellopesjds.api.model.VotingSessionStateDTO;
import com.gabriellopesjds.cooperativism.assembly.application.service.AssemblyFactory;
import com.gabriellopesjds.cooperativism.stave.application.service.StaveFactory;
import com.gabriellopesjds.cooperativism.stave.domain.model.Stave;
import com.gabriellopesjds.cooperativism.votingsession.domain.model.VotingSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.gabriellopesjds.api.model.VotingSessionStateDTO.COMPLETED;
import static com.gabriellopesjds.api.model.VotingSessionStateDTO.OPEN;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VotingSessionFactory {

    private final StaveFactory staveFactory;
    private final AssemblyFactory assemblyFactory;

    public VotingSession fromValue(UUID id, VotingSessionRequestDTO votingSessionRequestDTO) {
        Stave stave = new Stave();
        stave.setId(id);
        VotingSession votingSession = new VotingSession();
        votingSession.setDuration(votingSessionRequestDTO.getDurationInMinutes());
        votingSession.setStartDate(LocalDateTime.now());
        votingSession.setEndDate(votingSession.getStartDate().plusMinutes(votingSession.getDuration()));
        votingSession.setStave(stave);
        return votingSession;
    }

    public VotingSessionResponseDTO buildResponse(VotingSession votingSession) {
        return new VotingSessionResponseDTO()
            .id(votingSession.getId())
            .durationInMinutes(votingSession.getDuration())
            .startDate(votingSession.getStartDate())
            .endDate(votingSession.getEndDate())
            .state(votingSession.getEndDate().isBefore(LocalDateTime.now()) ? COMPLETED : OPEN)
            .stave(staveFactory.buildResponse(votingSession.getStave()));
    }

    public List<VotingSessionResultBaseResponseDTO> buildListVotingSessionResultBaseResponseDTO(List<VotingSession> votingSessionList){
        return votingSessionList
            .stream()
            .map(this::getVotingSessionResultBaseResponseDTO)
            .collect(Collectors.toList());
    }

    private VotingSessionResultBaseResponseDTO getVotingSessionResultBaseResponseDTO(VotingSession votingSession) {
        return new VotingSessionResultBaseResponseDTO()
            .id(votingSession.getId())
            .durationInMinutes(votingSession.getDuration())
            .startDate(votingSession.getStartDate())
            .endDate(votingSession.getEndDate())
            .totalVotes(votingSession.getTotalVotes())
            .totalPositiveVotes(votingSession.getTotalPositiveVotes())
            .totalNegativeVotes(votingSession.getTotalNegativeVotes())
            .state(VotingSessionStateDTO.valueOf(votingSession.getStateEnum().name()));
    }

    public VotingSessionResultResponseDTO buildResponse(Stave stave) {
        return new VotingSessionResultResponseDTO()
            .id(stave.getId())
            .theme(stave.getTheme())
            .description(stave.getDescription())
            .assembly(assemblyFactory.buildAssemblyBaseResponseDTO(stave.getAssembly()))
            .votingSession(buildListVotingSessionResultBaseResponseDTO(stave.getVotingSessionList()));
    }

}
