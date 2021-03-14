package com.gabriellopesjds.cooperativism.stave.repository;

import com.gabriellopesjds.cooperativism.stave.domain.model.Stave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StaveRepository extends JpaRepository<Stave, UUID> {

}
