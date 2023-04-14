package Gui.Overviews;

import Gui.ApplicationController;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WebcastOverview {

    public Scene getScene() {

        // Create a label with the message "x"
        Label messageLabel = new Label("Deze snap ik niet haha");

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

        // Create a VBox to hold the HBox and button
        VBox vBox = new VBox(10, messageLabel, back);
        vBox.setAlignment(Pos.CENTER);

        // Create a new Scene with the VBox as the root node
        return new Scene(vBox, 500, 300);
    }

}
