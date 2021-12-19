package com.haythem.miniprojet;

public class MainModel {
     private String Image,Modele,Marque,Specs;


    MainModel()
    {

    }

    public MainModel(String Image, String modele, String marque, String specs) {
        this.Image = Image;
        this.Modele = modele;
        this.Marque = marque;
        this.Specs = specs;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String Image) {
        this.Image = Image;
    }

    public String getModele() {
        return Modele;
    }

    public void setModele(String modele) {
        Modele = modele;
    }

    public String getMarque() {
        return Marque;
    }

    public void setMarque(String marque) {
        Marque = marque;
    }

    public String getSpecs() {
        return Specs;
    }

    public void setSpecs(String specs) {
        Specs = specs;
    }
}
