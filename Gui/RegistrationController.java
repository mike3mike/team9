package Gui;

import javafx.application.Application;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Components.Validators;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Domain.CourseDomain;
import Domain.Cursist;
import Domain.Registration;

public class RegistrationController extends Application {
    private ConnectionDB con = new ConnectionDB();
    private TableView registrations = new TableView<>();

    private TableColumn<Registration, String> CursistColumn = new TableColumn<>("Cursist");
    private TableColumn<Registration, String> CourseColumn = new TableColumn<>("Course");
    private TableColumn<Registration, String> RegistrationColumn = new TableColumn<>("RegistrationDate");
    private ArrayList<CourseDomain> courses = new ArrayList<>();
    private ArrayList<Cursist> cursists = new ArrayList<>();

    public RegistrationController() {
        CursistColumn.setCellValueFactory(new PropertyValueFactory<>("Cursist"));
        CourseColumn.setCellValueFactory(new PropertyValueFactory<>("Course"));
        RegistrationColumn.setCellValueFactory(new PropertyValueFactory<>("RegistrationDate"));

        registrations.getColumns().addAll(CursistColumn, CourseColumn, RegistrationColumn);
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

    public Scene printRegistration() {

        try {

            ResultSet rs = con.getList("SELECT * FROM inschrijving");
            BorderPane layout = new BorderPane();
            Scene printregistrations = new Scene(layout);
            // this while loop adds courses to the table
            while (rs.next()) {
                int nameID = rs.getInt("Cursistid");
                int courseID = rs.getInt("cursusid");

                String RegistrationDate = rs.getString("inschrijfdatum");
                int id = rs.getInt("id");

                registrations.getItems()
                        .add(new Registration(nameID, courseID, RegistrationDate, id));

            }
            layout.setLeft(registrations);

            Button add = new Button("add new Registration");
            Button delete = new Button("delete");
            Button edit = new Button("edit");
            Button Back = new Button("go back");
            HBox buttons = new HBox();
            buttons.getChildren().addAll(Back, add,edit, delete);
            add.setOnAction((EventHandler) -> {
                Stage stage = new Stage();
                stage.setScene(addregistrations());
                stage.show();

            });
            edit.setOnAction((EventHandler) -> {
                TableViewSelectionModel selectionModel = registrations.getSelectionModel();
                ObservableList<Registration> selectedItems = selectionModel.getSelectedItems();
                Registration id = selectedItems.get(0);
                Stage stage = new Stage();
                editRegistration(id);
                stage.setScene(editRegistration(id));
                stage.show();
                registrations.refresh();

            });
            delete.setOnAction((EventHandler) -> {
                TableViewSelectionModel selectionModel = registrations.getSelectionModel();
                ObservableList<Registration> selectedItems = selectionModel.getSelectedItems();
                Registration id = selectedItems.get(0);
                registrations.getItems().remove(id);

                try {
                    deleteregistrations(id.getId());
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

        }
        return null;

    }

    public Scene addregistrations() {

        VBox layout = new VBox();
        ChoiceBox<CourseDomain> choiceBoxCourses = new ChoiceBox();
        for (int i = 0; courses.size() > i; i++) {
            choiceBoxCourses.getItems().add(courses.get(i));
        }
        ChoiceBox<Cursist> choiceBoxCursists = new ChoiceBox();
        for (int i = 0; cursists.size() > i; i++) {
            choiceBoxCursists.getItems().add(cursists.get(i));
        }
        HBox PublishDateBox = new HBox();
        TextField publishDay = new TextField();
        publishDay.setPromptText("Dag");
        TextField publishMonth = new TextField();
        publishMonth.setPromptText("maand");

        TextField publishYear = new TextField();
        publishYear.setPromptText("jaar");

        PublishDateBox.getChildren().addAll(publishDay, publishMonth, publishYear);
        layout.getChildren().add(new Label("Course"));
        layout.getChildren().add(choiceBoxCourses);
        layout.getChildren().add(new Label("Cursist"));
        layout.getChildren().add(choiceBoxCursists);
        layout.getChildren().add(new Label("Registration Date"));
        layout.getChildren().add(PublishDateBox);

        Button button = new Button("verzenden");

        layout.getChildren().add(button);
        button.setOnAction((eventHandler) -> {

            try {
                String date = publishDay.getText() + "-" + publishMonth.getText() + "-" + publishYear.getText();

                extracted(choiceBoxCourses, choiceBoxCursists, date);

            } catch (SQLException e) {
                layout.getChildren().add(new Label(e.getMessage()));

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
    private void extracted(ChoiceBox choiceBoxCourses, ChoiceBox choiceBoxCursists, String registrationDateInput)
            throws SQLException {
        String date = registrationDateInput;
        CourseDomain course = (CourseDomain) choiceBoxCourses.getValue();
        Cursist cursist = (Cursist) choiceBoxCursists.getValue();
        int CourseID = course.getId();
        int curistID = cursist.getid();

        // adding course to database
        String SQL = "INSERT INTO inschrijving (cursusID,cursistID,inschrijfdatum)VALUES ('" + CourseID + "','"
                + curistID
                + "','" + date + "')";
        con.execute(SQL);
        // adding course to table
        try {

            ResultSet rs = con
                    .getList(
                            "SELECT * FROM inschrijving WHERE cursusID = '" + CourseID + "' AND cursistID = '"
                                    + curistID
                                    + "' ");
            while (rs.next()) {

                int id = rs.getInt("ID");
                registrations.getItems()
                        .add(new Registration(curistID, CourseID, date, id));

            }

            for (int i = 0; course.getModules().size() > i; i++) {
                Domain.Module module = course.getModules().get(i);
                int contentItemID = module.getID();
                SQL = "INSERT INTO progressie (contentitemID,cursistID,progressie)VALUES ('" + contentItemID + "','"
                        + curistID
                        + "','" + 0 + "')";
                con.execute(SQL);

            }
        } catch (SQLException e) {
            System.out.println(e);
            // TODO: handle exception
        }

    }

     // this method returns a Scene where you can edit a course.
     public Scene editRegistration(Registration registration) {
       int id = registration.getId();
 
            VBox layout = new VBox();
            ChoiceBox<CourseDomain> choiceBoxCourses = new ChoiceBox();
            for (int i = 0; courses.size() > i; i++) {
              choiceBoxCourses.getItems().add(courses.get(i));
            }
            choiceBoxCourses.getSelectionModel().select(registration.getCourse());
            ChoiceBox<Cursist> choiceBoxCursists = new ChoiceBox();
            for (int i = 0; cursists.size() > i; i++) {
              choiceBoxCursists.getItems().add(cursists.get(i));
            }
            choiceBoxCursists.getSelectionModel().select(registration.getCursist());
            HBox PublishDateBox = new HBox();
            String[] date = registration.getRegistrationDate().split("-");
            TextField publishDay = new TextField(date[2]);
            publishDay.setPromptText("Dag");
            TextField publishMonth = new TextField(date[1]);
            publishMonth.setPromptText("maand");
      
            TextField publishYear = new TextField(date[0]);
            publishYear.setPromptText("jaar");
      
            PublishDateBox.getChildren().addAll(publishDay, publishMonth, publishYear);
            layout.getChildren().add(new Label("Course"));
            layout.getChildren().add(choiceBoxCourses);
            layout.getChildren().add(new Label("Cursist"));
            layout.getChildren().add(choiceBoxCursists);
            layout.getChildren().add(new Label("Registration Date"));
            layout.getChildren().add(PublishDateBox);
            Text Error = new Text();
            layout.getChildren().add(Error);
            Button button = new Button("verzenden");

      
            layout.getChildren().add(button);
                  button.setOnAction((eventHandler) -> {
                      String Date = publishDay.getText() + "-" + publishMonth.getText() + "-" + publishYear.getText();
      
                      if(Validators.dateValid(Date)){
                            try {
                                editButton(choiceBoxCourses, choiceBoxCursists, Date, registration);
                                layout.getChildren().add(new Label("Cursus is gewijzigd"));
            
                            } catch (SQLException e) {
                                System.out.println(e);
                                layout.getChildren().add(new Label(e.getMessage()));
            
                            }
                       

                    }
                
                    else{
                        Error.setText("Date is not valid");
                    }
                  });
              
      
              Scene printCourses = new Scene(layout);
      
              return printCourses;
    


    

    }

    // this method is a submit method for the editcourse method. it excecutes an SQL
    // query that updates the course info and it also updates the info of the course
    // on the table.
    private void editButton(ChoiceBox choiceBoxCourses, ChoiceBox choiceBoxCursists, String date, Registration registration) throws SQLException {
        CourseDomain course = (CourseDomain) choiceBoxCourses.getValue();
        Cursist cursist = (Cursist) choiceBoxCursists.getValue();
        int CourseID = course.getId();
        int curistID = cursist.getid();
        int id = registration.getId();

        String SQL = "UPDATE inschrijving SET cursusID = '"+CourseID+"',cursistID = '"+curistID+"',inschrijfdatum= '"+date+"' WHERE id=" + id + ";";
        con.execute(SQL);
        int registrationIndex = registrations.getItems().indexOf(registration);
        Registration newRegistration = new Registration(curistID, CourseID, date, id);
        Object[] Registrations = registrations.getItems().toArray();
        Registrations[registrationIndex] = newRegistration;
        registrations.getItems().clear();
        registrations.getItems().addAll(Registrations);
        Stage thisStage = (Stage) choiceBoxCourses.getScene().getWindow();
                thisStage.close();
        

    }

    private void deleteregistrations(int id) throws SQLException {
        String SQL = "DELETE FROM inschrijving WHERE ID=" + id + ";";
        con.execute(SQL);

    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(printRegistration());
        stage.show();
    }

}
