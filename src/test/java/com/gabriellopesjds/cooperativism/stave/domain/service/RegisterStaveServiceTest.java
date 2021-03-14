package com.gabriellopesjds.cooperativism.stave.domain.service;

import com.gabriellopesjds.cooperativism.assembly.domain.model.Assembly;
import com.gabriellopesjds.cooperativism.assembly.domain.service.FinderAssemblyService;
import com.gabriellopesjds.cooperativism.exception.BusinessException;
import com.gabriellopesjds.cooperativism.stave.domain.model.Stave;
import com.gabriellopesjds.cooperativism.stave.repository.StaveRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockAssembly;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockStaveDefault;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterStaveServiceTest {

    @Mock
    private StaveRepository staveRepository;

    @Mock
    private FinderAssemblyService getterAssemblyService;

    @InjectMocks
    private RegisterStaveService registerStaveService;

    @Test
    void shouldRegisterStaveWithSuccess(){
        Stave stave = mockStaveDefault();
        Assembly assembly = mockAssembly();
        when(getterAssemblyService.findById(stave.getAssembly().getId())).thenReturn(assembly);

        registerStaveService.registerStave(stave);

        verify(getterAssemblyService).findById(eq(stave.getAssembly().getId()));
        verify(staveRepository).save(eq(stave));
    }

    @Test
    void shouldReturnBusinessExceptionWhenAssemblyNotFound(){
        Stave stave = mockStaveDefault();

        doThrow(BusinessException.class).when(getterAssemblyService).findById(eq(stave.getAssembly().getId()));

        assertThrows((BusinessException.class), () -> registerStaveService.registerStave(stave));

        verify(staveRepository, never()).save(eq(stave));
    }

    @Test
    void shouldReturnBusinessExceptionWhenThemeIsDuplicate(){
        Stave stave = mockStaveDefault();
        Assembly assembly = mockAssembly();
        assembly.setStaveList(Collections.singletonList(stave));
        when(getterAssemblyService.findById(stave.getAssembly().getId())).thenReturn(assembly);

        assertThrows((BusinessException.class), () -> registerStaveService.registerStave(stave));

        verify(staveRepository, never()).save(eq(stave));
    }

    @Test
    void shouldNotReturnBusinessExceptionWhenValidateListWithThemeDuplicate(){
        Assembly assembly = mockAssembly();
        Stave stave = mockStaveDefault();
        stave.setDescription("testing theme");
        assembly.setStaveList(Arrays.asList(stave, mockStaveDefault()));

        Assertions.assertDoesNotThrow(() -> registerStaveService.validateThemeDuplicate(assembly.getStaveList()));
    }

    @Test
    void shouldReturnBusinessExceptionWhenValidateListWithThemeDuplicate(){
        Assembly assembly = mockAssembly();
        assembly.setStaveList(Arrays.asList(mockStaveDefault(), mockStaveDefault()));

        assertThrows((BusinessException.class), () -> registerStaveService.validateThemeDuplicate(assembly.getStaveList()));
    }
}