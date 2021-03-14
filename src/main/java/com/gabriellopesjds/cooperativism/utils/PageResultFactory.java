package com.gabriellopesjds.cooperativism.utils;

import com.gabriellopesjds.api.model.PageResultDTO;
import com.gabriellopesjds.api.model.PageableDTO;
import com.gabriellopesjds.api.model.SortDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PageResultFactory {

    public PageResultDTO createPageFrom(Page<?> page) {
        final PageResultDTO pageResult = new PageResultDTO();

        pageResult.setPageNumber(page.getNumber());
        pageResult.setPageSize(page.getSize());
        pageResult.setTotalPages(page.getTotalPages());
        pageResult.setEmpty(page.isEmpty());
        pageResult.setFirst(page.isFirst());
        pageResult.setHasNext(page.hasNext());
        pageResult.setHasPrevious(page.hasPrevious());
        pageResult.setNumberOfElements(page.getNumberOfElements());
        pageResult.setLast(page.isLast());
        pageResult.setTotalElements(page.getTotalElements());

        createPageableFrom(page, pageResult);

        Optional.of(page.getSort()).ifPresent(sort -> {
            final SortDTO sortDTO = new SortDTO();

            sortDTO.setEmpty(sort.isEmpty());
            sortDTO.setSorted(sort.isSorted());
            sortDTO.setUnsorted(sort.isUnsorted());

            pageResult.setSort(sortDTO);
        });

        return pageResult;
    }

    private static void createPageableFrom(Page<?> page, PageResultDTO pageResult) {
        Optional.of(page.getPageable()).ifPresent(pageable -> {
            final PageableDTO pageableDTO = new PageableDTO();

            pageableDTO.setPaged(pageable.isPaged());
            pageableDTO.setOffset(pageable.getOffset());
            pageableDTO.setPageNumber(pageable.getPageNumber());
            pageableDTO.setPageSize(pageable.getPageSize());
            pageableDTO.setPaged(pageable.isPaged());
            pageableDTO.setUnpaged(pageable.isUnpaged());

            pageResult.setPageable(pageableDTO);
        });
    }
}
