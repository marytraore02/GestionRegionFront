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
    Region modifier(Long id, RegionDto region);

    String supprimer(long id_Region);
    Optional<Region> getOne(Long id);
    Optional<Region> getByNameRegion(String name);

    Iterable<Object[]> getRegionsSP();

}
