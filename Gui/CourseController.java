package Gui;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Components.ErrorMessage;
import Database.ConnectionDB;
import Domain.CourseDomain;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CourseController extends Application {

    private ConnectionDB con = new ConnectionDB();
    private TableView courses = new TableView<>();
    private TableColumn<CourseDomain, String> nameColumn = new TableColumn<>("Name");
    private TableColumn<CourseDomain, String> subjectColumn = new TableColumn<>("subject");
    private TableColumn<CourseDomain, String> descriptionColumn = new TableColumn<>("description");
    private TableColumn<CourseDomain, String> difficultyColumn = new TableColumn<>("difficulty");

    private ArrayList<Domain.Module> modules = new ArrayList<>();

    public CourseController() {
        // constructor for Course where tablecolumns are made
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        difficultyColumn.setCellValueFactory(new PropertyValueFactory<>("difficulty"));

        courses.getColumns().addAll(nameColumn, subjectColumn, descriptionColumn, difficultyColumn);
        try {

            ResultSet rs = con.getList(
                    "SELECT * from module INNER JOIN contentItem on contentItemid = contentItem.id WHERE cursusID IS NULL");
            // this while loop adds courses to the array
            while (rs.next()) {
                String title = rs.getString("titel");
                String description = rs.getString("beschrijving");
                String publishDate = rs.getString("publicatiedatum");
                String status = rs.getString("status");
                String version = rs.getString("versie");
                String contact = rs.getString("naamContactpersoon");
                String contactEmail = rs.getString("email");
                int id = rs.getInt("id");
                int ContentItemID = rs.getInt("contentItemid");

                modules.add(new Domain.Module(ContentItemID, id, publishDate, status, title, description, version,
                        contact,
                        contactEmail));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    // this method shows the courses on the screen
    public Scene printCourses() {

        try {

            ResultSet rs = con.getList("SELECT * FROM cursus");
            BorderPane layout = new BorderPane();
            Scene printCourses = new Scene(layout);
            // this while loop adds courses to the table
            while (rs.next()) {
                String name = rs.getString("naam");
                String subject = rs.getString("onderwerp");
                String description = rs.getString("introductietekst");
                String diffuculty = rs.getString("niveau");

                int id = rs.getInt("ID");
                courses.getItems().add(new CourseDomain(name, subject, description, diffuculty, id));

            }
            layout.setLeft(courses);

            Button add = new Button("add new Course");
            Button addModule = new Button("add module to course");
            Button delete = new Button("delete");
            Button edit = new Button("edit");
            Button back = new Button("go back");
            HBox buttons = new HBox();
            buttons.getChildren().addAll(back, add,addModule, delete, edit);
            add.setOnAction((EventHandler) -> {
                if (modules.size() == 0) {
                    ErrorMessage.ErrorScreen("voeg eerst een module toe!");
                } else {
                    addCourse();
                }

            });
            delete.setOnAction((EventHandler) -> {
                TableViewSelectionModel selectionModel = courses.getSelectionModel();
                ObservableList<CourseDomain> selectedItems = selectionModel.getSelectedItems();
                CourseDomain id = selectedItems.get(0);
                courses.getItems().remove(id);

                try {
                    deleteCourse(id.getId());
                } catch (SQLException e) {
                    ErrorMessage.ErrorScreen(e.getSQLState());
                    ;
                }

            });
            addModule.setOnAction((EventHandler) -> {
                TableViewSelectionModel selectionModel = courses.getSelectionModel();
                ObservableList<CourseDomain> selectedItems = selectionModel.getSelectedItems();
                CourseDomain id = selectedItems.get(0);
                // courses.getItems().remove(id);
                Stage stage = new Stage();
                try {
                    AddModule(id);
                    stage.setScene(AddModule(id));

                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                stage.show();
                courses.refresh();

            });

            edit.setOnAction((EventHandler) -> {
                TableViewSelectionModel selectionModel = courses.getSelectionModel();
                ObservableList<CourseDomain> selectedItems = selectionModel.getSelectedItems();
                CourseDomain id = selectedItems.get(0);
                // courses.getItems().remove(id);
                Stage stage = new Stage();
                editCourse(id);
                stage.setScene(editCourse(id));
                stage.show();
                courses.refresh();

            });
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

            layout.setBottom(buttons);
            return printCourses;

        } catch (SQLException e) {

        }
        return null;

    }

    // this method returns a Scene where you can submit a form that adds a course
    public void addCourse() {
        Stage stage = new Stage();

        GridPane layout = new GridPane();
        TextField NameInput = new TextField();
        TextField subjectInput = new TextField();
        TextField descriptionInput = new TextField();
        TextField difficultyInput = new TextField();
        layout.add(new Label("naam"), 1, 1);
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
            // this try catch is to submit the course. It excecutes an
            // sql query to add the course to the database and it retrieves back the course
            // from the database so it can be added to the table
            // (the course is retrieved from the database again because the id gets
            // autoincremented in the database)
            try {
                String name = "'" + NameInput.getText() + "'";
                String Subject = subjectInput.getText();
                String description = descriptionInput.getText();
                String difficulty = difficultyInput.getText();
                // adding course to database
                String SQL = "INSERT INTO cursus (naam,onderwerp,introductietekst,niveau)VALUES (" + name + ",'"
                        + Subject
                        + "','" + description + "','" + difficulty + "')";
                con.execute(SQL);
                // adding course to table
                try {
                    ResultSet rs = con
                            .getList(
                                    "SELECT * FROM cursus WHERE naam = " + name + " AND niveau = '" + difficulty
                                            + "' ");
                    while (rs.next()) {
                        name = rs.getString("naam");
                        Subject = rs.getString("onderwerp");
                        description = rs.getString("introductietekst");
                        String diffuculty = rs.getString("niveau");

                        int id = rs.getInt("id");
                        CourseDomain course = new CourseDomain(name, Subject, description, diffuculty, id);
                        courses.getItems().add(course);
                        stage.setScene(AddModule(course));

                    }
                } catch (SQLException e) {
                    System.out.println(e);
                    // TODO: handle exception
                }
            } catch (SQLException e) {
                Label errormessage = new Label();
                errormessage.setText(e.getMessage());
                layout.add(errormessage, 2, 5);

            }
        });
        Scene addCourse = new Scene(layout);
        stage.setScene(addCourse);
        stage.show();
    }

    private Scene AddModule(CourseDomain id) throws SQLException {
        VBox layout = new VBox();
        ChoiceBox<Domain.Module> modules = new ChoiceBox<>();
        for (int i = 0; this.modules.size() > i; i++) {
            modules.getItems().add(this.modules.get(i));
        }
        Button submit = new Button("verzenden");
        submit.setOnAction((Action) -> {
            Domain.Module Selectedmodule = modules.getValue();
            int moduleID = Selectedmodule.getModuleId();
            System.out.println(moduleID);
            String SQL = "UPDATE module SET cursusID = " + id.getId() + " WHERE contentItemid =" + moduleID;
            try {
                con.execute(SQL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Node node = (Node) Action.getSource();
            Stage thisStage = (Stage) node.getScene().getWindow();
            thisStage.close();

        });
        layout.getChildren().addAll(new Label("voeg module toe"), modules, submit);
        return new Scene(layout);

    }

    // this method returns a Scene where you can edit a course.
    public Scene editCourse(CourseDomain course) {
        try {

            GridPane layout = new GridPane();
            int id = course.getId();

            ResultSet rs = con.getList("SELECT * FROM cursus where ID = " + id);

            while (rs.next()) {
                TextField NameInput = new TextField(rs.getString("naam"));
                TextField subjectInput = new TextField(rs.getString("onderwerp"));
                TextField descriptionInput = new TextField(rs.getString("introductietekst"));
                TextField difficultyInput = new TextField(rs.getString("niveau"));
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
                        editButton(NameInput, subjectInput, descriptionInput, difficultyInput, id, course);
                        layout.add(new Label("Cursus is gewijzigd"), 2, 5);

                    } catch (SQLException e) {
                        layout.add(new Label(e.getMessage()), 2, 5);

                    }
                });
            }

            Scene printCourses = new Scene(layout);

            return printCourses;

        } catch (SQLException e) {

        }
        return null;

    }

    // this method is a submit method for the editcourse method. it excecutes an SQL
    // query that updates the course info and it also updates the info of the course
    // on the table.
    private void editButton(TextField NameInput, TextField subjectInput, TextField descriptionInput,
            TextField difficultyInput, int id, CourseDomain course) throws SQLException {
        String name = NameInput.getText();
        String Subject = subjectInput.getText();
        String description = descriptionInput.getText();
        String difficulty = difficultyInput.getText();
        String SQL = "UPDATE cursus SET naam='" + name + "', onderwerp='" + Subject + "', introductietekst = '"
                + description
                + "' ,niveau = '" + difficulty + "' WHERE id=" + id + ";";
        con.execute(SQL);
        int courseIndex = courses.getItems().indexOf(course);
        CourseDomain changedCourse = new CourseDomain(name, Subject, description, difficulty, id);
        Object[] Courses = courses.getItems().toArray();
        Courses[courseIndex] = changedCourse;
        courses.getItems().clear();
        courses.getItems().addAll(Courses);
        Stage thisStage = (Stage) NameInput.getScene().getWindow();
                thisStage.close();
        

    }

    private void deleteCourse(int id) throws SQLException {
        String SQL = "DELETE FROM cursus WHERE id=" + id + ";";
        con.execute(SQL);

    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setScene(printCourses());
        stage.show();

    }

}
