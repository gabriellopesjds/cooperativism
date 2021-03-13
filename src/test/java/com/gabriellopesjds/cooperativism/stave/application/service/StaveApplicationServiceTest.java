package com.gabriellopesjds.cooperativism.stave.application.service;

import com.gabriellopesjds.api.model.StaveRequestDTO;
import com.gabriellopesjds.api.model.StaveResponseDTO;
import com.gabriellopesjds.cooperativism.stave.domain.model.Stave;
import com.gabriellopesjds.cooperativism.stave.domain.service.RegisterStaveService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockStaveDefault;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockStaveRequestDTO;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockStaveResponseDTO;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StaveApplicationServiceTest {

    @Mock
    private RegisterStaveService registerStaveService;

    @Mock
    private StaveFactory staveFactory;

    @InjectMocks
    private StaveApplicationService staveApplicationService;

    @Test
    void shouldRegisterStave() {
        StaveRequestDTO staveRequestDTO = mockStaveRequestDTO();
        Stave stave = mockStaveDefault();
        StaveResponseDTO staveResponseDTO = mockStaveResponseDTO(stave);
        when(staveFactory.fromValue(eq(staveRequestDTO))).thenReturn(stave);
        when(registerStaveService.registerStave(eq(stave))).thenReturn(stave);
        when(staveFactory.buildResponse(eq(stave))).thenReturn(staveResponseDTO);

        staveApplicationService.registerStave(staveRequestDTO);

        verify(staveFactory).fromValue(eq(staveRequestDTO));
        verify(registerStaveService).registerStave(eq(stave));
        verify(staveFactory).buildResponse(eq(stave));
    }

}