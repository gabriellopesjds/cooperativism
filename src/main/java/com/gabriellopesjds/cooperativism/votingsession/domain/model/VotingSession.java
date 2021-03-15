package com.gabriellopesjds.cooperativism.votingsession.domain.model;

import com.gabriellopesjds.cooperativism.stave.domain.model.Stave;
import com.gabriellopesjds.cooperativism.vote.domain.model.Vote;
import com.gabriellopesjds.cooperativism.votingsession.domain.model.enumerated.VotingSessionStateEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "voting_session")
@Getter
@Setter
@GenericGenerator(name = "UUID_generator", strategy = "uuid2")
public class VotingSession {

    @Id
    @GeneratedValue(generator = "UUID_generator")
    @Column(name = "id")
    private UUID id;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "id_stave", referencedColumnName = "id")
    private Stave stave;

    @OneToMany(mappedBy = "votingSession")
    private List<Vote> voteList;

    @Transient
    private Long totalVotes;

    @Transient
    private Long totalPositiveVotes;

    @Transient
    private Long totalNegativeVotes;

    @Transient
    private VotingSessionStateEnum stateEnum;

}
