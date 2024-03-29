package Gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Gui.Overviews.AverageOverview;
import Gui.Overviews.GenderOverview;
import Gui.Overviews.GraduatedOverview;
import Gui.Overviews.WebcastOverview;

public class ApplicationController extends Application {
    Scene index;
    Scene courses = new CourseController().printCourses();
    Scene cursists = new CursistController().printCursists();
    Scene certificates = new CertificateController().printCertificate();
    Scene registrations = new RegistrationController().printRegistration();
    Scene modules = new ModuleController().printModules();
    Scene webcasts = new WebcastController().printModules();
    Scene overviews = printOverviews();

    private static final int BUTTON_WIDTH = 120;
    private static final int BUTTON_HEIGHT = 50;

    Stage window;

    @Override
    public void start(Stage window) throws Exception {
        this.window = window;
        Button coursesButton = new Button("Cursussen zien en wijzigen");
        coursesButton.setOnAction((Action) -> {
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
        overview.setPrefWidth(BUTTON_WIDTH * 4);

        HBox row1 = new HBox(20);
        row1.getChildren().addAll(coursesButton, cursists, registrations);
        row1.setAlignment(Pos.CENTER);

        HBox row2 = new HBox(20);
        row2.getChildren().addAll(certificates, modules, webcasts);
        row2.setAlignment(Pos.CENTER);

        HBox row3 = new HBox(20);
        row3.getChildren().add(overview);
        row3.setAlignment(Pos.CENTER);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(20));
        gridPane.addRow(0, row1);
        gridPane.addRow(1, row2);
        gridPane.addRow(2, row3);

        Text title = new Text("Hoofdpagina");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setFill(Color.WHITE);
        StackPane titlePane = new StackPane(title);
        titlePane.setStyle("-fx-background-color: #0072C6;");
        titlePane.setPadding(new Insets(20));

        BorderPane root = new BorderPane();
        root.setCenter(gridPane);
        root.setTop(titlePane);
        this.index = new Scene(root);
        window.setScene(index);
        window.show();
    }

    public Scene printOverviews() {

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
        back.setPrefWidth(BUTTON_WIDTH * 4);

        Button gender = new Button("% Behaalde cursussen per geslacht");
        gender.setOnAction((Action) -> {
            GenderOverview genderScene = new GenderOverview();
            window.setScene(genderScene.getScene());
        });
        Button average = new Button("Gemiddelde voortgang per cursus en per module");
        average.setOnAction((Action) -> {
            AverageOverview averageScene = new AverageOverview();
            window.setScene(averageScene.getScene());
        });
        Button webcasts = new Button("Top 3 meest bekeken webcasts");
        webcasts.setOnAction((Action) -> {
            WebcastOverview webcastScene = new WebcastOverview();
            window.setScene(webcastScene.getScene());
        });
        Button graduated = new Button("Aantal geslaagde cursisten per cursus");
        graduated.setOnAction((Action) -> {
            GraduatedOverview graduatedScene = new GraduatedOverview();
            window.setScene(graduatedScene.getScene());
        });

        HBox row1 = new HBox(20);
        row1.getChildren().addAll(gender, average);
        row1.setAlignment(Pos.CENTER);

        HBox row2 = new HBox(20);
        row2.getChildren().addAll(webcasts, graduated);
        row2.setAlignment(Pos.CENTER);

        HBox row3 = new HBox(20);
        row3.getChildren().add(back);
        row3.setAlignment(Pos.CENTER);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setPadding(new Insets(20));
        gridPane.addRow(0, row1);
        gridPane.addRow(1, row2);
        gridPane.addRow(2, row3);

        Text title = new Text("Overzichten");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setFill(Color.WHITE);
        StackPane titlePane = new StackPane(title);
        titlePane.setStyle("-fx-background-color: #0072C6;");
        titlePane.setPadding(new Insets(20));

        BorderPane root = new BorderPane();
        root.setCenter(gridPane);
        root.setTop(titlePane);

        Scene overviewScene = new Scene(root);
        return overviewScene;
    }

}
