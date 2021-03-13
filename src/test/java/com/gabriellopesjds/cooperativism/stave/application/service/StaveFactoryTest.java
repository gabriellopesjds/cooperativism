package com.gabriellopesjds.cooperativism.stave.application.service;

import com.gabriellopesjds.api.model.StaveRequestDTO;
import com.gabriellopesjds.api.model.StaveResponseDTO;
import com.gabriellopesjds.cooperativism.stave.domain.model.Stave;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockStaveDefault;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockStaveRequestDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = StaveFactory.class)
class StaveFactoryTest {

    @Autowired
    private StaveFactory staveFactory;

    @Test
    void shouldBuildStaveFromValueStaveRequestDTO(){
        StaveRequestDTO staveRequestDTO = mockStaveRequestDTO();

        Stave stave = staveFactory.fromValue(staveRequestDTO);

        assertEquals(staveRequestDTO.getDescription(), stave.getDescription());
        assertEquals(staveRequestDTO.getTheme(), stave.getTheme());
        assertEquals(staveRequestDTO.getIdAssembly().toString(), stave.getAssembly().getId().toString());
    }

    @Test
    void shouldBuildStaveResponseDTO(){
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

}