package pt.ipleiria.estg.ei.dae.projeto.projetopmei.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
    //-------------- Atributos ----------------
    @Id
    private String id;
    private String Name;
    private String Password;
    private String email;

    //-------------- Construtores ----------------
    public User() {

    }
    public User(String id, String name, String password, String email) {
        this.id = id;
        this.Name = name;
        this.Password = password;
        this.email = email;
    }

    //-------------- Metodos ----------------

    //-------------- Getters / Setters ----------------
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        this.Name = name;
    }

    public String getPassword() {
        return Password;
    }
    public void setPassword(String password) {
        this.Password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
