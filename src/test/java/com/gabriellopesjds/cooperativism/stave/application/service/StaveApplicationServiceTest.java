package com.gabriellopesjds.cooperativism.stave.application.service;

import com.gabriellopesjds.api.model.StavePageableResponseDTO;
import com.gabriellopesjds.api.model.StaveRequestDTO;
import com.gabriellopesjds.api.model.StaveResponseDTO;
import com.gabriellopesjds.api.model.StaveUpdateRequestDTO;
import com.gabriellopesjds.cooperativism.stave.domain.model.Stave;
import com.gabriellopesjds.cooperativism.stave.domain.service.DeleteStaveService;
import com.gabriellopesjds.cooperativism.stave.domain.service.FinderStaveService;
import com.gabriellopesjds.cooperativism.stave.domain.service.RegisterStaveService;
import com.gabriellopesjds.cooperativism.stave.domain.service.UpdateStaveService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.util.UUID;

import static com.gabriellopesjds.cooperativism.utils.UtilsTest.PAGE_NUMBER;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.PAGE_SIZE;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.SORT_BY;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.SORT_DIRECTION;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockStaveDefault;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockStavePageableResponseDTO;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockStaveRequestDTO;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockStaveResponseDTO;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockStaveUpdateRequestDTO;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StaveApplicationServiceTest {

    @Mock
    private RegisterStaveService registerStaveService;

    @Mock
    private FinderStaveService finderStaveService;

    @Mock
    private UpdateStaveService updateStaveService;

    @Mock
    private StaveFactory staveFactory;

    @Mock
    private DeleteStaveService deleteStaveService;

    @InjectMocks
    private StaveApplicationService staveApplicationService;

    @Test
    void shouldRegisterStave() {
        StaveRequestDTO staveRequestDTO = mockStaveRequestDTO();
        Stave stave = mockStaveDefault();
        StaveResponseDTO staveResponseDTO = mockStaveResponseDTO(stave);
        when(staveFactory.fromValue(staveRequestDTO)).thenReturn(stave);
        when(registerStaveService.registerStave(stave)).thenReturn(stave);
        when(staveFactory.buildResponse(stave)).thenReturn(staveResponseDTO);

        staveApplicationService.registerStave(staveRequestDTO);

        verify(staveFactory).fromValue(eq(staveRequestDTO));
        verify(registerStaveService).registerStave(eq(stave));
        verify(staveFactory).buildResponse(eq(stave));
    }

    @Test
    void shouldFinderAllStave() {
        Page page = Mockito.mock(Page.class);
        StavePageableResponseDTO stavePageableResponseDTO = mockStavePageableResponseDTO();

        when(finderStaveService.finderAllStave(PAGE_SIZE, PAGE_NUMBER, SORT_DIRECTION, SORT_BY)).thenReturn(page);
        when(staveFactory.buildResponse(page)).thenReturn(stavePageableResponseDTO);

        staveApplicationService.finderAllStave(PAGE_SIZE, PAGE_NUMBER, SORT_DIRECTION, SORT_BY);

        verify(finderStaveService).finderAllStave(PAGE_SIZE, PAGE_NUMBER, SORT_DIRECTION, SORT_BY);
        verify(staveFactory).buildResponse(eq(page));
    }

    @Test
    void shouldFindStave() {
        Stave stave = mockStaveDefault();
        StaveResponseDTO staveResponseDTO = mockStaveResponseDTO(stave);
        when(finderStaveService.findById(stave.getId())).thenReturn(stave);
        when(staveFactory.buildResponse(stave)).thenReturn(staveResponseDTO);

        staveApplicationService.findStave(stave.getId());

        verify(finderStaveService).findById(eq(stave.getId()));
        verify(staveFactory).buildResponse(eq(stave));
    }

    @Test
    void shouldDeleteStave(){
        UUID uuid = UUID.randomUUID();
        staveApplicationService.deleteStave(uuid);

        verify(deleteStaveService).delete(eq(uuid));
    }

    @Test
    void shouldUpdateStave(){
        StaveUpdateRequestDTO staveRequestDTO = mockStaveUpdateRequestDTO();
        Stave stave = mockStaveDefault();
        StaveResponseDTO staveResponseDTO = mockStaveResponseDTO(stave);
        when(staveFactory.fromValue(staveRequestDTO)).thenReturn(stave);
        when(updateStaveService.updateStave(stave.getId(), stave)).thenReturn(stave);
        when(staveFactory.buildResponse(stave)).thenReturn(staveResponseDTO);

        staveApplicationService.updateStave(stave.getId(), staveRequestDTO);

        verify(staveFactory).fromValue(eq(staveRequestDTO));
        verify(updateStaveService).updateStave(eq(stave.getId()), eq(stave));
        verify(staveFactory).buildResponse(eq(stave));
    }

}