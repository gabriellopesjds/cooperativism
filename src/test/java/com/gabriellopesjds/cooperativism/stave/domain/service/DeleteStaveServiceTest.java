package com.gabriellopesjds.cooperativism.stave.domain.service;

import com.gabriellopesjds.cooperativism.exception.BusinessException;
import com.gabriellopesjds.cooperativism.stave.domain.model.Stave;
import com.gabriellopesjds.cooperativism.stave.repository.StaveRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockStaveDefault;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteStaveServiceTest {

    @Mock
    private StaveRepository staveRepository;

    @Mock
    private FinderStaveService finderStaveService;

    @InjectMocks
    private DeleteStaveService deleteStaveService;

    @Test
    void shouldDeleteStaveWithSuccess(){
        Stave stave = mockStaveDefault();

        when(finderStaveService.findById(stave.getId())).thenReturn(stave);

        deleteStaveService.delete(stave.getId());

        verify(staveRepository).delete(eq(stave));
    }

    @Test
    void shouldReturnBusinessExceptionWhenAssemblyNotFound(){
        Stave stave = mockStaveDefault();

        doThrow(BusinessException.class).when(finderStaveService).findById(eq(stave.getId()));

        assertThrows((BusinessException.class), () -> deleteStaveService.delete(stave.getId()));

        verify(staveRepository, never()).delete(eq(stave));
    }

}