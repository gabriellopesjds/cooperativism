package com.gabriellopesjds.cooperativism.assembly.application.service;

import com.gabriellopesjds.api.model.AssemblyPageableResponseDTO;
import com.gabriellopesjds.api.model.AssemblyRequestDTO;
import com.gabriellopesjds.api.model.AssemblyResponseDTO;
import com.gabriellopesjds.api.model.AssemblyUpdateRequestDTO;
import com.gabriellopesjds.api.model.PageResultDTO;
import com.gabriellopesjds.api.model.StaveBaseRequestDTO;
import com.gabriellopesjds.cooperativism.assembly.domain.model.Assembly;
import com.gabriellopesjds.cooperativism.stave.application.service.StaveFactory;
import com.gabriellopesjds.cooperativism.stave.domain.model.Stave;
import com.gabriellopesjds.cooperativism.utils.PageResultFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;

import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockAssembly;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockAssemblyRequestDTO;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockAssemblyUpdateRequestDTO;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockStaveDefault;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssemblyFactoryTest {

    @Mock
    private StaveFactory staveFactory;

    @Mock
    private PageResultFactory pageResultFactory;

    @InjectMocks
    private AssemblyFactory assemblyFactory;

    @Test
    void shouldBuildAssemblyFromValueAssemblyRequestDTO() {
        AssemblyRequestDTO assemblyRequestDTO = mockAssemblyRequestDTO();
        Stave stave = mockStaveDefault();

        when(staveFactory.fromValue(any(StaveBaseRequestDTO.class))).thenReturn(stave);
        Assembly assembly = assemblyFactory.fromValue(assemblyRequestDTO);

        assertEquals(assemblyRequestDTO.getDescription(), assembly.getDescription());
        assertEquals(assemblyRequestDTO.getDate(), assembly.getDate());
        assertEquals(1, assembly.getStaveList().size());
    }

    @Test
    void shouldBuildAssemblyFromValueAssemblyUpdateRequestDTO() {
        AssemblyUpdateRequestDTO assemblyUpdateRequestDTO = mockAssemblyUpdateRequestDTO();

        Assembly assembly = assemblyFactory.fromValue(assemblyUpdateRequestDTO);

        assertEquals(assemblyUpdateRequestDTO.getDescription(), assembly.getDescription());
        assertEquals(assemblyUpdateRequestDTO.getDate(), assembly.getDate());
    }

    @Test
    void shouldBuildAssemblyResponseDTO() {
        Assembly assembly = mockAssembly();
        AssemblyResponseDTO assemblyResponseDTO = assemblyFactory.buildResponse(assembly);

        assertEquals(assembly.getId(), assemblyResponseDTO.getId());
        assertEquals(assembly.getDescription(), assemblyResponseDTO.getDescription());
        assertEquals(assembly.getDate(), assemblyResponseDTO.getDate());
        assertEquals(assembly.getCreationDate(), assemblyResponseDTO.getCreationDate());
        assertEquals(0, assemblyResponseDTO.getStaves().size());
    }

    @Test
    void shouldBuildAssemblyPageableResponseDTO() {
        Assembly assembly = mockAssembly();
        final int pageNumber = 0;
        final int pageSize = 1;
        final PageRequest pageable = PageRequest.of(pageNumber, pageSize);
        final Page<Assembly> page = new PageImpl<>(Collections.singletonList(assembly), pageable, 1);

        when(pageResultFactory.createPageFrom(page)).thenReturn(new PageResultDTO());
        final AssemblyPageableResponseDTO assemblyPageableResponseDTO = assemblyFactory.buildResponse(page);

        assertEquals(1, assemblyPageableResponseDTO.getAssemblys().size());
    }

}