package com.gabriellopesjds.cooperativism.associated.repository;

import com.gabriellopesjds.cooperativism.associated.domain.model.Associated;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssociatedRepository extends JpaRepository<Associated, UUID> {

    Optional<Associated> findByCpf(String cpf);

}
