package esprit.controllers.Offre;

import esprit.entites.Offre;
import esprit.services.OffreService;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

public class IndexOffreController {
    private final OffreService offreService = new OffreService();

    @FXML
    private TableView<Offre> tableView;
    @FXML
    private TableColumn<Offre, String> nomColumn;
    @FXML
    private TableColumn<Offre, String> descriptionColumn;
    @FXML
    private TableColumn<Offre, String> conditionColumn;
    @FXML
    private TableColumn<Offre, String> dateDebutColumn;
    @FXML
    private TableColumn<Offre, String> dateFinColumn;
    @FXML
    private TableColumn<Offre, String> typeColumn;
    @FXML
    private TableColumn<Offre, Void> updateColumn;
    @FXML
    private TableColumn<Offre, Void> deleteColumn;

    @FXML
    private void initialize() {
        // Initialize TableView columns
        nomColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getNom()));

        descriptionColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getDescription()));
        conditionColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getCondition_utilisation()));
        dateDebutColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getDate_debut().toString()));
        dateFinColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getDate_fin().toString()));
        typeColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> String.valueOf(cellData.getValue().getType_id())));

        // Set cell factory for update and delete buttons
        updateColumn.setCellFactory(param -> new TableCell<>() {
            final Button cellButton = new Button("Modifier");

            {
                cellButton.setOnAction(event -> {
                    Offre offre = getTableView().getItems().get(getIndex());
                    handleUpdateOffre(offre);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setGraphic(cellButton);
                    setText(null);
                }
            }
        });

        deleteColumn.setCellFactory(param -> new TableCell<>() {
            final Button cellButton = new Button("Supprimer");

            {
                cellButton.setOnAction(event -> {
                    Offre offre = getTableView().getItems().get(getIndex());
                    handleDeleteOffre(offre);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setGraphic(cellButton);
                    setText(null);
                }
            }
        });

        // Fetching all offers from the service
        refresh();
    }

    public void refresh() {
        // Retrieve all offers from the service
        ArrayList<Offre> offres = offreService.readAll();

        // Clear current items in TableView
        tableView.getItems().clear();

        // Add offers to the TableView
        tableView.getItems().addAll(offres);
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
