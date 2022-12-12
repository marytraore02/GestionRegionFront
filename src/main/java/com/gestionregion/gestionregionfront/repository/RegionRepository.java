package com.gestionregion.gestionregionfront.repository;

import com.gestionregion.gestionregionfront.models.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    Optional<Region> findByNomRegion(String name);
    /*boolean existsByName(String name);*/

    @Query(value = "SELECT id_region,code_region,nom_regiojn,domaine_activite_region,superficie,langue_majoritaire FROM region", nativeQuery = true)
    Iterable<Object[]> getRegionsSP ();


}
