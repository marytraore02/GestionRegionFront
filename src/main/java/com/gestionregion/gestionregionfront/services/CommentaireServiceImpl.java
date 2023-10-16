package com.gestionregion.gestionregionfront.services;

import com.gestionregion.gestionregionfront.models.Commentaire;
import com.gestionregion.gestionregionfront.models.Region;
import com.gestionregion.gestionregionfront.repository.CommentaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentaireServiceImpl implements CommentaireService{

    @Autowired
    CommentaireRepository repos;


    @Override
    public Commentaire createCommentaire(Commentaire commentaire) {
        return repos.save(commentaire);
    }

    @Override
    public Object modificationCommentaire(Commentaire commentaire, Long id) {
        return repos.findById(id)
                .map(p -> {
                    p.setDescription(commentaire.getDescription());
                    p.setRegion(commentaire.getRegion());
                    p.setUser(commentaire.getUser());
                    p.setRegion(commentaire.getRegion());
                    return repos.save(p);
                }).orElseThrow(() -> new RuntimeException("Commentaire non trouvé !"));
    }

    @Override
    public void deleteCommentaire(Commentaire commentaire) {
        repos.delete(commentaire);
    }

    @Override
    public List<Commentaire> getAllCommentaire() {
        return repos.findAll();
    }

    @Override
    public List<Commentaire> retrouverParRegion(Region region) {
        return repos.findByRegion(region);
    }


//    @Override
//    public List<Commentaire> retrouverParCommentaire(Commentaire commentaire) {
//        return repos.findByCommentaire(commentaire);
//    }

    @Override
    public Commentaire retrouverParId(Long id) {
        try {
            return repos.findById(id).get();

        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }
}
