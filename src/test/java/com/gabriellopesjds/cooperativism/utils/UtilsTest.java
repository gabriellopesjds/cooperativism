package com.gabriellopesjds.cooperativism.utils;

import com.gabriellopesjds.api.model.AssemblyBaseResponseDTO;
import com.gabriellopesjds.api.model.AssemblyPageableResponseDTO;
import com.gabriellopesjds.api.model.AssemblyRequestDTO;
import com.gabriellopesjds.api.model.AssemblyResponseDTO;
import com.gabriellopesjds.api.model.AssemblyUpdateRequestDTO;
import com.gabriellopesjds.api.model.PageResultDTO;
import com.gabriellopesjds.api.model.StaveBaseRequestDTO;
import com.gabriellopesjds.api.model.StaveBaseResponseDTO;
import com.gabriellopesjds.api.model.StavePageableResponseDTO;
import com.gabriellopesjds.api.model.StaveRequestDTO;
import com.gabriellopesjds.api.model.StaveResponseDTO;
import com.gabriellopesjds.api.model.StaveUpdateRequestDTO;
import com.gabriellopesjds.cooperativism.assembly.domain.model.Assembly;
import com.gabriellopesjds.cooperativism.stave.domain.model.Stave;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

public class UtilsTest {

    public static final UUID ASSEMBLY_ID = UUID.fromString("0f5ccfe8-5588-45c0-8804-517685d71308");
    public static final UUID STAVE_ID = UUID.fromString("c87a83ab-4054-4fa4-8199-f16ffff26af7");
    public static final LocalDateTime CREATION_DATE = LocalDateTime.now(ZoneOffset.UTC);
    public static final LocalDateTime FIXED_TIMESTAMP = LocalDateTime.of(2021, 3, 30, 10, 30);
    public static final String DESCRIPTION = "DESCRIPTION";
    private static final String THEME = "THEME";
    public static final Integer PAGE_SIZE = 10;
    public static final Integer PAGE_NUMBER = 0;
    public static final String SORT_DIRECTION = "DESC";
    public static final String SORT_BY = "DESCRIPTION";


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
}
