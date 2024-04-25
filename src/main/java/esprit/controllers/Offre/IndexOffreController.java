package esprit.controllers.Offre;

import esprit.entites.Offre;
import esprit.services.OffreService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class    IndexOffreController {
    private final OffreService offreService = new OffreService();

    @FXML
    private TilePane tilePane;

    @FXML
    private void initialize() {
        // Fetching all offers from the service
        refresh();
    }

    public void refresh() {
        // Retrieve all offers from the service
        ArrayList<Offre> offres = offreService.readAll();

        // Clear current elements in TilePane
        tilePane.getChildren().clear();

        // Display offers in the view
        for (Offre offre : offres) {
            AnchorPane offrePane = new AnchorPane(); // Container for each offer
            VBox offreBox = new VBox(5); // Vertical box to hold offer details
            Label nomLabel = new Label("Nom: " + offre.getNom());
            Label descriptionLabel = new Label("Description: " + offre.getDescription());
            Label conditionLabel = new Label("Condition: " + offre.getCondition_utilisation());
            Label dateDebutLabel = new Label("Date début: " + offre.getDate_debut().toString());
            Label dateFinLabel = new Label("Date fin: " + offre.getDate_fin().toString());
            Label typeLabel = new Label("Type: " + offreService.getTypeNameById(offre.getType_id()));
            Button updateButton = new Button("Update");
            Button deleteButton = new Button("Delete");

            // Add event handler for details button
            updateButton.setOnAction(e -> handleUpdateOffre(offre));

            // Add event handler for delete button
            deleteButton.setOnAction(e -> handleDeleteOffre(offre));

            // Adding elements to the offer box
            offreBox.getChildren().addAll(nomLabel, descriptionLabel, conditionLabel, dateDebutLabel, dateFinLabel, typeLabel, updateButton, deleteButton);
            offrePane.getChildren().add(offreBox); // Add offer box to offer pane

            // Set position of the offer pane in the TilePane
            AnchorPane.setTopAnchor(offreBox, 0.0);
            AnchorPane.setLeftAnchor(offreBox, 0.0);
            AnchorPane.setRightAnchor(offreBox, 0.0);

            // Add the offer pane to the TilePane
            tilePane.getChildren().add(offrePane);
        }
    }

    @FXML
    private void handleUpdateOffre(Offre offre) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/offre/update-offre.fxml"));
            Parent root = loader.load();
            UpdateOffreController controller = loader.getController();
            controller.setIndexOffreController(this); // Pass a reference to IndexOffreController
            controller.setOffre(offre); // Pass the offer to update to the update controller
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteOffre(Offre offre) {
        // Ask for confirmation before deleting
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Suppression de l'offre");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cette offre ?");

        // Display the confirmation dialog
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Delete the offer from the database
                offreService.delete(offre.getId());
                // Refresh the list of offers in the IndexOffre interface
                refresh();
            }
        });
    }

    @FXML
    private void handleAddOffre() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/offre/add-offre.fxml"));
            Parent root = loader.load();
            AddOffreController controller = loader.getController();
            controller.setIndexOffreController(this); // Pass a reference to IndexOffreController
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
