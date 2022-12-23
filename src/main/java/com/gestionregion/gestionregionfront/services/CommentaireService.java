package com.gestionregion.gestionregionfront.services;

import com.gestionregion.gestionregionfront.models.Commentaire;
import com.gestionregion.gestionregionfront.models.Region;

import java.util.List;

public interface CommentaireService {
    // Creation d'un Commentaire
    Commentaire createCommentaire(Commentaire commentaire);

    // Modification d'un commentaire
    Object modificationCommentaire(Commentaire commentaire, Long id);

    // Supression d'un commentaire
    void deleteCommentaire(Commentaire commentaire);

    // L'ensemble des etats
    List<Commentaire> getAllCommentaire();

    // retrouver etat par region
    List<Commentaire> retrouverParRegion(Region region);

    // retrouver etat par Commentaire
    List<Commentaire> retrouverParCommentaire(Commentaire commentaire);

    // retrouver etat par id
    Commentaire retrouverParId(Long id);
}
