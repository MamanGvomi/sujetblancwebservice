package com.example.ctwebserv.models;

import java.time.LocalDateTime;

public class Donation {
    private String nom;
    private String prenom;
    private LocalDateTime heure;
    private int montant;

    public Donation(String nom, String prenom, LocalDateTime heure, int montant) {
        this.nom = nom;
        this.prenom = prenom;
        this.heure = heure;
        this.montant = montant;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDateTime getHeure() {
        return heure;
    }

    public void setHeure(LocalDateTime heure) {
        this.heure = heure;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }
}
