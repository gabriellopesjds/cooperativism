package com.gabriellopesjds.cooperativism.associated.domain.service;

import com.gabriellopesjds.cooperativism.associated.domain.model.Associated;
import com.gabriellopesjds.cooperativism.associated.repository.AssociatedRepository;
import com.gabriellopesjds.cooperativism.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockAssociated;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterAssociatedServiceTest {

    @Mock
    private AssociatedRepository associatedRepository;

    @Mock
    private FinderAssociatedService finderAssociatedService;

    @InjectMocks
    private RegisterAssociatedService registerAssociatedService;

    @Test
    void shouldRegisterAssociatedWithSuccess(){
        Associated associated = mockAssociated();

        when(finderAssociatedService.findByCpf(associated.getCpf())).thenReturn(Optional.empty());

        registerAssociatedService.registerAssociated(associated);

        verify(finderAssociatedService).findByCpf(eq(associated.getCpf()));
        verify(associatedRepository).save(eq(associated));
    }

    @Test
    void shouldReturnBusinessExceptionWhenAssociatedIsDuplicate(){
        Associated associated = mockAssociated();

        when(finderAssociatedService.findByCpf(associated.getCpf())).thenReturn(Optional.of(associated));

        assertThrows((BusinessException.class), () -> registerAssociatedService.registerAssociated(associated));

        verify(associatedRepository, never()).save(eq(associated));
    }

}