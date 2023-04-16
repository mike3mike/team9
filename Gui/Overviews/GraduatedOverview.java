package Gui.Overviews;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Database.ConnectionDB;
import Domain.CourseDomain;
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
    private ConnectionDB con = new ConnectionDB();
    private ArrayList<CourseDomain> courses = new ArrayList<>();
    private ComboBox<CourseDomain> courseoptions = new ComboBox<>();

    public Scene getScene() {

        try {
            ResultSet sr = con.getList("SELECT * FROM cursus ");
            while (sr.next()) {
                String Course = sr.getString("naam");
                String Subject = sr.getString("onderwerp");
                String description = sr.getString("introductietekst");
                String diffuculty = sr.getString("niveau");
                int courseId = sr.getInt("id");
                CourseDomain course = new CourseDomain(Course, Subject, description, diffuculty, courseId);
                courses.add(course);

            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        Label result = new Label("");
        result.setVisible(false);

        // Create a label with the message "Kies een cursus" and a dropdown
        Label messageLabel = new Label("Kies een cursus");
        for (int i = 0; courses.size() > i; i++) {
            courseoptions.getItems().add(courses.get(i));
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
            result.setText(getCompletedNumber() + " cursisten hebben deze cursus behaald");
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

    public int getCompletedNumber() {
        try {
            ResultSet rs = con.getList("SELECT COUNT(e.studentId) as graduated_students " +
                    "FROM inschrijving e " +
                    "WHERE e.cursusID = " + courseoptions.getValue().getId() + " AND degreeId > 0;");
            int result = rs.getInt("graduated_students");
            return result;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }
}