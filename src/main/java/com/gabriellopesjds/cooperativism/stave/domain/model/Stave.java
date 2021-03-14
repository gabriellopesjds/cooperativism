package com.gabriellopesjds.cooperativism.stave.domain.model;

import com.gabriellopesjds.cooperativism.assembly.domain.model.Assembly;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "stave")
@Getter
@Setter
@GenericGenerator(name = "UUID_generator", strategy = "uuid2")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Stave {

    @Id
    @GeneratedValue(generator = "UUID_generator")
    @Column(name = "id")
    private UUID id;


    @EqualsAndHashCode.Include
    @Column(name = "theme")
    private String theme;

    @EqualsAndHashCode.Include
    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_assembly", columnDefinition = "BINARY(16)")
    private Assembly assembly;
}
