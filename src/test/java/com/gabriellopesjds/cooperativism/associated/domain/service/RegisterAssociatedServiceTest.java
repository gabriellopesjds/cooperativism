package com.gabriellopesjds.cooperativism.associated.domain.service;

import com.gabriellopesjds.cooperativism.associated.domain.model.Associated;
import com.gabriellopesjds.cooperativism.associated.repository.AssociatedRepository;
import com.gabriellopesjds.cooperativism.exception.BusinessException;
import com.gabriellopesjds.cooperativism.stave.domain.service.RegisterStaveService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockAssociated;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockStaveDefault;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
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