package esprit.controllers.Type;

import esprit.entites.TypeOffre;
import esprit.services.TypeService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class IndexTypeController {
    private final TypeService typeService = new TypeService();

    @FXML
    private TilePane tilePane;

    @FXML
    private void initialize() {
        // Fetching all types from the service
        refresh();
    }

    public void refresh() {
        // Fetch types from the service
        ArrayList<TypeOffre> types = typeService.readAll();

        // Clear current elements in TilePane
        tilePane.getChildren().clear();

        // Display types in the view
        for (TypeOffre type : types) {
            AnchorPane typePane = new AnchorPane(); // Container for each type
            VBox typeBox = new VBox(5); // Vertical box to hold type details
            Label nomLabel = new Label("Nom: " + type.getNom());
            Label descriptionLabel = new Label("Description: " + type.getDescription());
            Label dateCreationLabel = new Label("Date création: " + type.getDate_creation().toString());
            Button updateButton = new Button("Update");
            updateButton.setOnAction(e -> {
                handleUpdateType(type); // Appeler la méthode de gestion de mise à jour
            });
            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(e -> {
                handleDeleteType(type); // Appeler la méthode de gestion de suppression
            });

            // Adding elements to the type box
            typeBox.getChildren().addAll(nomLabel, descriptionLabel, dateCreationLabel, updateButton, deleteButton);
            typePane.getChildren().add(typeBox); // Add type box to type pane

            // Set position of the type box in the AnchorPane
            AnchorPane.setTopAnchor(typeBox, 0.0);
            AnchorPane.setLeftAnchor(typeBox, 0.0);
            AnchorPane.setRightAnchor(typeBox, 0.0);

            // Add the type pane to the TilePane
            tilePane.getChildren().add(typePane);
        }
    }

    @FXML
    private void handleAjouterType() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/type/add-type.fxml"));
            Parent root = loader.load();
            AddTypeController controller = loader.getController();
            controller.setIndexTypeController(this); // Passe une référence à IndexTypeController
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdateType(TypeOffre type) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/type/update-type.fxml"));
            Parent root = loader.load();
            UpdateTypeController controller = loader.getController();
            controller.setIndexTypeController(this); // Passe une référence à IndexTypeController
            controller.setType(type); // Passe le type à mettre à jour au contrôleur de mise à jour
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteType(TypeOffre type) {
        // Demander confirmation avant de supprimer
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Suppression de type");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer ce type ?");

        // Afficher la boîte de dialogue de confirmation
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Supprimer le type de la base de données
                typeService.delete(type.getId());
                // Rafraîchir la liste des types dans l'interface IndexType
                refresh();
            }
        });
    }
}
