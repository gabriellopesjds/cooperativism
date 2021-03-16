package com.gabriellopesjds.cooperativism.stave.domain.service;

import com.gabriellopesjds.cooperativism.exception.BusinessException;
import com.gabriellopesjds.cooperativism.stave.domain.model.Stave;
import com.gabriellopesjds.cooperativism.stave.domain.model.enumerated.StaveSortByEnum;
import com.gabriellopesjds.cooperativism.stave.repository.StaveRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.fromOptionalString;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class FinderStaveService {

    private final StaveRepository staveRepository;

    public Page<Stave> finderAllStave(Integer pageSize, Integer pageNumber, String sortDirection, String sortBy) {
        log.info("Finder all stave");
        Sort.Direction direction = fromOptionalString(sortDirection).orElse(Sort.Direction.ASC);
        Sort sort = Sort.by(direction, StaveSortByEnum.fromValueOrDefault(sortBy).getValue());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return staveRepository.findAll(pageable);
    }

    public Stave findById(UUID id){
        log.info("Finder stave with id: {}.", id);
        return staveRepository.findById(id)
            .orElseThrow(() -> new BusinessException("COOPERATIVISM-002", id.toString()));
    }

}
