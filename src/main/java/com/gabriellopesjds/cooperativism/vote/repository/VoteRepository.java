package com.gabriellopesjds.cooperativism.vote.repository;

import com.gabriellopesjds.cooperativism.vote.domain.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VoteRepository extends JpaRepository<Vote, UUID> {

    Optional<Vote> findByVotingSessionIdAndAssociatedId(UUID idVotingSession, UUID idAssociated);
}
