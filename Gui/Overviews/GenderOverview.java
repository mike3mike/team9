package Gui.Overviews;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Database.ConnectionDB;
import Domain.Cursist;
import Gui.ApplicationController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GenderOverview {
    private ConnectionDB con = new ConnectionDB();

    private static final int BUTTON_WIDTH = 120;
    private static final int BUTTON_HEIGHT = 50;

    private ComboBox<String> genderOptions = new ComboBox<>();
    private Map<String, String> gender;
    private int completedPercentage;

    public Scene getScene() {

        // Create label with results
        Label result = new Label("");
        result.setVisible(false);

        // Create a label with the message "Kies een geslacht" and a dropdown
        Label messageLabel = new Label("Kies een geslacht");
        genderOptions.getItems().addAll("Male", "Female");

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
            Iterator<Map.Entry<String, String>> iterator = getDropdownValue().entrySet().iterator();
            String gender = iterator.next().getValue();
            completedPercentage = getCompletedPercentage();
            result.setText("Voor het geslacht " + gender + " is " + String.valueOf(completedPercentage)
                    + " procent van de ingeschreven cursussen behaald");
            result.setVisible(true);
        });

        // Create HBox to hold label and dropdown
        HBox hBox1 = new HBox(10, messageLabel, genderOptions);
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

    private Map<String, String> getDropdownValue() {
        String selectedValue = genderOptions.getValue();
        Map<String, String> result = new HashMap<>();
        if (selectedValue.equals("Male")) {
            result.put("m", "male");
        } else if (selectedValue.equals("Female")) {
            result.put("f", "female");
        }
        return result;
    }

    private Integer getCompletedPercentage() {
        // TODO add functionality to get the percentage of courses completed
        Iterator<Map.Entry<String, String>> iterator = getDropdownValue().entrySet().iterator();
        String gender = iterator.next().getKey();
        try {
            ResultSet sr = con.getList(
                    "SELECT geslacht, COUNT(*) AS totaal, SUM(CASE WHEN certificaat.beoordeling > 5.5 THEN 1 ELSE 0 END) AS totaalGehaald FROM certificaat JOIN cursist ON cursist.id = certificaat.CursistID WHERE geslacht = '"
                            + gender + "' GROUP BY geslacht");
            sr.next();
            int total = sr.getInt("totaal");
            int totalCompleted = sr.getInt("totaalGehaald");
            double percentage = (double) totalCompleted / total;
            int totalPercentage = (int) (percentage * 100.00);
            return totalPercentage;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
