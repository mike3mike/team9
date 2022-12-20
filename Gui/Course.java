package Gui;

import java.sql.ResultSet;

import javax.naming.spi.DirStateFactory.Result;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Course extends Application {
    Domain.Course course = new Domain.Course();

    @Override
    public void start(Stage stage) throws Exception {
        VBox courses = new VBox();
        BorderPane layout = new BorderPane();
        ResultSet rs = course.printCourses();
        while (rs.next()) {
            String name = rs.getString("name");
            String Subject = rs.getString("Subject");
            String description = rs.getString("description");
            String diffuculty = rs.getString("difficulty");
            Label Course = new Label(name + " " + Subject + " " + description + " " + diffuculty);
            courses.getChildren().add(Course);
        }
        layout.setTop(courses);

        Scene view = new Scene(layout);
        stage.setScene(view);
        stage.show();

    }

}
