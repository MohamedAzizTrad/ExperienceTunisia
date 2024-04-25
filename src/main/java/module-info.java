module ConnexionJDBC {

    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    //pour acceder à MainApplication
    exports esprit.tests;
    opens esprit.tests to javafx.fxml;

    //pour acceder au controllers d'Offre
    exports esprit.controllers.Offre;
    opens esprit.controllers.Offre to javafx.fxml;

    //pour acceder au controllers de type
    exports esprit.controllers.Type;
    opens esprit.controllers.Type to javafx.fxml;

}