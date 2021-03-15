package com.gabriellopesjds.cooperativism.vote.domain.model;

import com.gabriellopesjds.cooperativism.associated.domain.model.Associated;
import com.gabriellopesjds.cooperativism.vote.domain.model.enumerated.VoteTypeEnum;
import com.gabriellopesjds.cooperativism.votingsession.domain.model.VotingSession;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vote")
@Getter
@Setter
@GenericGenerator(name = "UUID_generator", strategy = "uuid2")
public class Vote {

    @Id
    @GeneratedValue(generator = "UUID_generator")
    @Column(name = "id")
    private UUID id;

    @Column(name = "vote_type")
    @Enumerated(EnumType.STRING)
    private VoteTypeEnum voteType;

    @ManyToOne
    @JoinColumn(name = "id_voting_session", referencedColumnName = "id")
    private VotingSession votingSession;

    @ManyToOne
    @JoinColumn(name = "id_associated", referencedColumnName = "id")
    private Associated associated;

}
