package Gui;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OverviewController extends Application {

    Scene index;

    public Scene printOverviews() {
        BorderPane layout = new BorderPane();
        Scene printOverviews = new Scene(layout);
        VBox list = new VBox();
        list.getChildren().add(new Label("Welkom!"));
        list.getChildren().add(new Label("kies een van de volgende acties:"));
        HBox buttons = new HBox();
        HBox button2 = new HBox();
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

        Button geslacht = new Button("% Behaalde cursussen per geslacht");
        geslacht.setOnAction((Action) -> {
            // functionaliteit
        });
        Button gemiddeld = new Button("Gemiddelde voortgang per cursus en per module");
        gemiddeld.setOnAction((Action) -> {
            // functionaliteit
        });
        Button accountCursus = new Button("Voortgang cursus en modules per account");
        accountCursus.setOnAction((Action) -> {
            // functionaliteit
        });
        Button account = new Button("Behaalde certificaten per account");
        account.setOnAction((Action) -> {
            // functionaliteit
        });
        Button webcasts = new Button("Top 3 meest bekeken webcasts");
        webcasts.setOnAction((Action) -> {
            // functionaliteit
        });
        Button behaaldeCursussen = new Button("Top 3 meest behaalde cursussen");
        behaaldeCursussen.setOnAction((Action) -> {
            // functionaliteit
        });
        Button aanbevolen = new Button("Aanbevolen cursussen");
        aanbevolen.setOnAction((Action) -> {
            // functionaliteit
        });
        Button geslaagde = new Button("Aantal geslaagde cursisten per cursus");
        geslaagde.setOnAction((Action) -> {
            // functionaliteit
        });
        buttons.getChildren().addAll(geslacht, gemiddeld, accountCursus, account);
        button2.getChildren().addAll(back, webcasts, behaaldeCursussen, aanbevolen, geslaagde);
        list.getChildren().addAll(buttons, button2);
        layout.setCenter(list);
        return printOverviews;
    }
    @Override

    public void start(Stage stage) throws Exception {

        stage.setScene(printOverviews());
        stage.show();

    }
}