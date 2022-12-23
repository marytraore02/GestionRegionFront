package com.gestionregion.gestionregionfront.repository;

import com.gestionregion.gestionregionfront.models.Pays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaysRepository extends JpaRepository<Pays, Long> {
    Pays findByNomPays(String nomPays);
}
