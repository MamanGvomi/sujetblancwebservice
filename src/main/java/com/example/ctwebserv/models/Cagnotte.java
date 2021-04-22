package com.example.ctwebserv.models;

import java.time.LocalDate;

public class Cagnotte {
    private String nomCagnotte;
    private int montantDemandee;
    private LocalDate dateFin;
    private boolean isActive;


    public Cagnotte(String nomCagnotte, int montantDemandee, LocalDate dateFin, boolean isActive) {
        this.nomCagnotte = nomCagnotte;
        this.montantDemandee = montantDemandee;
        this.dateFin = dateFin;
        this.isActive = isActive;
    }

    public String getNomCagnotte() {
        return nomCagnotte;
    }

    public void setNomCagnotte(String nomCagnotte) {
        this.nomCagnotte = nomCagnotte;
    }

    public int getMontantDemandee() {
        return montantDemandee;
    }

    public void setMontantDemandee(int montantDemandee) {
        this.montantDemandee = montantDemandee;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
