package com.gabriellopesjds.cooperativism.stave.application.service;

import com.gabriellopesjds.api.model.StaveBaseRequestDTO;
import com.gabriellopesjds.api.model.StaveBaseResponseDTO;
import com.gabriellopesjds.api.model.StavePageableResponseDTO;
import com.gabriellopesjds.api.model.StaveRequestDTO;
import com.gabriellopesjds.api.model.StaveResponseDTO;
import com.gabriellopesjds.api.model.StaveUpdateRequestDTO;
import com.gabriellopesjds.cooperativism.stave.domain.model.Stave;
import com.gabriellopesjds.cooperativism.utils.PageResultFactory;
import com.gabriellopesjds.cooperativism.utils.UtilsTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;

import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockStaveDefault;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockStaveRequestDTO;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockStaveUpdateRequestDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class StaveFactoryTest {

    @Mock
    private PageResultFactory pageResultFactory;

    @InjectMocks
    private StaveFactory staveFactory;

    @Test
    void shouldBuildStaveFromValueStaveRequestDTO() {
        StaveRequestDTO staveRequestDTO = mockStaveRequestDTO();

        Stave stave = staveFactory.fromValue(staveRequestDTO);

        assertEquals(staveRequestDTO.getDescription(), stave.getDescription());
        assertEquals(staveRequestDTO.getTheme(), stave.getTheme());
        assertEquals(staveRequestDTO.getIdAssembly().toString(), stave.getAssembly().getId().toString());
    }

    @Test
    void shouldBuildStaveResponseDTO() {
        Stave stave = mockStaveDefault();
        StaveResponseDTO staveResponseDTO = staveFactory.buildResponse(stave);

        assertEquals(stave.getId().toString(), staveResponseDTO.getId().toString());
        assertEquals(stave.getTheme(), staveResponseDTO.getTheme());
        assertEquals(stave.getDescription(), staveResponseDTO.getDescription());
        assertEquals(stave.getAssembly().getId(), staveResponseDTO.getAssembly().getId());
        assertEquals(stave.getAssembly().getDescription(), staveResponseDTO.getAssembly().getDescription());
        assertEquals(stave.getAssembly().getDate(), staveResponseDTO.getAssembly().getDate());
        assertEquals(stave.getAssembly().getCreationDate(), staveResponseDTO.getAssembly().getCreationDate());
    }

    @Test
    void shouldBuildStaveFromValueStaveUpdateRequestDTO() {
        StaveUpdateRequestDTO staveRequestDTO = mockStaveUpdateRequestDTO();

        Stave stave = staveFactory.fromValue(staveRequestDTO);

        assertEquals(staveRequestDTO.getDescription(), stave.getDescription());
        assertEquals(staveRequestDTO.getTheme(), stave.getTheme());
    }

    @Test
    void shouldBuildStavePageableResponseDTO() {
        Stave stave = mockStaveDefault();
        final int pageNumber = 0;
        final int pageSize = 1;
        final PageRequest pageable = PageRequest.of(pageNumber, pageSize);
        final Page<Stave> page = new PageImpl<>(Collections.singletonList(stave), pageable, 1);

        final StavePageableResponseDTO stavePageableResponseDTO = staveFactory.buildResponse(page);

        assertEquals(1, stavePageableResponseDTO.getStaves().size());
    }

    @Test
    void shouldBuildStaveFromValueStaveBaseRequestDTO() {
        StaveBaseRequestDTO staveBaseRequestDTO = UtilsTest.mockStaveBaseRequestDTO();
        Stave stave = staveFactory.fromValue(staveBaseRequestDTO);

        assertEquals(staveBaseRequestDTO.getDescription(), stave.getDescription());
        assertEquals(staveBaseRequestDTO.getTheme(), stave.getTheme());
    }

    @Test
    void shouldBuildStaveBaseResponse(){
        Stave stave = mockStaveDefault();
        StaveBaseResponseDTO staveBaseResponseDTO = staveFactory.buildStaveBaseResponse(stave);

        assertEquals(stave.getId(), staveBaseResponseDTO.getId());
        assertEquals(stave.getTheme(), staveBaseResponseDTO.getTheme());
        assertEquals(stave.getDescription(), staveBaseResponseDTO.getDescription());
    }

}