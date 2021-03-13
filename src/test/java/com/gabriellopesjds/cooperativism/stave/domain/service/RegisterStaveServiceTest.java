package com.gabriellopesjds.cooperativism.stave.domain.service;

import com.gabriellopesjds.cooperativism.assembly.domain.model.Assembly;
import com.gabriellopesjds.cooperativism.assembly.domain.service.GetterAssemblyService;
import com.gabriellopesjds.cooperativism.exception.BusinessException;
import com.gabriellopesjds.cooperativism.stave.domain.model.Stave;
import com.gabriellopesjds.cooperativism.stave.repository.StaveRepository;
import com.gabriellopesjds.cooperativism.utils.UtilsTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    private GetterAssemblyService getterAssemblyService;

    @InjectMocks
    private RegisterStaveService registerStaveService;

    @Test
    void shouldRegisterStaveWithSuccess(){
        Stave stave = UtilsTest.mockStaveDefault();
        Assembly assembly = UtilsTest.mockAssembly();
        when(getterAssemblyService.findById(eq(stave.getAssembly().getId()))).thenReturn(assembly);

        registerStaveService.registerStave(stave);

        verify(staveRepository).save(eq(stave));
    }

    @Test
    void shouldReturnBusinessExceptionWhenAssemblyNotFound(){
        Stave stave = UtilsTest.mockStaveDefault();

        doThrow(BusinessException.class).when(getterAssemblyService).findById(eq(stave.getAssembly().getId()));

        assertThrows((BusinessException.class), () -> registerStaveService.registerStave(stave));

        verify(staveRepository, never()).save(eq(stave));
    }
}