package com.gestionregion.gestionregionfront.services;

import com.gestionregion.gestionregionfront.models.Population;
import com.gestionregion.gestionregionfront.repository.PopulationRepository;
import com.gestionregion.gestionregionfront.services.PopulationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@AllArgsConstructor
public class PopulationServiceImpl implements PopulationService {

    @Autowired
    PopulationRepository populationRepository;

    @Override
    public Population creer(Population population) {
        return populationRepository.save(population);
    }

    @Override
    public List<Population> lire() {
        return populationRepository.findAll();
    }

    @Override
    public Population modifier(Long id_population, Population population) {
        return populationRepository.findById(id_population)
                .map(p->{
                    p.setNbPopulation(population.getNbPopulation());
                    p.setAnneePopulation(population.getAnneePopulation());
                    return populationRepository.save(p);
                }).orElseThrow(()-> new RuntimeException("Population non trouvé !"));
    }

    @Override
    public String supprimer(long id_population) {
        populationRepository.deleteById(id_population);
        return "Population supprimé";
    }

    @Override
    public Population GetOne(long id_population) {
        return populationRepository.getReferenceById(id_population);
    }

}
