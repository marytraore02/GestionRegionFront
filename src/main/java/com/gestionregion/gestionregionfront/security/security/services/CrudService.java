package com.gestionregion.gestionregionfront.security.security.services;

import com.gestionregion.gestionregionfront.security.models.ERole;
import com.gestionregion.gestionregionfront.security.models.Role;
import com.gestionregion.gestionregionfront.security.models.User;

import java.util.List;

public interface CrudService {

    User creerUser(User user);

    void addRoleToUser(String username, ERole roleName);

    String Supprimer(Long id_users);  // LA METHODE PERMETTANT DE SUPPRIMER UN COLLABORATEUR

    String Modifier(Long id, User user);   // LA METHODE PERMETTANT DE MODIFIER UN COLLABORATEUR

    List<User> Afficher();       // LA METHODE PERMETTANT D'AFFICHER UN COLLABORATEUR


    User getEmailUser(String email);

    User Login(String email, String password);

    List<User> recupererParRole(Role role);

    User RecupererParId(Long id);

}