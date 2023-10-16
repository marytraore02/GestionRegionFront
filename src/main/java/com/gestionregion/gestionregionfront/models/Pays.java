package com.gestionregion.gestionregionfront.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="pays")
@Getter
@Setter
@NoArgsConstructor
public class Pays {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_pays")
    private Long idPays;
    @Column(length = 50)
    private String nomPays;
    private String imagePays;
    private String descriptionPays;
    private String superficiePays;

    @JsonIgnore
    @OneToMany(mappedBy = "pays")
    List<Region> regions = new ArrayList<>();

    public Pays(String nomPays, String imagePays, String descriptionPays, String superficiePays) {
        this.nomPays = nomPays;
        this.imagePays = imagePays;
        this.descriptionPays = descriptionPays;
        this.superficiePays = superficiePays;
    }
}
