package com.gabriellopesjds.cooperativism.assembly.domain.service;

import com.gabriellopesjds.cooperativism.assembly.domain.model.Assembly;
import com.gabriellopesjds.cooperativism.assembly.repository.AssemblyRepository;
import com.gabriellopesjds.cooperativism.exception.BusinessException;
import com.gabriellopesjds.cooperativism.stave.domain.service.RegisterStaveService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockAssembly;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockStaveDefault;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterAssemblyServiceTest {

    @Mock
    private AssemblyRepository assemblyRepository;

    @Mock
    private FinderAssemblyService finderAssemblyService;

    @Mock
    private RegisterStaveService registerStaveService;

    @InjectMocks
    private RegisterAssemblyService registerAssemblyService;

    @Test
    void shouldRegisterAssemblyWithSuccess(){
        Assembly assembly = mockAssembly();

        when(finderAssemblyService.findByDescriptionAndDate(assembly)).thenReturn(Optional.empty());

        registerAssemblyService.registerAssembly(assembly);

        verify(finderAssemblyService).findByDescriptionAndDate(eq(assembly));
        verify(registerStaveService).validateThemeDuplicate(eq(assembly.getStaveList()));
        verify(assemblyRepository).save(eq(assembly));
    }

    @Test
    void shouldReturnBusinessExceptionWhenAssemblyIsDuplicate(){
        Assembly assembly = mockAssembly();

        when(finderAssemblyService.findByDescriptionAndDate(assembly)).thenReturn(Optional.of(assembly));

        assertThrows((BusinessException.class), () -> registerAssemblyService.registerAssembly(assembly));

        verify(assemblyRepository, never()).save(eq(assembly));
    }

    @Test
    void shouldReturnBusinessExceptionWhenThemeIsDuplicate(){
        Assembly assembly = mockAssembly();
        assembly.setStaveList(Arrays.asList(mockStaveDefault(), mockStaveDefault()));

        when(finderAssemblyService.findByDescriptionAndDate(assembly)).thenReturn(Optional.empty());
        doThrow(BusinessException.class).when(registerStaveService).validateThemeDuplicate(assembly.getStaveList());

        assertThrows((BusinessException.class), () -> registerAssemblyService.registerAssembly(assembly));

        verify(assemblyRepository, never()).save(eq(assembly));
    }
}