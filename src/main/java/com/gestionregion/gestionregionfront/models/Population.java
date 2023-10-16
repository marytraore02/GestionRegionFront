package com.gestionregion.gestionregionfront.models;

import com.gestionregion.gestionregionfront.models.Region;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="population")
public class Population {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_population")
    private Long idPopulation;
    private String nbPopulation;
    private String anneePopulation;

    @ManyToOne
    private Region region;
}
