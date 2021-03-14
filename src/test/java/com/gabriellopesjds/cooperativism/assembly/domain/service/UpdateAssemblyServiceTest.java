package com.gabriellopesjds.cooperativism.assembly.domain.service;

import com.gabriellopesjds.cooperativism.assembly.domain.model.Assembly;
import com.gabriellopesjds.cooperativism.assembly.repository.AssemblyRepository;
import com.gabriellopesjds.cooperativism.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockAssembly;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateAssemblyServiceTest {

    @Mock
    private AssemblyRepository assemblyRepository;

    @Mock
    private FinderAssemblyService finderAssemblyService;

    @InjectMocks
    private UpdateAssemblyService updateAssemblyService;


    @Test
    void shouldRegisterAssemblyWithSuccess(){
        Assembly assembly = mockAssembly();

        when(finderAssemblyService.findById(assembly.getId())).thenReturn(assembly);
        when(finderAssemblyService.findByDescriptionAndDateIsNot(
            assembly.getDescription(),
            assembly.getDate(),
            assembly.getId())).thenReturn(Optional.empty());

        updateAssemblyService.updateAssembly(assembly.getId(), assembly);

        verify(finderAssemblyService).findById(eq(assembly.getId()));
        verify(finderAssemblyService).findByDescriptionAndDateIsNot(
            eq(assembly.getDescription()),
            eq(assembly.getDate()),
            eq(assembly.getId())
        );
        verify(assemblyRepository).save(eq(assembly));
    }

    @Test
    void shouldReturnBusinessExceptionWhenAssemblyIsDuplicate(){
        Assembly assembly = mockAssembly();

        when(finderAssemblyService.findById(assembly.getId())).thenReturn(assembly);
        when(finderAssemblyService.findByDescriptionAndDateIsNot(
            assembly.getDescription(), assembly.getDate(), assembly.getId())).thenReturn(Optional.of(assembly));

        assertThrows((BusinessException.class), () -> updateAssemblyService.updateAssembly(assembly.getId(), assembly));

        verify(finderAssemblyService).findById(eq(assembly.getId()));
        verify(assemblyRepository, never()).save(eq(assembly));
    }

}