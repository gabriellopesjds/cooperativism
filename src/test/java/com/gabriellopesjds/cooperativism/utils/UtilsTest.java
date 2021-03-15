package com.gabriellopesjds.cooperativism.utils;

import com.gabriellopesjds.api.model.AssemblyBaseResponseDTO;
import com.gabriellopesjds.api.model.AssemblyPageableResponseDTO;
import com.gabriellopesjds.api.model.AssemblyRequestDTO;
import com.gabriellopesjds.api.model.AssemblyResponseDTO;
import com.gabriellopesjds.api.model.AssemblyUpdateRequestDTO;
import com.gabriellopesjds.api.model.AssociatedPageableResponseDTO;
import com.gabriellopesjds.api.model.AssociatedRequestDTO;
import com.gabriellopesjds.api.model.AssociatedResponseDTO;
import com.gabriellopesjds.api.model.AssociatedUpdateRequestDTO;
import com.gabriellopesjds.api.model.PageResultDTO;
import com.gabriellopesjds.api.model.StaveBaseRequestDTO;
import com.gabriellopesjds.api.model.StaveBaseResponseDTO;
import com.gabriellopesjds.api.model.StavePageableResponseDTO;
import com.gabriellopesjds.api.model.StaveRequestDTO;
import com.gabriellopesjds.api.model.StaveResponseDTO;
import com.gabriellopesjds.api.model.StaveUpdateRequestDTO;
import com.gabriellopesjds.api.model.VoteRequestDTO;
import com.gabriellopesjds.api.model.VoteResponseDTO;
import com.gabriellopesjds.api.model.VoteTypeDTO;
import com.gabriellopesjds.api.model.VotingSessionRequestDTO;
import com.gabriellopesjds.api.model.VotingSessionResponseDTO;
import com.gabriellopesjds.cooperativism.assembly.domain.model.Assembly;
import com.gabriellopesjds.cooperativism.associated.domain.model.Associated;
import com.gabriellopesjds.cooperativism.stave.domain.model.Stave;
import com.gabriellopesjds.cooperativism.vote.domain.model.Vote;
import com.gabriellopesjds.cooperativism.vote.domain.model.enumerated.VoteTypeEnum;
import com.gabriellopesjds.cooperativism.votingsession.domain.model.VotingSession;
import com.gabriellopesjds.cooperativism.votingsession.domain.model.enumerated.VotingSessionStateEnum;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UtilsTest {

    public static final UUID ASSEMBLY_ID = UUID.fromString("0f5ccfe8-5588-45c0-8804-517685d71308");
    public static final UUID STAVE_ID = UUID.fromString("c87a83ab-4054-4fa4-8199-f16ffff26af7");
    public static final UUID ASSOCIATED_ID = UUID.fromString("f87a83ab-4054-4fa4-8199-f16ffff26af2");
    public static final UUID VOTING_SESSION_ID = UUID.fromString("487a83ab-4034-4fa4-8199-f16ffff26af2");
    public static final UUID VOTE_ID = UUID.fromString("187a83ab-4034-4fa4-8199-f16ffff26af2");
    public static final LocalDateTime CREATION_DATE = LocalDateTime.now(ZoneOffset.UTC);
    public static final LocalDateTime FIXED_TIMESTAMP = LocalDateTime.of(2021, 3, 30, 10, 30);
    public static final String DESCRIPTION = "DESCRIPTION";
    private static final String THEME = "THEME";
    public static final Integer PAGE_SIZE = 10;
    public static final Integer PAGE_NUMBER = 0;
    public static final String SORT_DIRECTION = "DESC";
    public static final String SORT_BY = "DESCRIPTION";
    public static final String NAME = "ASSOCIATED";
    public static final String CPF = "65732190074";
    private static final Long DURATION = 10L;
    public static final LocalDateTime START_DATE = LocalDateTime.of(2021, 3, 30, 10, 30);
    public static final LocalDateTime END_DATE = LocalDateTime.of(2021, 3, 30, 10, 40);


    public static Assembly mockAssembly() {
        Assembly assembly = new Assembly();
        assembly.setId(ASSEMBLY_ID);
        assembly.setDescription(DESCRIPTION);
        assembly.setDate(FIXED_TIMESTAMP);
        assembly.setCreationDate(CREATION_DATE);
        return assembly;
    }

    public static Stave mockStaveDefault() {
        return mockStave(mockAssembly());
    }

    public static Stave mockStave(Assembly assembly) {
        Stave stave = new Stave();
        stave.setId(STAVE_ID);
        stave.setDescription(DESCRIPTION);
        stave.setTheme(THEME);
        stave.setAssembly(assembly);
        return stave;
    }

    public static StaveRequestDTO mockStaveRequestDTO() {
        StaveRequestDTO staveRequestDTO = new StaveRequestDTO();
        staveRequestDTO.setTheme(THEME);
        staveRequestDTO.setDescription(DESCRIPTION);
        staveRequestDTO.setIdAssembly(ASSEMBLY_ID);
        return staveRequestDTO;
    }

    public static StaveResponseDTO mockStaveResponseDTO(Stave stave) {
        AssemblyBaseResponseDTO assemblyDTO = new AssemblyBaseResponseDTO()
            .id(stave.getAssembly().getId())
            .description(stave.getAssembly().getDescription())
            .date(stave.getAssembly().getDate())
            .creationDate(stave.getAssembly().getCreationDate());

        return new StaveResponseDTO()
            .id(stave.getId())
            .description(stave.getDescription())
            .theme(stave.getTheme())
            .assembly(assemblyDTO);
    }

    public static StaveUpdateRequestDTO mockStaveUpdateRequestDTO() {
        StaveUpdateRequestDTO staveRequestDTO = new StaveUpdateRequestDTO();
        staveRequestDTO.setTheme(THEME);
        staveRequestDTO.setDescription(DESCRIPTION);
        return staveRequestDTO;
    }

    public static StavePageableResponseDTO mockStavePageableResponseDTO(){
        StavePageableResponseDTO stavePageableResponseDTO = new StavePageableResponseDTO();
        stavePageableResponseDTO.setStaves(Collections.singletonList(mockStaveResponseDTO(mockStaveDefault())));
        stavePageableResponseDTO.setPageResult(new PageResultDTO());
        return stavePageableResponseDTO;
    }

    public static StaveBaseRequestDTO mockStaveBaseRequestDTO(){
        return new StaveBaseRequestDTO()
            .description(DESCRIPTION)
            .theme(THEME);
    }

    public static AssemblyRequestDTO mockAssemblyRequestDTO() {
        return new AssemblyRequestDTO()
            .description(DESCRIPTION)
            .date(FIXED_TIMESTAMP)
            .staves(Arrays.asList(mockStaveBaseRequestDTO()));
    }

    public static StaveBaseResponseDTO mockStaveBaseResponseDTO(){
        return new StaveBaseResponseDTO()
            .description(DESCRIPTION)
            .theme(THEME);
    }

    public static AssemblyResponseDTO mockAssemblyResponseDTO(){
        return new AssemblyResponseDTO()
            .description(DESCRIPTION)
            .id(ASSEMBLY_ID)
            .date(FIXED_TIMESTAMP)
            .creationDate(CREATION_DATE)
            .staves(Arrays.asList(mockStaveBaseResponseDTO()));
    }

    public static AssemblyPageableResponseDTO mockAssemblyPageableResponseDTO(){
        return new AssemblyPageableResponseDTO()
            .assemblys(Collections.singletonList(mockAssemblyResponseDTO()))
            .pageResult(new PageResultDTO());
    }

    public static AssemblyUpdateRequestDTO mockAssemblyUpdateRequestDTO(){
        return new AssemblyUpdateRequestDTO()
            .description(DESCRIPTION)
            .date(FIXED_TIMESTAMP);
    }

    public static AssociatedRequestDTO mockAssociatedRequestDTO(){
        return new AssociatedRequestDTO()
            .name(NAME)
            .cpf(CPF);
    }

    public static Associated mockAssociated(){
        Associated associated = new Associated();
        associated.setId(ASSOCIATED_ID);
        associated.setName(NAME);
        associated.setCpf(CPF);
        return associated;
    }

    public static AssociatedResponseDTO mockAssociatedResponseDTO(){
        return new AssociatedResponseDTO()
            .id(ASSOCIATED_ID)
            .name(NAME)
            .cpf(CPF);
    }

    public static AssociatedPageableResponseDTO mockAssociatedPageableResponseDTO(){
        return new AssociatedPageableResponseDTO()
            .associateds(Collections.singletonList(mockAssociatedResponseDTO()))
            .pageResult(new PageResultDTO());
    }

    public static AssociatedUpdateRequestDTO mockAssociatedUpdateRequestDTO(){
        return new AssociatedUpdateRequestDTO()
            .name(NAME);
    }

    public static VotingSessionRequestDTO mockVotingSessionRequestDTO(){
        return new VotingSessionRequestDTO()
            .durationInMinutes(DURATION);
    }

    public static VotingSession mockVotingSession(){
        VotingSession votingSession = new VotingSession();
        votingSession.setDuration(DURATION);
        votingSession.setStave(mockStaveDefault());
        votingSession.setStartDate(FIXED_TIMESTAMP);
        votingSession.setEndDate(FIXED_TIMESTAMP);
        return votingSession;
    }

    public static VotingSession mockVotingSessionResult(VotingSession votingSession){
        votingSession.setTotalVotes(100L);
        votingSession.setTotalPositiveVotes(70L);
        votingSession.setTotalNegativeVotes(30L);
        votingSession.setStateEnum(votingSession.getEndDate().isBefore(LocalDateTime.now()) ?
            VotingSessionStateEnum.COMPLETED : VotingSessionStateEnum.OPEN);
        return votingSession;
    }

    public static VotingSessionResponseDTO mockVotingSessionResponseDTO(){
        return new VotingSessionResponseDTO()
            .id(VOTING_SESSION_ID)
            .startDate(START_DATE)
            .endDate(END_DATE)
            .durationInMinutes(DURATION)
            .stave(mockStaveResponseDTO(mockStaveDefault()));
    }

    public static VoteRequestDTO mockVoteRequestDTO(){
        return new VoteRequestDTO()
            .voteType(VoteTypeDTO.YES)
            .idAssociated(ASSOCIATED_ID);
    }

    public static Vote mockVote(){
        return mockVote(mockAssociated(), mockVotingSession(), VoteTypeEnum.YES);
    }

    public static Vote mockVote(Associated associated, VotingSession votingSession, VoteTypeEnum voteTypeEnum){
        Vote vote = new Vote();
        vote.setId(VOTE_ID);
        vote.setAssociated(associated);
        vote.setVotingSession(votingSession);
        vote.setVoteType(voteTypeEnum);
        return vote;
    }

    public static VoteResponseDTO mockVoteResponseDTO(){
        return new VoteResponseDTO()
            .id(VOTE_ID)
            .associated(mockAssociatedResponseDTO())
            .vote(VoteTypeDTO.YES);
    }

    public static List<Vote> generatedVotes(VotingSession votingSession, int amountPositive, int amountNegative) {
        List<Vote> list = new ArrayList<>();
        List<Vote> listPositive = Arrays.stream(new int[amountPositive])
            .mapToObj(operand -> generatedVote(votingSession, VoteTypeEnum.YES))
            .collect(Collectors.toList());

        List<Vote> listNegative = Arrays.stream(new int[amountNegative])
            .mapToObj(operand -> generatedVote(votingSession, VoteTypeEnum.NO))
            .collect(Collectors.toList());
        list.addAll(listPositive);
        list.addAll(listNegative);
        return list;
    }

    private static Vote generatedVote(VotingSession votingSession, VoteTypeEnum typeEnum) {
        Associated associated = mockAssociated();
        associated.setId(UUID.randomUUID());
        return mockVote(associated, votingSession, typeEnum);
    }
}
