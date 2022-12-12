package com.gestionregion.gestionregionfront.repository;

import com.gestionregion.gestionregionfront.models.Population;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PopulationRepository extends JpaRepository<Population, Long> {
}
