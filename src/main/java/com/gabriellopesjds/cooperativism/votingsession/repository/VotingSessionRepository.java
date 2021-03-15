package com.gabriellopesjds.cooperativism.votingsession.repository;

import com.gabriellopesjds.cooperativism.votingsession.domain.model.VotingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VotingSessionRepository extends JpaRepository<VotingSession, UUID> {

    Optional<VotingSession> findByEndDateAfterAndStaveId(LocalDateTime date, UUID id);

}
