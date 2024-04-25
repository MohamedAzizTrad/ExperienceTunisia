package esprit.entites;

import java.time.LocalDate;

public class TypeOffre {
    private int id;
    private String nom,description;
    private LocalDate date_creation;

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

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(LocalDate date_creation) {
        this.date_creation = date_creation;
    }

    public TypeOffre() {
    }

    public TypeOffre(String nom, String description, LocalDate date_creation) {
        this.nom = nom;
        this.description = description;
        this.date_creation = date_creation;
    }

    public TypeOffre(int id, String nom, String description, LocalDate date_creation) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.date_creation = date_creation;
    }

    @Override
    public String toString() {
        return "TypeOffre{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", date_creation=" + date_creation +
                '}';
    }
}
