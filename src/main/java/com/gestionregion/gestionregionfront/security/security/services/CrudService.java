package com.gestionregion.gestionregionfront.security.security.services;

import com.gestionregion.gestionregionfront.security.models.User;

import java.util.List;

public interface CrudService {

    String Supprimer(Long id_users);  // LA METHODE PERMETTANT DE SUPPRIMER UN COLLABORATEUR

    String Modifier(Long id, User user);   // LA METHODE PERMETTANT DE MODIFIER UN COLLABORATEUR

    List<User> Afficher();       // LA METHODE PERMETTANT D'AFFICHER UN COLLABORATEUR

}