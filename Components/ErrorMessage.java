package Components;

import javax.swing.Action;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ErrorMessage {

    public static void ErrorScreen(String message) {

        Stage stage = new Stage();
        VBox box = new VBox();
        Label messageLabel = new Label(message);
        Button close = new Button("venster sluiten");
        box.setSpacing(20);
        Scene scene = new Scene(box);
        stage.setScene(scene);
        stage.show();
        close.setOnAction((Action) -> {
            stage.close();
        });
    }

}
