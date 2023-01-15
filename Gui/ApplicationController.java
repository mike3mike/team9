package Gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ApplicationController extends Application {
    Scene index;
    Scene courses = new CourseController().printCourses();
    Scene cursists = new CursistController().printCursists();
    Scene certificates = new CertificateController().printCertificate();
    Scene registrations = new RegistrationController().printRegistration();
    Scene modules = new ModuleController().printCursists();
    Scene overview = new OverviewController().printOverviews();

    Stage window;

    @Override
    public void start(Stage window) throws Exception {
        this.window = window;
        BorderPane layout = new BorderPane();
        VBox list = new VBox();
        list.getChildren().add(new Label("Welkom!"));
        list.getChildren().add(new Label("kies een van de volgende acties:"));
        HBox buttons = new HBox();
        Button Courses = new Button("cursussen zien en wijzigen");
        Courses.setOnAction((Action) -> {
            window.setScene(courses);
        });
        Button cursists = new Button("cursisten zien en wijzigen");
        cursists.setOnAction((Action) -> {
            window.setScene(this.cursists);
        });
        Button registrations = new Button("inschrijvingen zien en wijzigen");
        registrations.setOnAction((Action) -> {
            window.setScene(this.registrations);
        });
        Button certificates = new Button("diploma's zien en wijzigen");
        certificates.setOnAction((Action) -> {
            window.setScene(this.certificates);
        });
        Button modules = new Button("modules zien en wijzigen");
        modules.setOnAction((Action) -> {
            window.setScene(this.modules);
        });
        Button overview = new Button("overzichten zien");
        cursists.setOnAction((Action) -> {
            window.setScene(this.overview);
        });
        buttons.getChildren().addAll(Courses, cursists, registrations, certificates, modules, overview);
        list.getChildren().add(buttons);
        layout.setCenter(list);
        this.index = new Scene(layout);
        window.setScene(index);
        window.show();
    }

}
