package Gui;

import java.sql.ResultSet;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Course extends Application {
    Domain.Course course = new Domain.Course();

    @Override
    public void start(Stage stage) throws Exception {
        Scene AddCourse = course.course;

        stage.setScene(AddCourse);
        stage.show();

    }

}
