package com.gestionregion.gestionregionfront.services;

import com.gestionregion.gestionregionfront.dto.RegionDto;
import com.gestionregion.gestionregionfront.models.Region;
import com.gestionregion.gestionregionfront.repository.RegionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RegionServiceImpl implements RegionService {

    @Autowired
    RegionRepository regionRepository;

    @Override
    public Region creer(Region region) {
        return regionRepository.save(region);
        //return "add-student";
    }

    @Override
    public List<Region> lire() {
        return regionRepository.findAll();
    }

    @Override
    public Region modifier(Long id, Region region) {
            return regionRepository.findById(id)
                    .map(p->{
                        p.setCodeRegion(region.getCodeRegion());
                        p.setNomRegion(region.getNomRegion());
                        p.setDomaineActiviteRegion(region.getDomaineActiviteRegion());
                        p.setDescriptionRegion(region.getDescriptionRegion());
                        p.setSuperficie(region.getSuperficie());
                        p.setLangueMajoritaire(region.getLangueMajoritaire());
                        return regionRepository.save(p);
                    }).orElseThrow(()-> new RuntimeException("Region non trouvé !"));
        }

    @Override
    public String supprimer(long id_Region) {
        regionRepository.deleteById(id_Region);
        return "Region supprimé";
    }

    @Override
    public Region RecupererParId(Long id) {
        return regionRepository.getReferenceById(id);
    }

    @Override
    public Optional<Region> getOne(Long id) {
        return regionRepository.findById(id);
    }

    @Override
    public Region getByNameRegion(String name) {
        return regionRepository.findByNomRegion(name);
    }

    @Override
    public boolean existsByNomRegion(String name) {
        return regionRepository.existsByNomRegion(name);
    }

    /*public boolean existsByName(String nom){
         return regionRepository.existsByNomRegion(nom);
     }*/
    public boolean existsById(Long id){
        return regionRepository.existsById(id);
    }


    public Iterable<Object[]> getRegionsSP() {
        return regionRepository.getRegionsSP();
    }

}
