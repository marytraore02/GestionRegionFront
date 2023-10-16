package com.gestionregion.gestionregionfront.services;

import com.gestionregion.gestionregionfront.models.Pays;
import com.gestionregion.gestionregionfront.repository.PaysRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PaysServiceImpl implements PaysService {

    @Autowired
    PaysRepository paysRepository;

    @Override
    public Pays creer(Pays pays) {
        return paysRepository.save(pays);
    }

    @Override
    public Pays getNomPays(String nom) {
        return paysRepository.findByNomPays(nom);
    }

    @Override
    public boolean existsByNomPays(String name) {
        return paysRepository.existsByNomPays(name);
    }


    @Override
    public List<Pays> lire() {
        return paysRepository.findAll();
    }

    @Override
    public Pays modifier(Long id_pays, Pays pays) {
        return paysRepository.findById(id_pays)
                .map(p->{
                    p.setNomPays(pays.getNomPays());
                    p.setDescriptionPays(pays.getDescriptionPays());
                    p.setSuperficiePays(pays.getSuperficiePays());
                    return paysRepository.save(p);
                }).orElseThrow(()-> new RuntimeException("Pays non trouvé !"));
    }

    @Override
    public String supprimer(long id_pays) {
        paysRepository.deleteById(id_pays);
        return "Pays supprimé";
    }

    @Override
    public Pays RecupererParId(Long id) {
        return paysRepository.findById(id).get();
    }

}
