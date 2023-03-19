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
    Scene modules = new ModuleController().printModules();
    Scene webcasts = new WebcastController().printModules();
    Scene overviews = new OverviewController().printOverviews();

    Stage window;

    @Override
    public void start(Stage window) throws Exception {
        this.window = window;
        BorderPane layout = new BorderPane();
        VBox list = new VBox();
        list.getChildren().add(new Label("Welkom!"));
        list.getChildren().add(new Label("Kies een van de volgende acties:"));
        HBox buttons = new HBox();
        Button Courses = new Button("Cursussen zien en wijzigen");
        Courses.setOnAction((Action) -> {
            window.setScene(courses);
        });
        Button cursists = new Button("Cursisten zien en wijzigen");
        cursists.setOnAction((Action) -> {
            window.setScene(this.cursists);
        });
        Button registrations = new Button("Inschrijvingen zien en wijzigen");
        registrations.setOnAction((Action) -> {
            window.setScene(this.registrations);
        });
        Button certificates = new Button("Diploma's zien en wijzigen");
        certificates.setOnAction((Action) -> {
            window.setScene(this.certificates);
        });
        Button modules = new Button("Modules zien en wijzigen");
        modules.setOnAction((Action) -> {
            window.setScene(this.modules);
        });
        Button webcasts = new Button("Webcasts zien en wijzigen");
        webcasts.setOnAction((Action) -> {
            window.setScene(this.webcasts);
        });
        Button overview = new Button("Laat overzichten zien");
        overview.setOnAction((Action) -> {
            window.setScene(this.overviews);
        });
        buttons.getChildren().addAll(Courses, cursists, registrations, certificates, modules, webcasts, overview);
        list.getChildren().add(buttons);
        layout.setCenter(list);
        this.index = new Scene(layout);
        window.setScene(index);
        window.show();
    }

}
