package com.gestionregion.gestionregionfront.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name="pays")
@Getter
@Setter
@NoArgsConstructor
public class Pays {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long idPays;
    @Column(length = 50)
    private String nomPays;

}
