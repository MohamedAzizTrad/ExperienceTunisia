package esprit.controllers.Type;

import esprit.entites.TypeOffre;
import esprit.services.TypeService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class UpdateTypeController {

    @FXML
    private TextField nomTextField;

    @FXML
    private TextField descriptionTextField;

    private TypeService typeService = new TypeService();
    private IndexTypeController indexTypeController;
    private TypeOffre type;

    public void setIndexTypeController(IndexTypeController indexTypeController) {
        this.indexTypeController = indexTypeController;
    }

    public void setType(TypeOffre type) {
        this.type = type;
        // Afficher les informations du type sélectionné dans les champs de texte
        nomTextField.setText(type.getNom());
        descriptionTextField.setText(type.getDescription());
    }

    @FXML
    private void handleUpdate() {
        // Vérifier si les champs sont valides
        if (isInputValid()) {
            // Mettre à jour les informations du type avec les valeurs des champs de texte
            type.setNom(nomTextField.getText());
            type.setDescription(descriptionTextField.getText());

            // Mettre à jour le type dans la base de données
            typeService.update(type);

            // Afficher une boîte de dialogue de confirmation
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText("Type mis à jour");
            alert.setContentText("Le type a été mis à jour avec succès !");
            alert.showAndWait();

            // Rafraîchir la liste des types dans l'interface IndexType
            if (indexTypeController != null) {
                indexTypeController.refresh();
            }

            // Fermer la fenêtre de mise à jour
            nomTextField.getScene().getWindow().hide();
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (nomTextField.getText() == null || nomTextField.getText().isEmpty()) {
            errorMessage += "Nom de Type invalide !\n";
        }

        if (descriptionTextField.getText() == null || descriptionTextField.getText().isEmpty()) {
            errorMessage += "Description invalide !\n";
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            // Afficher une boîte de dialogue d'erreur avec les messages d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Champs invalides");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}
