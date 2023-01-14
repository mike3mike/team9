package Gui;

import javafx.application.Application;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Database.ConnectionDB;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import Domain.Certificate;
import Domain.CourseDomain;
import Domain.Cursist;

public class CertificateController extends Application {

    private ConnectionDB con = new ConnectionDB();
    private TableView Certificates = new TableView<>();

    private TableColumn<Certificate, String> CursistColumn = new TableColumn<>("Cursist");
    private TableColumn<Certificate, String> CourseColumn = new TableColumn<>("Course");
    private TableColumn<Certificate, String> nameStaffcolumn = new TableColumn<>("staff name");
    private TableColumn<Certificate, String> gradecolumn = new TableColumn<>("grade");
    private ArrayList<CourseDomain> courses = new ArrayList<>();
    private ArrayList<Cursist> cursists = new ArrayList<>();

    public CertificateController() {
        CursistColumn.setCellValueFactory(new PropertyValueFactory<>("Cursist"));
        CourseColumn.setCellValueFactory(new PropertyValueFactory<>("Course"));
        nameStaffcolumn.setCellValueFactory(new PropertyValueFactory<>("nameStaff"));
        gradecolumn.setCellValueFactory(new PropertyValueFactory<>("grade"));

        Certificates.getColumns().addAll(CursistColumn, CourseColumn, nameStaffcolumn, gradecolumn);
        try {
            ResultSet sr = con.getList("Select * FROM Cursist ");
            while (sr.next()) {
                String name = sr.getString("naam");
                String email = sr.getString("email");
                String Dateofbirth = sr.getString("geboortedatum");
                String gender = sr.getString("geslacht");
                String addres = sr.getString("adres");
                String postalcode = sr.getString("postcode");

                String city = sr.getString("woonplaats");
                String country = sr.getString("land");
                int CursistId = sr.getInt("id");
                Cursist cursist = new Cursist(name, email, Dateofbirth, gender, addres, postalcode, city, country,
                        CursistId, null,
                        null);
                cursists.add(cursist);

            }

            sr = con.getList("SELECT * FROM cursus ");
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
    }

    public Scene printCertificate() {

        try {

            ResultSet rs = con.getList("SELECT * FROM certificaat");
            BorderPane layout = new BorderPane();
            Scene printregistrations = new Scene(layout);
            // this while loop adds courses to the table
            while (rs.next()) {
                int nameID = rs.getInt("CursistID");
                int courseID = rs.getInt("CursusID");

                String nameStaff = rs.getString("naamMedewerker");
                String grade = rs.getString("beoordeling");
                int id = rs.getInt("id");

                Certificates.getItems()
                        .add(new Certificate(nameID, courseID, grade, nameStaff, id));

            }
            layout.setLeft(Certificates);

            Button add = new Button("add new Certificate");
            Button delete = new Button("delete");
            Button Back = new Button("go back");
            HBox buttons = new HBox();
            buttons.getChildren().addAll(Back, add, delete);
            add.setOnAction((EventHandler) -> {
                Stage stage = new Stage();
                stage.setScene(addCertificate());
                stage.show();

            });
            delete.setOnAction((EventHandler) -> {
                TableViewSelectionModel selectionModel = Certificates.getSelectionModel();
                ObservableList<Certificate> selectedItems = selectionModel.getSelectedItems();
                Certificate id = selectedItems.get(0);
                Certificates.getItems().remove(id);

                try {
                    deletecertificate(id.getId());
                } catch (SQLException e) {
                    buttons.getChildren().add(new Label(e.getLocalizedMessage()));
                }

            });
            Back.setOnAction((Action) -> {
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

            layout.setBottom(buttons);
            return printregistrations;

        } catch (SQLException e) {
            System.out.println(e);

        }
        return null;

    }

    public Scene addCertificate() {

        GridPane layout = new GridPane();
        ChoiceBox<CourseDomain> choiceBoxCourses = new ChoiceBox<CourseDomain>();
        for (int i = 0; courses.size() > i; i++) {
            choiceBoxCourses.getItems().add(courses.get(i));
        }
        ChoiceBox<Cursist> choiceBoxCursists = new ChoiceBox<Cursist>();
        for (int i = 0; cursists.size() > i; i++) {
            choiceBoxCursists.getItems().add(cursists.get(i));
        }
        TextField NameStaffInput = new TextField();
        ChoiceBox<String> gradeCheckBox = new ChoiceBox<String>();
        gradeCheckBox.getItems().add("onvoldoende");
        gradeCheckBox.getItems().add("voldoende");
        gradeCheckBox.getItems().add("zeer goed");
        layout.add(new Label("Course"), 1, 1);
        layout.add(choiceBoxCourses, 1, 2);
        layout.add(new Label("Cursist"), 2, 1);
        layout.add(choiceBoxCursists, 2, 2);
        layout.add(new Label("naam medewerker"), 3, 1);
        layout.add(NameStaffInput, 3, 2);
        layout.add(new Label("beoordeling"), 4, 1);
        layout.add(gradeCheckBox, 4, 2);

        Button button = new Button("verzenden");
        layout.add(button, 1, 5);
        button.setOnAction((eventHandler) -> {

            try {
                extracted(choiceBoxCourses, choiceBoxCursists, gradeCheckBox, NameStaffInput);

            } catch (SQLException e) {
                layout.add(new Label(e.getMessage()), 2, 5);

            }
        });
        Scene addCourse = new Scene(layout);
        return addCourse;
    }

    // this method is a submit method for the addCourse method. It excecutes an
    // sql query to add the course to the database and it retrieves back the course
    // from the database so it can be added to the table
    // (the course is retrieved from the database because the id gets
    // autoincremented in the databases)
    private void extracted(ChoiceBox<CourseDomain> choiceBoxCourses, ChoiceBox<Cursist> choiceBoxCursists,
            ChoiceBox<String> gradeCheckBox,
            TextField NameStaffInput)
            throws SQLException {
        String NameStaff = NameStaffInput.getText();
        String grade = gradeCheckBox.getValue();
        CourseDomain course = choiceBoxCourses.getValue();
        Cursist cursist = choiceBoxCursists.getValue();
        int CourseID = course.getId();
        int curistID = cursist.getid();

        // adding course to database
        String SQL = "INSERT INTO certificaat (CursusID,CursistID,naamMedewerker,beoordeling)VALUES ('" + CourseID
                + "','"
                + curistID
                + "','" + NameStaff + "','" + grade + "' )";
        con.execute(SQL);
        // adding course to table
        try {
            ResultSet rs = con
                    .getList(
                            "SELECT * FROM certificaat WHERE CursusID = '" + CourseID + "' AND CursistID = '" + curistID
                                    + "' ");
            while (rs.next()) {
                int nameID = rs.getInt("Cursistid");
                int courseID = rs.getInt("cursusid");
                String nameStaff = rs.getString("naamMedewerker");
                grade = rs.getString("beoordeling");
                int id = rs.getInt("id");

                Certificates.getItems()
                        .add(new Certificate(nameID, courseID, grade, nameStaff, id));

            }
        } catch (SQLException e) {
            System.out.println(e);
            // TODO: handle exception
        }

        NameStaffInput.clear();

    }

    private void deletecertificate(int id) throws SQLException {
        String SQL = "DELETE FROM certificaat WHERE ID=" + id + ";";
        con.execute(SQL);

    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(printCertificate());
        stage.show();
    }

}
