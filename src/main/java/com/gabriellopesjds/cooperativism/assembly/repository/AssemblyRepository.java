package com.gabriellopesjds.cooperativism.assembly.repository;

import com.gabriellopesjds.cooperativism.assembly.domain.model.Assembly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AssemblyRepository extends JpaRepository<Assembly, UUID> {

}
