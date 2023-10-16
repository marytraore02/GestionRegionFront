package com.gestionregion.gestionregionfront.services;

import com.gestionregion.gestionregionfront.dto.RegionDto;
import com.gestionregion.gestionregionfront.models.Region;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public interface RegionService {
    Region creer(Region region);
    List<Region> lire();
    Region modifier(Long id, Region region);
    String supprimer(long id_Region);
    Region RecupererParId(Long id);
    Optional<Region> getOne(Long id);
    Region getByNameRegion(String name);
    boolean existsByNomRegion(String name);
    Iterable<Object[]> getRegionsSP();

}
