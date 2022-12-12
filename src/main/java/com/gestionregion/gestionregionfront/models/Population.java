package com.gestionregion.gestionregionfront.models;

import com.gestionregion.gestionregionfront.models.Region;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="population")
public class Population {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long idPopulation;
    private String NbPopulation;
    @Column(length = 4)
    private Long AnneePopulation;

    @ManyToOne
    private Region region;
}
