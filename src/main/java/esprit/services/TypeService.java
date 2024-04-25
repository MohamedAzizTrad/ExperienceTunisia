package esprit.services;


import esprit.entites.TypeOffre;
import esprit.interfaces.IService;
import esprit.tools.MYConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TypeService implements IService<TypeOffre> {
    @Override
    public void insert(TypeOffre typeOffre) {
        String requete = "INSERT INTO type (nom, description, date_creation) VALUES (?, ?, ?)";
        try {
            PreparedStatement pst = MYConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, typeOffre.getNom());
            pst.setString(2, typeOffre.getDescription());
            pst.setDate(3, java.sql.Date.valueOf(typeOffre.getDate_creation()));

            pst.executeUpdate();
            System.out.println("Type d'offre ajouté avec succès");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du type d'offre : " + e.getMessage());
        }
    }

    @Override
    public void update(TypeOffre typeOffre) {
        String requete = "UPDATE type SET nom = ?, description = ?, date_creation = ? WHERE id = ?";
        try {
            PreparedStatement pst = MYConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, typeOffre.getNom());
            pst.setString(2, typeOffre.getDescription());
            pst.setDate(3, java.sql.Date.valueOf(typeOffre.getDate_creation()));
            pst.setInt(4, typeOffre.getId());

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Type d'offre avec l'ID " + typeOffre.getId() + " a été mis à jour avec succès.");
            } else {
                System.out.println("Aucune mise à jour effectuée pour le type d'offre avec l'ID " + typeOffre.getId() + ".");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du type d'offre : " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        // Mettre à jour les offres avec type_id égal à l'id du type que vous souhaitez supprimer
        String updateQuery = "UPDATE offre SET type_id = NULL WHERE type_id = ?";
        try {
            PreparedStatement updateStatement = MYConnection.getInstance().getCnx().prepareStatement(updateQuery);
            updateStatement.setInt(1, id);
            updateStatement.executeUpdate();

            // Supprimer le type après avoir mis à jour les offres
            String deleteQuery = "DELETE FROM type WHERE id = ?";
            PreparedStatement deleteStatement = MYConnection.getInstance().getCnx().prepareStatement(deleteQuery);
            deleteStatement.setInt(1, id);

            int rowsDeleted = deleteStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Type d'offre avec l'ID " + id + " a été supprimé avec succès.");
            } else {
                System.out.println("Aucun type d'offre avec l'ID " + id + " n'a été trouvé.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du type d'offre : " + e.getMessage());
        }
    }


    @Override
    public ArrayList<TypeOffre> readAll() {
        ArrayList<TypeOffre> typesOffre = new ArrayList<>();
        String requete = "SELECT * FROM type";
        try {
            Statement st = MYConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);

            while (rs.next()) {
                TypeOffre typeOffre = new TypeOffre();
                typeOffre.setId(rs.getInt("id"));
                typeOffre.setNom(rs.getString("nom"));
                typeOffre.setDescription(rs.getString("description"));
                typeOffre.setDate_creation(rs.getDate("date_creation").toLocalDate());

                typesOffre.add(typeOffre);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des types d'offre : " + e.getMessage());
        }
        return typesOffre;
    }

    @Override
    public TypeOffre readById(int id) {
        TypeOffre typeOffre = null;
        String requete = "SELECT * FROM type WHERE id = ?";
        try {
            PreparedStatement pst = MYConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                typeOffre = new TypeOffre();
                typeOffre.setId(rs.getInt("id"));
                typeOffre.setNom(rs.getString("nom"));
                typeOffre.setDescription(rs.getString("description"));
                typeOffre.setDate_creation(rs.getDate("date_creation").toLocalDate());
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du type d'offre : " + e.getMessage());
        }
        return typeOffre;
    }

    public boolean isNomTypeExist(String nom) {
        String requete = "SELECT COUNT(*) FROM type WHERE nom = ?";
        try {
            PreparedStatement pst = MYConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, nom);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification de l'existence du nom de type : " + e.getMessage());
        }
        return false;
    }
    public TypeOffre getTypeIdByName(String typeName) {
        TypeOffre typeOffre = null;
        String query = "SELECT * FROM type WHERE nom = ?";
        try {
            PreparedStatement pst = MYConnection.getInstance().getCnx().prepareStatement(query);
            pst.setString(1, typeName);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                typeOffre = new TypeOffre();
                typeOffre.setId(rs.getInt("id"));
                typeOffre.setNom(rs.getString("nom"));
                typeOffre.setDescription(rs.getString("description"));
                typeOffre.setDate_creation(rs.getDate("date_creation").toLocalDate());
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du type d'offre par nom : " + e.getMessage());
        }
        return typeOffre;
    }

}
