package Gui;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class OverviewController {

    public Scene printOverviews() {

        BorderPane layout = new BorderPane();
        Scene OverviewControllerButtons = new Scene(layout);
        VBox list = new VBox();
        list.getChildren().add(new Label("Welkom!"));
        list.getChildren().add(new Label("kies een van de volgende acties:"));

        layout.setCenter(list);
        return OverviewControllerButtons;
    }

}
