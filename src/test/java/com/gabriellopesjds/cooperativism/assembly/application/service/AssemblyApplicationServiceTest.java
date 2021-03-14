package com.gabriellopesjds.cooperativism.assembly.application.service;

import com.gabriellopesjds.api.model.AssemblyPageableResponseDTO;
import com.gabriellopesjds.api.model.AssemblyRequestDTO;
import com.gabriellopesjds.api.model.AssemblyResponseDTO;
import com.gabriellopesjds.api.model.AssemblyUpdateRequestDTO;
import com.gabriellopesjds.cooperativism.assembly.domain.model.Assembly;
import com.gabriellopesjds.cooperativism.assembly.domain.service.DeleteAssemblyService;
import com.gabriellopesjds.cooperativism.assembly.domain.service.FinderAssemblyService;
import com.gabriellopesjds.cooperativism.assembly.domain.service.RegisterAssemblyService;
import com.gabriellopesjds.cooperativism.assembly.domain.service.UpdateAssemblyService;
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
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.SORT_DIRECTION;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockAssembly;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockAssemblyPageableResponseDTO;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockAssemblyRequestDTO;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockAssemblyResponseDTO;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockAssemblyUpdateRequestDTO;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssemblyApplicationServiceTest {

    @Mock
    private RegisterAssemblyService registerAssemblyService;

    @Mock
    private FinderAssemblyService finderAssemblyService;

    @Mock
    private UpdateAssemblyService updateAssemblyService;

    @Mock
    private DeleteAssemblyService deleteAssemblyService;

    @Mock
    private AssemblyFactory assemblyFactory;

    @InjectMocks
    private AssemblyApplicationService assemblyApplicationService;

    @Test
    void shouldRegisterAssembly() {
        AssemblyRequestDTO assemblyRequestDTO = mockAssemblyRequestDTO();
        Assembly assembly = mockAssembly();
        AssemblyResponseDTO assemblyResponseDTO = mockAssemblyResponseDTO();

        when(assemblyFactory.fromValue(assemblyRequestDTO)).thenReturn(assembly);
        when(registerAssemblyService.registerAssembly(assembly)).thenReturn(assembly);
        when(assemblyFactory.buildResponse(assembly)).thenReturn(assemblyResponseDTO);

        assemblyApplicationService.registerAssembly(assemblyRequestDTO);

        verify(assemblyFactory).fromValue(eq(assemblyRequestDTO));
        verify(registerAssemblyService).registerAssembly(eq(assembly));
        verify(assemblyFactory).buildResponse(eq(assembly));
    }

    @Test
    void shouldFinderAllAssembly() {
        Page page = Mockito.mock(Page.class);
        AssemblyPageableResponseDTO assemblyPageableResponseDTO = mockAssemblyPageableResponseDTO();

        when(finderAssemblyService.finderAllAssembly(PAGE_SIZE, PAGE_NUMBER, SORT_DIRECTION)).thenReturn(page);
        when(assemblyFactory.buildResponse(page)).thenReturn(assemblyPageableResponseDTO);

        assemblyApplicationService.finderAllAssembly(PAGE_SIZE, PAGE_NUMBER, SORT_DIRECTION);

        verify(finderAssemblyService).finderAllAssembly(PAGE_SIZE, PAGE_NUMBER, SORT_DIRECTION);
        verify(assemblyFactory).buildResponse(eq(page));
    }

    @Test
    void shouldFindAssembly() {
        Assembly assembly = mockAssembly();
        AssemblyResponseDTO assemblyResponseDTO = mockAssemblyResponseDTO();
        when(finderAssemblyService.findById(assembly.getId())).thenReturn(assembly);
        when(assemblyFactory.buildResponse(assembly)).thenReturn(assemblyResponseDTO);

        assemblyApplicationService.findAssembly(assembly.getId());

        verify(finderAssemblyService).findById(eq(assembly.getId()));
        verify(assemblyFactory).buildResponse(eq(assembly));
    }

    @Test
    void shouldDeleteAssembly() {
        UUID uuid = UUID.randomUUID();
        assemblyApplicationService.deleteAssembly(uuid);

        verify(deleteAssemblyService).delete(eq(uuid));
    }

    @Test
    void shouldUpdateAssembly() {
        AssemblyUpdateRequestDTO assemblyUpdateRequestDTO = mockAssemblyUpdateRequestDTO();
        Assembly assembly = mockAssembly();
        AssemblyResponseDTO assemblyResponseDTO = mockAssemblyResponseDTO();
        when(assemblyFactory.fromValue(assemblyUpdateRequestDTO)).thenReturn(assembly);
        when(updateAssemblyService.updateAssembly(assembly.getId(), assembly)).thenReturn(assembly);
        when(assemblyFactory.buildResponse(assembly)).thenReturn(assemblyResponseDTO);

        assemblyApplicationService.updateAssembly(assembly.getId(), assemblyUpdateRequestDTO);

        verify(assemblyFactory).fromValue(eq(assemblyUpdateRequestDTO));
        verify(updateAssemblyService).updateAssembly(eq(assembly.getId()), eq(assembly));
        verify(assemblyFactory).buildResponse(eq(assembly));
    }

}