package com.gabriellopesjds.cooperativism.associated.domain.service;

import com.gabriellopesjds.cooperativism.associated.domain.model.Associated;
import com.gabriellopesjds.cooperativism.associated.repository.AssociatedRepository;
import com.gabriellopesjds.cooperativism.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
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
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockAssociated;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FinderAssociatedServiceTest {

    @Mock
    private AssociatedRepository associatedRepository;

    @InjectMocks
    private FinderAssociatedService finderAssociatedService;

    @Test
    void shouldReturnPageAssociated(){
        finderAssociatedService.finderAllAssociated(PAGE_SIZE, PAGE_NUMBER, SORT_DIRECTION);

        verify(associatedRepository).findAll(argThat((Pageable expected) ->
            expected.getPageSize() == PAGE_SIZE &&
            expected.getPageNumber() == PAGE_NUMBER &&
            expected.getSort().isSorted()));
    }

    @Test
    void shouldReturnAssociatedWhenFindById(){
        Associated associated = mockAssociated();
        when(associatedRepository.findById(ASSEMBLY_ID)).thenReturn(Optional.of(associated));

        Associated associatedSaved = finderAssociatedService.findById(ASSEMBLY_ID);

        assertNotNull(associatedSaved);
    }

    @Test
    void shouldReturnBusinessExceptionWhenAssociatedNotFound(){
        when(associatedRepository.findById(ASSEMBLY_ID)).thenReturn(Optional.empty());

        assertThrows((BusinessException.class), () -> finderAssociatedService.findById(ASSEMBLY_ID));
    }

    @Test
    void shouldFindByCpf(){
        Associated associated = mockAssociated();
        finderAssociatedService.findByCpf(associated.getCpf());

        verify(associatedRepository).findByCpf(eq(associated.getCpf()));
    }

    @Test
    void shouldFindAssociatedByCpf(){
        Associated associated = mockAssociated();
        when(associatedRepository.findByCpf(associated.getCpf())).thenReturn(Optional.of(associated));
        finderAssociatedService.findAssociatedByCpf(associated.getCpf());

        verify(associatedRepository).findByCpf(eq(associated.getCpf()));
    }

    @Test
    void shouldBusinessExceptionWhenFindAssociatedNotFound(){
        Associated associated = mockAssociated();
        when(associatedRepository.findByCpf(associated.getCpf())).thenReturn(Optional.empty());

        Assertions.assertThrows(BusinessException.class, () -> finderAssociatedService.findAssociatedByCpf(associated.getCpf()));

        verify(associatedRepository).findByCpf(eq(associated.getCpf()));
    }

}