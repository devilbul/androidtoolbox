package fr.isen.galiay.androidtoolbox.bdd;

import android.arch.persistence.room.*;

@Entity(tableName = "user")
public class User {
    @PrimaryKey
    private int id_user;

    @ColumnInfo(name = "identifiant")
    private String identifiant;

    @ColumnInfo(name = "mot_de_passe")
    private String motDePasse;

    @ColumnInfo(name = "date_de_naissance")
    private String dateNaissance;

    @ColumnInfo(name = "prenom")
    private String prenom;

    @ColumnInfo(name = "nom")
    private String nom;

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
