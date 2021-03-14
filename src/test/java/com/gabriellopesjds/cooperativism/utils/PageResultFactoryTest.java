package com.gabriellopesjds.cooperativism.utils;

import com.gabriellopesjds.api.model.PageResultDTO;
import com.gabriellopesjds.api.model.PageableDTO;
import com.gabriellopesjds.api.model.SortDTO;
import com.gabriellopesjds.cooperativism.stave.domain.model.Stave;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = PageResultFactory.class)
class PageResultFactoryTest {

    @Autowired
    private PageResultFactory pageResultFactory;

    @Test
    void should() {
        final List<Stave> staveList = Arrays.asList(UtilsTest.mockStaveDefault());

        final int pageNumber = 0;
        final int pageSize = 1;
        final PageRequest pageable = PageRequest.of(pageNumber, pageSize);
        final Page<Stave> page = new PageImpl<>(staveList, pageable, 1);
        final PageResultDTO pageResult = pageResultFactory.createPageFrom(page);

        assertNotNull(pageResult);
        assertEquals(pageResult.getEmpty(), page.isEmpty());
        assertEquals(pageResult.getFirst(), page.isFirst());
        assertEquals(pageResult.getHasNext(), page.hasNext());
        assertEquals(pageResult.getHasPrevious(), page.hasPrevious());
        assertEquals(pageResult.getLast(), page.isLast());
        assertEquals(pageResult.getNumberOfElements(), page.getNumberOfElements());
        assertEquals(pageResult.getPageNumber(), page.getNumber());
        assertEquals(pageResult.getPageSize(), page.getSize());
        assertEquals(pageResult.getTotalElements(), page.getTotalElements());
        assertEquals(pageResult.getTotalPages(), page.getTotalPages());

        final SortDTO sortDTO = pageResult.getSort();
        final Sort sortOrigin = page.getSort();

        assertEquals(sortDTO.getEmpty(), sortOrigin.isEmpty());
        assertEquals(sortDTO.getSorted(), sortOrigin.isSorted());

        final PageableDTO pageableResult = pageResult.getPageable();
        final Pageable pageableOrigin = page.getPageable();

        assertNotNull(pageableResult);
        assertEquals((long) pageableResult.getOffset(), pageableOrigin.getOffset());
        assertEquals(pageableResult.getPaged(), pageableOrigin.isPaged());
        assertEquals(pageableResult.getPageNumber(), pageableOrigin.getPageNumber());
        assertEquals(pageableResult.getPageSize(), pageableOrigin.getPageSize());
        assertEquals(pageableResult.getUnpaged(), pageableOrigin.isUnpaged());
    }

}