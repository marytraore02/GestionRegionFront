package com.gestionregion.gestionregionfront.repository;

import com.gestionregion.gestionregionfront.models.Commentaire;
import com.gestionregion.gestionregionfront.models.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {
    List<Commentaire> findByCommentaire(Commentaire commentaire);

    List<Commentaire> findByRegion(Region region);
}
