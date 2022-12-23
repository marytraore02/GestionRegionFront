package com.gestionregion.gestionregionfront.security.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gestionregion.gestionregionfront.models.Commentaire;
import com.gestionregion.gestionregionfront.models.Region;
import lombok.*;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users", 
    uniqueConstraints = { 
      @UniqueConstraint(columnNames = "username"),
      @UniqueConstraint(columnNames = "email") 
    })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Size(max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;


  @JsonIgnore
  @OneToMany(mappedBy = "user")
  List<Commentaire> listCommentaire = new ArrayList<>();

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(  name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
 Set<Role> roles = new HashSet<>();


  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }
}
