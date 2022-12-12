package com.gestionregion.gestionregionfront.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "region")
@NoArgsConstructor
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_region")
    private Long idRegion;
    private String codeRegion;
    private String nomRegion;
    private String domaineActiviteRegion;
    private String superficie;
    private String langueMajoritaire;

    @ManyToOne
    private Pays pays;

    public Region(String code_Region, String nom_Region, String domaineActiviteRegion, String superficie, String langueMajoritaire, Pays pays) {
        this.codeRegion = code_Region;
        this.nomRegion = nom_Region;
        this.domaineActiviteRegion = domaineActiviteRegion;
        this.superficie = superficie;
        this.langueMajoritaire = langueMajoritaire;
        this.pays = pays;
    }
}
