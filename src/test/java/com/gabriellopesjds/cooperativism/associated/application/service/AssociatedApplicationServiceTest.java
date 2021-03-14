package com.gabriellopesjds.cooperativism.associated.application.service;

import com.gabriellopesjds.api.model.AssociatedPageableResponseDTO;
import com.gabriellopesjds.api.model.AssociatedRequestDTO;
import com.gabriellopesjds.api.model.AssociatedResponseDTO;
import com.gabriellopesjds.api.model.AssociatedUpdateRequestDTO;
import com.gabriellopesjds.cooperativism.associated.domain.model.Associated;
import com.gabriellopesjds.cooperativism.associated.domain.service.DeleteAssociatedService;
import com.gabriellopesjds.cooperativism.associated.domain.service.FinderAssociatedService;
import com.gabriellopesjds.cooperativism.associated.domain.service.RegisterAssociatedService;
import com.gabriellopesjds.cooperativism.associated.domain.service.UpdateAssociatedService;
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
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockAssociated;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockAssociatedPageableResponseDTO;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockAssociatedRequestDTO;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockAssociatedResponseDTO;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockAssociatedUpdateRequestDTO;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssociatedApplicationServiceTest {

    @Mock
    private RegisterAssociatedService registerAssociatedService;

    @Mock
    private FinderAssociatedService finderAssociatedService;

    @Mock
    private UpdateAssociatedService updateAssociatedService;

    @Mock
    private DeleteAssociatedService deleteAssociatedService;

    @Mock
    private AssociatedFactory associatedFactory;

    @InjectMocks
    private AssociatedApplicationService associatedApplicationService;

    @Test
    void shouldRegisterAssociated() {
        AssociatedRequestDTO associatedRequestDTO = mockAssociatedRequestDTO();
        Associated associated = mockAssociated();
        AssociatedResponseDTO associatedResponseDTO = mockAssociatedResponseDTO();

        when(associatedFactory.fromValue(associatedRequestDTO)).thenReturn(associated);
        when(registerAssociatedService.registerAssociated(associated)).thenReturn(associated);
        when(associatedFactory.buildResponse(associated)).thenReturn(associatedResponseDTO);

        associatedApplicationService.registerAssociated(associatedRequestDTO);

        verify(associatedFactory).fromValue(eq(associatedRequestDTO));
        verify(registerAssociatedService).registerAssociated(eq(associated));
        verify(associatedFactory).buildResponse(eq(associated));
    }

    @Test
    void shouldFinderAllAssociated() {
        Page page = Mockito.mock(Page.class);
        AssociatedPageableResponseDTO associatedPageableResponseDTO = mockAssociatedPageableResponseDTO();

        when(finderAssociatedService.finderAllAssociated(PAGE_SIZE, PAGE_NUMBER, SORT_DIRECTION)).thenReturn(page);
        when(associatedFactory.buildResponse(page)).thenReturn(associatedPageableResponseDTO);

        associatedApplicationService.finderAllAssociated(PAGE_SIZE, PAGE_NUMBER, SORT_DIRECTION);

        verify(finderAssociatedService).finderAllAssociated(PAGE_SIZE, PAGE_NUMBER, SORT_DIRECTION);
        verify(associatedFactory).buildResponse(eq(page));
    }

    @Test
    void shouldFindAssociated() {
        Associated associated = mockAssociated();
        AssociatedResponseDTO associatedResponseDTO = mockAssociatedResponseDTO();
        when(finderAssociatedService.findById(associated.getId())).thenReturn(associated);
        when(associatedFactory.buildResponse(associated)).thenReturn(associatedResponseDTO);

        associatedApplicationService.findAssociated(associated.getId());

        verify(finderAssociatedService).findById(eq(associated.getId()));
        verify(associatedFactory).buildResponse(eq(associated));
    }

    @Test
    void shouldFindAssociatedByCpf() {
        Associated associated = mockAssociated();
        AssociatedResponseDTO associatedResponseDTO = mockAssociatedResponseDTO();
        when(finderAssociatedService.findAssociatedByCpf(associated.getCpf())).thenReturn(associated);
        when(associatedFactory.buildResponse(associated)).thenReturn(associatedResponseDTO);

        associatedApplicationService.findAssociatedByCpf(associated.getCpf());

        verify(finderAssociatedService).findAssociatedByCpf(eq(associated.getCpf()));
        verify(associatedFactory).buildResponse(eq(associated));
    }

    @Test
    void shouldDeleteAssociated() {
        UUID uuid = UUID.randomUUID();
        associatedApplicationService.deleteAssociated(uuid);

        verify(deleteAssociatedService).delete(eq(uuid));
    }

    @Test
    void shouldDeleteAssociatedByCpf() {
        Associated associated = mockAssociated();
        associatedApplicationService.deleteAssociatedByCpf(associated.getCpf());

        verify(deleteAssociatedService).delete(eq(associated.getCpf()));
    }

    @Test
    void shouldUpdateAssociated() {
        AssociatedUpdateRequestDTO associatedUpdateRequestDTO = mockAssociatedUpdateRequestDTO();
        Associated associated = mockAssociated();
        AssociatedResponseDTO associatedResponseDTO = mockAssociatedResponseDTO();
        when(associatedFactory.fromValue(associatedUpdateRequestDTO)).thenReturn(associated);
        when(updateAssociatedService.updateAssociated(associated.getId(), associated)).thenReturn(associated);
        when(associatedFactory.buildResponse(associated)).thenReturn(associatedResponseDTO);

        associatedApplicationService.updateAssociated(associated.getId(), associatedUpdateRequestDTO);

        verify(associatedFactory).fromValue(eq(associatedUpdateRequestDTO));
        verify(updateAssociatedService).updateAssociated(eq(associated.getId()), eq(associated));
        verify(associatedFactory).buildResponse(eq(associated));
    }

}