package com.gabriellopesjds.cooperativism.stave.domain.service;

import com.gabriellopesjds.cooperativism.exception.BusinessException;
import com.gabriellopesjds.cooperativism.stave.domain.model.Stave;
import com.gabriellopesjds.cooperativism.stave.repository.StaveRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

import static com.gabriellopesjds.cooperativism.utils.UtilsTest.PAGE_NUMBER;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.PAGE_SIZE;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.SORT_BY;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.SORT_DIRECTION;
import static com.gabriellopesjds.cooperativism.utils.UtilsTest.mockStaveDefault;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FinderStaveServiceTest {

    @Mock
    private StaveRepository staveRepository;

    @InjectMocks
    private FinderStaveService finderStaveService;

    @Test
    void shouldReturnPageStave(){
        finderStaveService.finderAllStave(PAGE_SIZE, PAGE_NUMBER, SORT_DIRECTION, SORT_BY);

        verify(staveRepository).findAll(argThat((Pageable expected) ->
            expected.getPageSize() == PAGE_SIZE &&
            expected.getPageNumber() == PAGE_NUMBER &&
            expected.getSort().isSorted()));
    }

    @Test
    void shouldReturnStaveWhenFindById(){
        UUID uuid = UUID.randomUUID();
        Stave stave = mockStaveDefault();
        when(staveRepository.findById(uuid)).thenReturn(Optional.of(stave));

        finderStaveService.findById(uuid);

        verify(staveRepository).findById(uuid);
    }

    @Test
    void  shouldReturnBusinessExceptionWhenFindByIdAndStaveNotExists(){
        UUID uuid = UUID.randomUUID();
        when(staveRepository.findById(uuid)).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> finderStaveService.findById(uuid));
    }

}