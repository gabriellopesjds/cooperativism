package com.gabriellopesjds.cooperativism.associated.domain.service;

import com.gabriellopesjds.cooperativism.associated.domain.model.Associated;
import com.gabriellopesjds.cooperativism.associated.repository.AssociatedRepository;
import com.gabriellopesjds.cooperativism.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockAssociated;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteAssociatedServiceTest {

    @Mock
    private AssociatedRepository associatedRepository;

    @Mock
    private FinderAssociatedService finderAssociatedService;

    @InjectMocks
    private DeleteAssociatedService deleteAssociatedService;

    @Test
    void shouldDeleteAssociatedWithSuccess(){
        Associated associated = mockAssociated();

        when(finderAssociatedService.findById(associated.getId())).thenReturn(associated);

        deleteAssociatedService.delete(associated.getId());

        verify(associatedRepository).delete(eq(associated));
    }

    @Test
    void shouldReturnBusinessExceptionWhenAssociatedNotFound(){
        Associated associated = mockAssociated();

        doThrow(BusinessException.class).when(finderAssociatedService).findById(eq(associated.getId()));

        assertThrows((BusinessException.class), () -> deleteAssociatedService.delete(associated.getId()));

        verify(associatedRepository, never()).delete(eq(associated));
    }

    @Test
    void shouldDeleteAssociatedByCpfWithSuccess(){
        Associated associated = mockAssociated();

        when(finderAssociatedService.findAssociatedByCpf(associated.getCpf())).thenReturn(associated);

        deleteAssociatedService.delete(associated.getCpf());

        verify(associatedRepository).delete(eq(associated));
    }

    @Test
    void shouldReturnBusinessExceptionWhenCpfAssociatedNotFound(){
        Associated associated = mockAssociated();

        doThrow(BusinessException.class).when(finderAssociatedService).findAssociatedByCpf(eq(associated.getCpf()));

        assertThrows((BusinessException.class), () -> deleteAssociatedService.delete(associated.getCpf()));

        verify(associatedRepository, never()).delete(eq(associated));
    }

}