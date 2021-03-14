package com.gabriellopesjds.cooperativism.assembly.repository;

import com.gabriellopesjds.cooperativism.assembly.domain.model.Assembly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssemblyRepository extends JpaRepository<Assembly, UUID> {

    Optional<Assembly> findByDescriptionAndDate(String description, LocalDateTime date);

    Optional<Assembly> findByDescriptionAndDateAndIdIsNot(String description, LocalDateTime date, UUID id);

}
