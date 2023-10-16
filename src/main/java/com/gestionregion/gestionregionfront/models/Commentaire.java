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

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Commentaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String description;

    @ManyToOne
    User user;

    @ManyToOne
    Region region;

//    @ManyToOne(cascade = CascadeType.ALL)
//    private Commentaire commentaire;

//    @JsonIgnore
//    @OneToMany(mappedBy = "commentaire")
//    List<Commentaire> sousCommentaire = new ArrayList<>();
}
