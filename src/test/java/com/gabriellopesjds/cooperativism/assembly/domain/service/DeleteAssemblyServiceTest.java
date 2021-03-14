package com.gabriellopesjds.cooperativism.assembly.domain.service;

import com.gabriellopesjds.cooperativism.assembly.domain.model.Assembly;
import com.gabriellopesjds.cooperativism.assembly.repository.AssemblyRepository;
import com.gabriellopesjds.cooperativism.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockAssembly;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteAssemblyServiceTest {

    @Mock
    private AssemblyRepository assemblyRepository;

    @Mock
    private FinderAssemblyService finderAssemblyService;

    @InjectMocks
    private DeleteAssemblyService deleteAssemblyService;

    @Test
    void shouldDeleteAssemblyWithSuccess(){
        Assembly assembly = mockAssembly();

        when(finderAssemblyService.findById(assembly.getId())).thenReturn(assembly);

        deleteAssemblyService.delete(assembly.getId());

        verify(assemblyRepository).delete(eq(assembly));
    }

    @Test
    void shouldReturnBusinessExceptionWhenAssemblyNotFound(){
        Assembly assembly = mockAssembly();

        doThrow(BusinessException.class).when(finderAssemblyService).findById(eq(assembly.getId()));

        assertThrows((BusinessException.class), () -> deleteAssemblyService.delete(assembly.getId()));

        verify(assemblyRepository, never()).delete(eq(assembly));
    }

}