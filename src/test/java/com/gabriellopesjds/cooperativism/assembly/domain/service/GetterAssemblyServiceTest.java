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

import static com.gabriellopesjds.cooperativism.utils.UtilsTest.ASSEMBLY_ID;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockAssembly;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetterAssemblyServiceTest {

    @Mock
    private AssemblyRepository assemblyRepository;

    @InjectMocks
    private GetterAssemblyService getterAssemblyService;

    @Test
    void shouldReturnAssemblyWhenFindById(){
        Assembly assembly = mockAssembly();
        when(assemblyRepository.findById(ASSEMBLY_ID)).thenReturn(Optional.of(assembly));

        Assembly assemblySaved = getterAssemblyService.findById(ASSEMBLY_ID);

        assertNotNull(assemblySaved);
    }

    @Test
    void shouldReturnBusinessExceptionWhenAssemblyNotFound(){
        when(assemblyRepository.findById(ASSEMBLY_ID)).thenReturn(Optional.empty());

        assertThrows((BusinessException.class), () -> getterAssemblyService.findById(ASSEMBLY_ID));
    }

}