package Gui;

import javax.swing.Action;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class IndexController {

    public IndexController() {
    }

    public Scene indexScreen() {

        BorderPane layout = new BorderPane();
        VBox list = new VBox();
        list.getChildren().add(new Label("Welkom!"));
        list.getChildren().add(new Label("kies een van de volgende acties:"));
        HBox buttons = new HBox();
        Button Courses = new Button("cursussen zien en wijzigen");
        Courses.setOnAction((Action) -> {
        });
        Button cursists = new Button("cursisten zien en wijzigen");
        Button registrations = new Button("inschrijvingen zien en wijzigen");
        Button certificates = new Button("diploma's zien en wijzigen");
        buttons.getChildren().addAll(Courses, cursists, registrations, certificates);
        list.getChildren().add(buttons);
        layout.setCenter(list);
        Scene index = new Scene(layout);
        return index;

    }
}
