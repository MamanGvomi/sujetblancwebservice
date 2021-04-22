package com.example.ctwebserv.models;

public class Association {
    private String nomAssociation;
    private String nomResponsable;
    private String ville;

    public Association(String nomAssociation, String nomResponsable, String ville) {
        this.nomAssociation = nomAssociation;
        this.nomResponsable = nomResponsable;
        this.ville = ville;
    }

    public String getNomAssociation() {
        return nomAssociation;
    }

    public void setNomAssociation(String nomAssociation) {
        this.nomAssociation = nomAssociation;
    }

    public String getNomResponsable() {
        return nomResponsable;
    }

    public void setNomResponsable(String nomResponsable) {
        this.nomResponsable = nomResponsable;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
}
