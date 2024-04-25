package esprit.controllers.Offre;

import esprit.controllers.Offre.IndexOffreController;
import esprit.entites.Offre;
import esprit.entites.TypeOffre;
import esprit.services.OffreService;
import esprit.services.TypeService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import java.util.ArrayList;

public class UpdateOffreController {

    @FXML
    private TextField nomTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField conditionTextField;

    @FXML
    private DatePicker dateDebutPicker;

    @FXML
    private DatePicker dateFinPicker;

    @FXML
    private ComboBox<TypeOffre> typeComboBox;

    private OffreService offreService = new OffreService();
    private TypeService typeService = new TypeService();
    private IndexOffreController indexOffreController;
    private Offre offre;

    public void setIndexOffreController(IndexOffreController indexOffreController) {
        this.indexOffreController = indexOffreController;
    }

    public void setOffre(Offre offre) {
        this.offre = offre;
        nomTextField.setText(offre.getNom());
        descriptionTextField.setText(offre.getDescription());
        conditionTextField.setText(offre.getCondition_utilisation());
        dateDebutPicker.setValue(offre.getDate_debut());
        dateFinPicker.setValue(offre.getDate_fin());
        typeComboBox.setValue(typeService.readById(offre.getType_id()));
    }

    @FXML
    private void initialize() {
        ArrayList<TypeOffre> typesOffre = typeService.readAll();
        typeComboBox.setItems(FXCollections.observableArrayList(typesOffre));

        // Personnaliser l'affichage des éléments de la liste déroulante
        typeComboBox.setCellFactory(param -> new ListCell<TypeOffre>() {
            @Override
            protected void updateItem(TypeOffre item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getNom() == null) {
                    setText(null);
                } else {
                    setText(item.getNom());
                }
            }
        });

        // Définir le rendu des éléments sélectionnés dans la liste déroulante
        typeComboBox.setButtonCell(new ListCell<TypeOffre>() {
            @Override
            protected void updateItem(TypeOffre item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getNom() == null) {
                    setText(null);
                } else {
                    setText(item.getNom());
                }
            }
        });
    }

    @FXML
    private void handleUpdate() {
        if (isInputValid()) {
            offre.setNom(nomTextField.getText());
            offre.setDescription(descriptionTextField.getText());
            offre.setCondition_utilisation(conditionTextField.getText());
            offre.setDate_debut(dateDebutPicker.getValue());
            offre.setDate_fin(dateFinPicker.getValue());
            int typeId = typeComboBox.getValue().getId();
            offre.setType_id(typeId);
            offreService.update(offre);
            if (indexOffreController != null) {
                indexOffreController.refresh();
            }
            nomTextField.getScene().getWindow().hide();
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (nomTextField.getText() == null || nomTextField.getText().isEmpty()) {
            errorMessage += "Nom de l'offre est vide!\n";
        }

        if (descriptionTextField.getText() == null || descriptionTextField.getText().isEmpty()) {
            errorMessage += "Description de l'offre est vide!\n";
        }

        if (conditionTextField.getText() == null || conditionTextField.getText().isEmpty()) {
            errorMessage += "Condition d'utilisation est vide!\n";
        }

        if (dateDebutPicker.getValue() == null || dateFinPicker.getValue() == null) {
            errorMessage += "Veuillez sélectionner une date de début et une date de fin!\n";
        } else if (dateDebutPicker.getValue().isAfter(dateFinPicker.getValue())) {
            errorMessage += "La date de début ne peut pas être après la date de fin!\n";
        }

        if (typeComboBox.getValue() == null) {
            errorMessage += "Veuillez sélectionner un type d'offre!\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            showAlertDialog(Alert.AlertType.ERROR, "Erreur", "Champs invalides", errorMessage);
            return false;
        }
    }

    private void showAlertDialog(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
