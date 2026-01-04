package com.example.locationcaftan.model;

public class Caftan {

    private int id;
    private String nom;
    private String prix;
    private String taille;
    private String couleur;
    private String image_url;
    private String statut;

    public Caftan(int id, String nom, String prix, String taille, String couleur, String image_url, String statut) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.taille = taille;
        this.couleur = couleur;
        this.image_url = image_url;
        this.statut = statut;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrix() {
        return prix;
    }

    public String getTaille() {
        return taille;
    }

    public String getCouleur() {
        return couleur;
    }

    public String getImageUrl() {
        return image_url.trim();
    }

    public String getStatut() {
        return statut;
    }
}
