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
class UpdateAssociatedServiceTest {

    @Mock
    private AssociatedRepository associatedRepository;

    @Mock
    private FinderAssociatedService finderAssociatedService;

    @InjectMocks
    private UpdateAssociatedService updateAssociatedService;


    @Test
    void shouldRegisterAssociatedWithSuccess(){
        Associated associated = mockAssociated();

        when(finderAssociatedService.findById(associated.getId())).thenReturn(associated);

        updateAssociatedService.updateAssociated(associated.getId(), associated);

        verify(finderAssociatedService).findById(eq(associated.getId()));
        verify(associatedRepository).save(eq(associated));
    }

}