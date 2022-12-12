package com.gestionregion.gestionregionfront.services;

import com.gestionregion.gestionregionfront.models.Population;

import java.util.List;

public interface PopulationService {
    Population creer (Population population);
    List<Population> lire();
    Population modifier(Long id_population, Population population);
    String supprimer(long id_population);

}
