package Gui.Overviews;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.lang.model.element.ModuleElement;

import Database.ConnectionDB;
import Domain.CourseDomain;
import Gui.ApplicationController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AverageOverview {
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

        // Create a new scene when the show button is clicked
        Button show = new Button("Laat resultaat zien");
        show.setOnAction((Action) -> {
            CourseDomain selectedCourse = courseoptions.getValue();
            if (selectedCourse != null) {
                Stage stage = new Stage();
                Scene scene = new Scene(getTable());
                stage.setScene(scene);
                stage.show();
            }
        });

        // Create HBox to hold label and dropdown
        HBox hBox1 = new HBox(10, messageLabel, courseoptions);
        hBox1.setAlignment(Pos.CENTER);

        // Create HBox to hold the buttons
        HBox hBox2 = new HBox(10, show, back);
        hBox2.setAlignment(Pos.CENTER);

        // Create a VBox to hold the HBox and button
        VBox vBox = new VBox(10, hBox1, hBox2);
        vBox.setAlignment(Pos.CENTER);

        // Create a new Scene with the VBox as the root node
        return new Scene(vBox, 500, 300);
    }

    public GridPane getTable() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(20));

        try {
            ResultSet sr = con.getList(
                    "SELECT " +
                            "   m.titel AS module_name, " +
                            "   AVG(p.progressie) AS gemiddelde_voortgang " +
                            "FROM " +
                            "   Module m " +
                            "   LEFT JOIN progressie p ON m.contentItemId = p.contentItemId " +
                            "   JOIN contentItem c ON m.contentItemId = c.id" +
                            "WHERE " +
                            "   CursusID = " + courseoptions.getValue().getId() +
                            "GROUP BY " +
                            "   c.titel " +
                            "HAVING " +
                            "   COUNT(p.id) > 0 " +
                            "ORDER BY " +
                            "   c.titel;");
            int index = 0;
            while (sr.next()) {
                String title = sr.getString("module_name");
                double average = sr.getInt("gemiddelde_voortgang");

                Label labelTitel = new Label();
                labelTitel.setText(title);

                Label labelAverage = new Label();
                labelAverage.setText(Double.toString(average));

                HBox row = new HBox(20);
                row.getChildren().addAll(labelTitel, labelAverage);
                row.setAlignment(Pos.CENTER);

                gridPane.addRow(index++, row);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return gridPane;
    }
}
