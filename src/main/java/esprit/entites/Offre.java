package esprit.entites;

import java.time.LocalDate;

public class Offre {
    private int id;
    private String nom;
    private String description;
    private String condition_utilisation;
    private LocalDate date_debut;
    private LocalDate date_fin;

    private int type_id;

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public Offre(int id, String nom, String descritpion, String condition_utilisation, LocalDate date_debut, LocalDate date_fin) {
        this.id = id;
        this.nom = nom;
        this.description = descritpion;
        this.condition_utilisation = condition_utilisation;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }


    public Offre(String nom, String description, String condition_utilisation, LocalDate date_debut, LocalDate date_fin, int type_id) {
        this.nom = nom;
        this.description = description;
        this.condition_utilisation = condition_utilisation;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.type_id = type_id;
    }

    public Offre(int id, String nom, String description, String condition_utilisation, LocalDate date_debut, LocalDate date_fin, int type_id) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.condition_utilisation = condition_utilisation;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.type_id = type_id;
    }

    public Offre(){
    }

    public Offre(String nom, String descritpion, String condition_utilisation, LocalDate date_debut, LocalDate date_fin) {
        this.nom = nom;
        this.description = descritpion;
        this.condition_utilisation = condition_utilisation;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }





    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String descritpion) {
        this.description = descritpion;
    }

    public String getCondition_utilisation() {
        return condition_utilisation;
    }

    public void setCondition_utilisation(String condition_utilisation) {
        this.condition_utilisation = condition_utilisation;
    }

    public LocalDate getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(LocalDate date_debut) {
        this.date_debut = date_debut;
    }

    public LocalDate getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(LocalDate date_fin) {
        this.date_fin = date_fin;
    }

    @Override
    public String toString() {
        return "Offre{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", descritpion='" + description + '\'' +
                ", condition='" + condition_utilisation + '\'' +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                '}';
    }
}