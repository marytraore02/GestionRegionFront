package com.gestionregion.gestionregionfront.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gestionregion.gestionregionfront.security.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "region")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_region")
    private Long idRegion;
    private String imageRegion;
    private String codeRegion;
    private String nomRegion;
    private String descriptionRegion;
    private String domaineActiviteRegion;
    private String superficie;
    private String langueMajoritaire;

    @ManyToOne
    private Pays pays;

    @JsonIgnore
    @OneToMany(mappedBy = "region")
    List<Commentaire> commentaireList = new ArrayList<>();

    public Region(String imageRegion, String codeRegion, String nomRegion, String descriptionRegion, String domaineActiviteRegion, String superficie, String langueMajoritaire, Pays pays) {
        this.imageRegion = imageRegion;
        this.codeRegion = codeRegion;
        this.nomRegion = nomRegion;
        this.descriptionRegion = descriptionRegion;
        this.domaineActiviteRegion = domaineActiviteRegion;
        this.superficie = superficie;
        this.langueMajoritaire = langueMajoritaire;
        this.pays = pays;
    }


}
