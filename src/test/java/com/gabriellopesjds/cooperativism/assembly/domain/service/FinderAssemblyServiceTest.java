package com.gabriellopesjds.cooperativism.assembly.domain.service;

import com.gabriellopesjds.cooperativism.assembly.domain.model.Assembly;
import com.gabriellopesjds.cooperativism.assembly.repository.AssemblyRepository;
import com.gabriellopesjds.cooperativism.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static com.gabriellopesjds.cooperativism.utils.UtilsTest.ASSEMBLY_ID;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.PAGE_NUMBER;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.PAGE_SIZE;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.SORT_DIRECTION;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockAssembly;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FinderAssemblyServiceTest {

    @Mock
    private AssemblyRepository assemblyRepository;

    @InjectMocks
    private FinderAssemblyService finderAssemblyService;

    @Test
    void shouldReturnPageAssembly(){
        finderAssemblyService.finderAllAssembly(PAGE_SIZE, PAGE_NUMBER, SORT_DIRECTION);

        verify(assemblyRepository).findAll(argThat((Pageable expected) ->
            expected.getPageSize() == PAGE_SIZE &&
            expected.getPageNumber() == PAGE_NUMBER &&
            expected.getSort().isSorted()));
    }

    @Test
    void shouldReturnAssemblyWhenFindById(){
        Assembly assembly = mockAssembly();
        when(assemblyRepository.findById(ASSEMBLY_ID)).thenReturn(Optional.of(assembly));

        Assembly assemblySaved = finderAssemblyService.findById(ASSEMBLY_ID);

        assertNotNull(assemblySaved);
    }

    @Test
    void shouldReturnBusinessExceptionWhenAssemblyNotFound(){
        when(assemblyRepository.findById(ASSEMBLY_ID)).thenReturn(Optional.empty());

        assertThrows((BusinessException.class), () -> finderAssemblyService.findById(ASSEMBLY_ID));
    }

    @Test
    void shouldFindByDescriptionAndDate(){
        Assembly assembly = mockAssembly();
        finderAssemblyService.findByDescriptionAndDate(assembly);

        verify(assemblyRepository).findByDescriptionAndDate(eq(assembly.getDescription()), eq(assembly.getDate()));
    }

    @Test
    void shouldFindByDescriptionAndDateIsNot(){
        Assembly assembly = mockAssembly();
        finderAssemblyService.findByDescriptionAndDateIsNot(assembly.getDescription(), assembly.getDate(), assembly.getId());

        verify(assemblyRepository).findByDescriptionAndDateAndIdIsNot(eq(assembly.getDescription()), eq(assembly.getDate()), eq(assembly.getId()));
    }

}