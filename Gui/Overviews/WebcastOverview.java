package Gui.Overviews;

import java.sql.ResultSet;
import java.sql.SQLException;

import Database.ConnectionDB;
import Gui.ApplicationController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WebcastOverview {
    private ConnectionDB con = new ConnectionDB();

    public Scene getScene() {

        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(20));

        try {
            ResultSet sr = con.getList(
                    "SELECT TOP 3 c.titel, count(w.contentItemid) AS views FROM Webcast w  JOIN progressie p on w.contentItemId = p.contentItemId JOIN contentItem c on c.id = w.contentItemid GROUP BY c.titel ORDER BY views DESC");
            int index = 0;
            while (sr.next()) {
                String titel = sr.getString("titel");
                int views = sr.getInt("views");

                Label labelTitel = new Label();
                labelTitel.setText(titel);

                Label labelViews = new Label();
                labelViews.setText(Integer.toString(views));

                HBox row = new HBox(20);
                row.getChildren().addAll(labelTitel, labelViews);
                row.setAlignment(Pos.CENTER);

                gridPane.addRow(index++, row);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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

        // Create a VBox to hold the HBox and button
        VBox vBox = new VBox(10, gridPane, back);
        vBox.setAlignment(Pos.CENTER);

        // // Create a new Scene with the VBox as the root node
        return new Scene(vBox, 500, 300);
    }

}
