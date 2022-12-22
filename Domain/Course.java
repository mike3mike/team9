package Domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import Database.ConnectionDB;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextInputControl;

public class Course {
    private ConnectionDB con = new ConnectionDB();
    BorderPane homeScreen;
    public Scene course = new Scene(homeScreen);

    public void printCourses() {

        try {
            ResultSet rs = con.getList("SELECT * FROM Courses");
            VBox courses = new VBox();
            BorderPane layout = new BorderPane();
            while (rs.next()) {
                String name = rs.getString("name");
                String Subject = rs.getString("Subject");
                String description = rs.getString("description");
                String diffuculty = rs.getString("difficulty");
                Button edit = new Button("edit");
                Label Course = new Label(name + " " + Subject + " " + description + " " + diffuculty + " ");
                courses.getChildren().addAll(Course, edit);

            }
            layout.setTop(courses);
            Button add = new Button("add");
            add.setOnAction((EventHandler) -> {
                addCourse();
            });

            layout.setBottom(add);
            this.homeScreen = layout;
            this.course = new Scene(layout);

        } catch (SQLException e) {

        }

    }

    public void addCourse() {

        GridPane layout = new GridPane();
        TextField NameInput = new TextField();
        TextField subjectInput = new TextField();
        TextField descriptionInput = new TextField();
        TextField difficultyInput = new TextField();
        layout.add(new Label("Name"), 1, 1);
        layout.add(NameInput, 1, 2);
        layout.add(new Label("Subject"), 2, 1);
        layout.add(subjectInput, 2, 2);
        layout.add(new Label("Description"), 3, 1);
        layout.add(descriptionInput, 3, 2);
        layout.add(new Label("Difficulty"), 4, 1);
        layout.add(difficultyInput, 4, 2);

        Button button = new Button("verzenden");
        layout.add(button, 1, 5);
        button.setOnAction((eventHandler) -> {

            try {
                extracted(NameInput, subjectInput, descriptionInput, difficultyInput);

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                layout.add(new Label(e.getMessage()), 2, 5);

            }
        });
        this.course = new Scene(layout);

    }

    private void extracted(TextField NameInput, TextField subjectInput, TextField descriptionInput,
            TextField difficultyInput) throws SQLException {
        String name = "'" + NameInput.getText() + "'";
        String Subject = subjectInput.getText();
        String description = descriptionInput.getText();
        String difficulty = difficultyInput.getText();
        String SQL = "INSERT INTO Courses (name,subject,description,difficulty)VALUES (" + name + ",'" + Subject
                + "','" + description + "','" + difficulty + "')";
        con.execute(SQL);

        NameInput.clear();
        subjectInput.clear();
        descriptionInput.clear();
        difficultyInput.clear();

    }

}
