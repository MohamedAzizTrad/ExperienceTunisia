package esprit.controllers.Type;

import esprit.controllers.Offre.UpdateOffreController;
import esprit.entites.TypeOffre;
import esprit.services.TypeService;
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

public class IndexTypeController {
    private final TypeService typeService = new TypeService();

    @FXML
    private TableView<TypeOffre> tableView;

    @FXML
    private TableColumn<TypeOffre, String> nomColumn;
    @FXML
    private TableColumn<TypeOffre, String> descriptionColumn;
    @FXML
    private TableColumn<TypeOffre, String> dateCreationColumn;
    @FXML
    private TableColumn<TypeOffre, Void> updateColumn;
    @FXML
    private TableColumn<TypeOffre, Void> deleteColumn;



    @FXML
    private void initialize() {
        // Initialize TableView columns
        nomColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getNom()));
        descriptionColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getDescription()));
        dateCreationColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().getDate_creation().toString()));

        // Set cell factory for update and delete buttons
        updateColumn.setCellFactory(param -> new TableCell<>() {
            final Button cellButton = new Button("Modifier");

            {
                cellButton.setOnAction(event -> {
                    TypeOffre type = getTableView().getItems().get(getIndex());
                    handleUpdateType(type);
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
                    TypeOffre type = getTableView().getItems().get(getIndex());
                    handleDeleteType(type);
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

        // Fetching all types from the service
        refresh();
    }

    public void refresh() {
        // Retrieve all types from the service
        ArrayList<TypeOffre> types = typeService.readAll();

        // Clear current items in TableView
        tableView.getItems().clear();

        // Add types to the TableView
        tableView.getItems().addAll(types);

    }

    @FXML
    private void handleAjouterType() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/type/add-type.fxml"));
            Parent root = loader.load();
            AddTypeController controller = loader.getController();
            controller.setIndexTypeController(this); // Pass a reference to IndexTypeController
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
        // Implement update type functionality here
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/type/update-type.fxml"));
            Parent root = loader.load();
            UpdateTypeController controller = loader.getController();
            controller.setIndexTypeController(this); // Pass a reference to IndexOffreController
            controller.setType(type); // Pass the offer to update to the update controller
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
        // Implement delete type functionality here
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Suppression de le type");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer cette type ?");

        // Display the confirmation dialog
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {

                typeService.delete(type.getId());

                refresh();
            }
        });
    }
}
