package esprit.services;

import esprit.entites.Offre;
import esprit.interfaces.IService;
import esprit.tools.MYConnection;

import java.sql.*;
import java.util.ArrayList;

public class OffreService implements IService<Offre> {
    Connection cnx = MYConnection.getInstance().getCnx();

    public void ajouterOffre(Offre offre) {
        try{
            String requete = "INSERT INTO offre (nom,description,condition_utilisation,date_debut,date_fin,type_id) VALUES (?,?,?,?,?,?)" ;
            PreparedStatement pst = new MYConnection().getCnx().prepareStatement(requete);
            pst.setString(1, offre.getNom());
            pst.setString(2, offre.getDescription());
            pst.setString(3, offre.getCondition_utilisation());
            pst.setDate(4, Date.valueOf(offre.getDate_debut()));
            pst.setDate(5, Date.valueOf(offre.getDate_fin()));
            pst.setInt(6, offre.getType_id());

            pst.executeUpdate();
            System.out.println("Offre ajoutée avec succès");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void insert(Offre o) {
        String requete2 = "INSERT INTO offre (nom,description,condition_utilisation,date_debut,date_fin,type_id) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement ost = new MYConnection().getCnx().prepareStatement(requete2);
            ost.setString(1, o.getNom());
            ost.setString(2, o.getDescription());
            ost.setString(3, o.getCondition_utilisation());
            ost.setDate(4, Date.valueOf(o.getDate_debut()));
            ost.setDate(5, Date.valueOf(o.getDate_fin()));
            ost.setInt(6, o.getType_id());

            ost.executeUpdate();
            System.out.println("Votre offre est ajoutée");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(Offre offre) {
        String requete = "UPDATE offre SET nom = ?, description = ?, condition_utilisation = ?, date_debut = ?, date_fin = ?, type_id = ? WHERE id = ?";
        try {
            PreparedStatement pst = new MYConnection().getCnx().prepareStatement(requete);
            pst.setString(1, offre.getNom());
            pst.setString(2, offre.getDescription());
            pst.setString(3, offre.getCondition_utilisation());
            pst.setDate(4, Date.valueOf(offre.getDate_debut()));
            pst.setDate(5, Date.valueOf(offre.getDate_fin()));
            pst.setInt(6, offre.getType_id());
            pst.setInt(7, offre.getId());

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("L'offre avec l'ID " + offre.getId() + " a été modifiée avec succès.");
            } else {
                System.out.println("Aucune modification n'a été effectuée pour l'offre avec l'ID " + offre.getId() + ".");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification de l'offre : " + e.getMessage());
        }

    }

    @Override
    public void delete(int id) {
        String requete = "DELETE FROM offre WHERE id = ?";
        try {
            PreparedStatement pst = new MYConnection().getCnx().prepareStatement(requete);
            pst.setInt(1, id);
            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("L'offre avec l'ID " + id + " a été supprimée avec succès.");
            } else {
                System.out.println("Aucune offre avec l'ID " + id + " n'a été trouvée.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'offre : " + e.getMessage());
        }

    }

    @Override
    public ArrayList<Offre> readAll() {
        ArrayList<Offre> offres = new ArrayList<>();
        try {
            String requete3 = "SELECT * FROM offre";
            Statement st = new MYConnection().getCnx().createStatement();

            ResultSet rs = st.executeQuery(requete3);
            while (rs.next()){
                Offre o = new Offre();
                o.setId(rs.getInt(1));
                o.setNom(rs.getString("nom"));
                o.setDescription(rs.getString("description"));
                o.setCondition_utilisation(rs.getString("condition_utilisation"));
                o.setDate_debut(rs.getDate("date_debut").toLocalDate());
                o.setDate_fin(rs.getDate("date_fin").toLocalDate());
                o.setType_id(rs.getInt("type_id"));

                offres.add(o);

            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return offres;
    }

    @Override
    public Offre readById(int id) {
        Offre offre = null;
        String requete = "SELECT * FROM offre WHERE id = ?";
        try {
            PreparedStatement pst = new MYConnection().getCnx().prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                offre = new Offre();
                offre.setId(rs.getInt("id"));
                offre.setNom(rs.getString("nom"));
                offre.setDescription(rs.getString("description"));
                offre.setCondition_utilisation(rs.getString("condition_utilisation"));
                offre.setDate_debut(rs.getDate("date_debut").toLocalDate());
                offre.setDate_fin(rs.getDate("date_fin").toLocalDate());
                offre.setType_id(rs.getInt("type_id"));
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'offre : " + e.getMessage());
        }
        return offre;
    }

    public String getTypeNameById(int typeId) {
        String typeName = null;
        String query = "SELECT nom FROM type WHERE id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, typeId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                typeName = rs.getString("nom");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return typeName;
    }
}

