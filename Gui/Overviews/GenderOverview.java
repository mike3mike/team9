package Gui.Overviews;

import Gui.ApplicationController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GenderOverview {
    private static final int BUTTON_WIDTH = 120;
    private static final int BUTTON_HEIGHT = 50;

    public Scene getScene() {
        // Create a label with the message
        Label messageLabel = new Label("Hij werkt!");

        // Create a button to go back
        Button back = new Button("Ga terug");
        back.setOnAction((Action) -> {
            Stage stage = new Stage();
            ApplicationController controller = new ApplicationController();
            try {
                controller.start(stage);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Node node = (Node) Action.getSource();
            Stage thisStage = (Stage) node.getScene().getWindow();
            thisStage.close();
        });

        // Create a VBox to hold the label and button
        VBox vbox = new VBox(10, messageLabel, back);
        vbox.setAlignment(Pos.CENTER);

        // Create a new Scene with the VBox as the root node
        return new Scene(vbox, 400, 300);
    }

}
