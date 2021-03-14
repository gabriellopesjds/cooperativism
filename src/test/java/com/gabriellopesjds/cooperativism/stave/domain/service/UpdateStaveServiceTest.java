package com.gabriellopesjds.cooperativism.stave.domain.service;

import com.gabriellopesjds.cooperativism.exception.BusinessException;
import com.gabriellopesjds.cooperativism.stave.domain.model.Stave;
import com.gabriellopesjds.cooperativism.stave.repository.StaveRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.UUID;

import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockStaveDefault;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateStaveServiceTest {

    @Mock
    private StaveRepository staveRepository;

    @Mock
    private FinderStaveService finderStaveService;

    @InjectMocks
    private UpdateStaveService updateStaveService;


    @Test
    void shouldRegisterStaveWithSuccess(){
        Stave staveUpdate = mockStaveDefault();
        staveUpdate.setTheme("UPDATE");
        Stave staveSaved = mockStaveDefault();

        when(finderStaveService.findById(staveUpdate.getId())).thenReturn(staveSaved);

        updateStaveService.updateStave(staveUpdate.getId(), staveUpdate);

        verify(finderStaveService).findById(eq(staveUpdate.getId()));
        verify(staveRepository).save(eq(staveSaved));
    }

    @Test
    void shouldReturnBusinessExceptionWhenThemeIsDuplicate(){
        Stave staveUpdate = mockStaveDefault();
        staveUpdate.setTheme("UPDATE");

        Stave staveSaved = mockStaveDefault();
        Stave staveSavedDuplicate = mockStaveDefault();
        staveSavedDuplicate.setTheme("UPDATE");
        staveSavedDuplicate.setId(UUID.randomUUID());
        staveSaved.getAssembly().setStaveList(Arrays.asList(staveSaved,staveSavedDuplicate));

        when(finderStaveService.findById(staveUpdate.getId())).thenReturn(staveSaved);

        assertThrows((BusinessException.class), () -> updateStaveService.updateStave(staveUpdate.getId(), staveUpdate));

        verify(finderStaveService).findById(eq(staveUpdate.getId()));
        verify(staveRepository, never()).save(eq(staveSaved));
    }

}