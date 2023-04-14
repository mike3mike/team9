package Gui.Overviews;

import Gui.ApplicationController;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GraduatedOverview {

    private ComboBox<String> courseoptions = new ComboBox<>();
    private int numberGraduated;

    public Scene getScene() {

        // Create label with results
        Label result = new Label("");
        result.setVisible(false);

        // Create a label with the message "Kies een cursus" and a dropdown
        Label messageLabel = new Label("Kies een cursus");
        for (int i = 0; cursists.size() > i; i++) {
            courseoptions.getItems().add(cursists.get(i));
        }

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
        Button show = new Button("Laat resultaat zien");
        show.setOnAction((Action) -> {
            result.setText("Voor deze cursus zijn " + numberGraduated + " gebruikers geslaagd");
            result.setVisible(true);
        });

        // Create HBox to hold label and dropdown
        HBox hBox1 = new HBox(10, messageLabel, courseoptions);
        hBox1.setAlignment(Pos.CENTER);

        // Create HBox to hold the buttons
        HBox hBox2 = new HBox(10, show, back);
        hBox2.setAlignment(Pos.CENTER);

        // Create a VBox to hold the HBox and button
        VBox vBox = new VBox(10, hBox1, hBox2, result);
        vBox.setAlignment(Pos.CENTER);

        // Create a new Scene with the VBox as the root node
        return new Scene(vBox, 500, 300);
    }

}
