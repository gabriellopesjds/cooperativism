package com.gabriellopesjds.cooperativism.assembly.domain.service;

import com.gabriellopesjds.cooperativism.assembly.domain.model.Assembly;
import com.gabriellopesjds.cooperativism.assembly.repository.AssemblyRepository;
import com.gabriellopesjds.cooperativism.exception.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.data.domain.Sort.Direction.fromOptionalString;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FinderAssemblyService {

    private final AssemblyRepository assemblyRepository;

    public Page<Assembly> finderAllAssembly(Integer pageSize, Integer pageNumber, String sortDirection) {
        Sort.Direction direction = fromOptionalString(sortDirection).orElse(Sort.Direction.ASC);
        Sort sort = Sort.by(direction, "description");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return assemblyRepository.findAll(pageable);
    }

    public Assembly findById(UUID id) {
        return assemblyRepository.findById(id)
            .orElseThrow(() -> new BusinessException("COOPERATIVISM-001", id.toString()));
    }

    public Optional<Assembly> findByDescriptionAndDate(Assembly assembly){
        return assemblyRepository.findByDescriptionAndDate(assembly.getDescription(), assembly.getDate());
    }

    public Optional<Assembly> findByDescriptionAndDateIsNot(String description, LocalDateTime date, UUID id){
        return assemblyRepository.findByDescriptionAndDateAndIdIsNot(description, date, id);
    }

}
