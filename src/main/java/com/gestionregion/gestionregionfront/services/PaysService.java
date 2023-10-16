package com.gestionregion.gestionregionfront.services;

import com.gestionregion.gestionregionfront.models.Pays;
import com.gestionregion.gestionregionfront.security.models.User;

import java.util.List;

public interface PaysService {

    Pays creer (Pays pays);
    Pays getNomPays( String nom);
    boolean existsByNomPays(String name);
    //Pays getPays(Pays pays);
    List<Pays> lire();
    Pays modifier(Long id_pays, Pays pays);
    String supprimer(long id_pays);
    Pays RecupererParId(Long id);

}
