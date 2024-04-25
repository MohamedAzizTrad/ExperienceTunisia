package esprit.controllers.Type;

import esprit.entites.TypeOffre;
import esprit.services.TypeService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class AddTypeController {

    @FXML
    private TextField nomTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private Button ajouterButton;

    private TypeService typeService = new TypeService();

    private IndexTypeController indexTypeController;

    public void setIndexTypeController(IndexTypeController indexTypeController) {
        this.indexTypeController = indexTypeController;
    }

    @FXML
    private void handleAjouter() {
        // Vérifier si les champs sont valides
        if (isInputValid()) {
            // Créer un nouvel objet TypeOffre
            TypeOffre nouveauType = new TypeOffre(
                    nomTextField.getText(),
                    descriptionTextField.getText(),
                    LocalDate.now() // Date de création actuelle
            );
            // Ajouter le nouveau type à la base de données
            typeService.insert(nouveauType);

            // Afficher une boîte de dialogue de confirmation
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText("Type ajouté");
            alert.setContentText("Le type a été ajouté avec succès !");
            alert.showAndWait();

            // Rafraîchir la liste des types dans l'interface IndexType
            if (indexTypeController != null) {
                indexTypeController.refresh();
            }

            // Fermer la fenêtre d'ajout
            nomTextField.getScene().getWindow().hide();
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";

        // Vérifier si le nom de type est vide
        if (nomTextField.getText() == null || nomTextField.getText().isEmpty()) {
            errorMessage += "Nom de Type invalide !\n";
        } else {
            // Vérifier si le nom de type est déjà utilisé dans la base de données
            if (typeService.isNomTypeExist(nomTextField.getText())) {
                errorMessage += "Nom de Type déjà existe !\n";
            }
        }

        // Vérifier si la description est vide
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
