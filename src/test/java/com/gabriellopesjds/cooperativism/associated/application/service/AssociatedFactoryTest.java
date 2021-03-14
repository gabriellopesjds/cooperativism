package com.gabriellopesjds.cooperativism.associated.application.service;

import com.gabriellopesjds.api.model.AssociatedPageableResponseDTO;
import com.gabriellopesjds.api.model.AssociatedRequestDTO;
import com.gabriellopesjds.api.model.AssociatedResponseDTO;
import com.gabriellopesjds.api.model.AssociatedUpdateRequestDTO;
import com.gabriellopesjds.api.model.PageResultDTO;
import com.gabriellopesjds.cooperativism.associated.domain.model.Associated;
import com.gabriellopesjds.cooperativism.utils.PageResultFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;

import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockAssociated;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockAssociatedRequestDTO;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockAssociatedUpdateRequestDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssociatedFactoryTest {

    @Mock
    private PageResultFactory pageResultFactory;

    @InjectMocks
    private AssociatedFactory associatedFactory;

    @Test
    void shouldBuildAssociatedFromValueAssociatedRequestDTO() {
        AssociatedRequestDTO associatedRequestDTO = mockAssociatedRequestDTO();
        Associated associated = associatedFactory.fromValue(associatedRequestDTO);

        assertEquals(associatedRequestDTO.getName(), associated.getName());
        assertEquals(associatedRequestDTO.getCpf(), associated.getCpf());
    }

    @Test
    void shouldBuildAssociatedFromValueAssociatedUpdateRequestDTO() {
        AssociatedUpdateRequestDTO associatedUpdateRequestDTO = mockAssociatedUpdateRequestDTO();

        Associated associated = associatedFactory.fromValue(associatedUpdateRequestDTO);

        assertEquals(associatedUpdateRequestDTO.getName(), associated.getName());
    }

    @Test
    void shouldBuildAssociatedResponseDTO() {
        Associated associated = mockAssociated();
        AssociatedResponseDTO associatedResponseDTO = associatedFactory.buildResponse(associated);

        assertEquals(associated.getId(), associatedResponseDTO.getId());
        assertEquals(associated.getName(), associatedResponseDTO.getName());
        assertEquals(associated.getCpf(), associatedResponseDTO.getCpf());
    }

    @Test
    void shouldBuildAssociatedPageableResponseDTO() {
        Associated associated = mockAssociated();
        final int pageNumber = 0;
        final int pageSize = 1;
        final PageRequest pageable = PageRequest.of(pageNumber, pageSize);
        final Page<Associated> page = new PageImpl<>(Collections.singletonList(associated), pageable, 1);

        when(pageResultFactory.createPageFrom(page)).thenReturn(new PageResultDTO());
        final AssociatedPageableResponseDTO associatedPageableResponseDTO = associatedFactory.buildResponse(page);

        assertEquals(1, associatedPageableResponseDTO.getAssociateds().size());
    }

}