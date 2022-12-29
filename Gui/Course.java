package Gui;

import java.sql.ResultSet;
import java.sql.SQLException;

import Database.ConnectionDB;
import Domain.CourseDomain;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
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
import javafx.util.Callback;

public class Course extends Application {

    private ConnectionDB con = new ConnectionDB();
    private TableView courses = new TableView<>();
    private TableColumn<CourseDomain, String> nameColumn = new TableColumn<>("Name");
    private TableColumn<CourseDomain, String> subjectColumn = new TableColumn<>("subject");
    private TableColumn<CourseDomain, String> descriptionColumn = new TableColumn<>("description");
    private TableColumn<CourseDomain, String> difficultyColumn = new TableColumn<>("difficulty");
    private TableColumn actionCol = new TableColumn("Action");

    public Course() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        difficultyColumn.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        Callback<TableColumn<CourseDomain, String>, TableCell<CourseDomain, String>> cellFactory = //
                new Callback<TableColumn<CourseDomain, String>, TableCell<CourseDomain, String>>() {
                    @Override
                    public TableCell call(final TableColumn<CourseDomain, String> param) {
                        final TableCell<CourseDomain, String> cell = new TableCell<CourseDomain, String>() {

                            final Button btn = new Button("edit");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    btn.setOnAction(event -> {
                                        Stage stage = new Stage();
                                        CourseDomain course = getTableView().getItems().get(getIndex());

                                        stage.setScene(editCourse(course.getId()));
                                        stage.show();

                                    });
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        actionCol.setCellFactory(cellFactory);
        courses.getColumns().addAll(nameColumn, subjectColumn, descriptionColumn, difficultyColumn, actionCol);

    }

    public Scene printCourses() {

        try {

            ResultSet rs = con.getList("SELECT * FROM Courses");
            BorderPane layout = new BorderPane();
            Scene printCourses = new Scene(layout);

            while (rs.next()) {
                String name = rs.getString("name");
                String Subject = rs.getString("subject");
                String description = rs.getString("description");
                String diffuculty = rs.getString("difficulty");

                int id = rs.getInt("ID");
                courses.getItems().add(new CourseDomain(name, Subject, description, diffuculty, id));

            }
            layout.setLeft(courses);
            Button add = new Button("add new Course");
            Button delete = new Button("delete");
            HBox buttons = new HBox();
            buttons.getChildren().addAll(add, delete);
            add.setOnAction((EventHandler) -> {
                Stage stage = new Stage();
                stage.setScene(addCourse());
                stage.show();

            });
            delete.setOnAction((EventHandler) -> {
                TableViewSelectionModel selectionModel = courses.getSelectionModel();
                ObservableList<CourseDomain> selectedItems = selectionModel.getSelectedItems();
                CourseDomain id = selectedItems.get(0);
                courses.getItems().remove(id);

                try {
                    deleteCourse(id.getId());
                } catch (SQLException e) {
                    buttons.getChildren().add(new Label(e.getLocalizedMessage()));
                }

            });

            layout.setBottom(buttons);
            return printCourses;

        } catch (SQLException e) {

        }
        return null;

    }

    public Scene addCourse() {

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
                layout.add(new Label(e.getMessage()), 2, 5);

            }
        });
        Scene addCourse = new Scene(layout);
        return addCourse;
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
        try {
            ResultSet rs = con
                    .getList(
                            "SELECT * FROM Courses WHERE name = " + name + " AND difficulty = '" + difficulty + "' ");
            while (rs.next()) {
                name = rs.getString("name");
                Subject = rs.getString("subject");
                description = rs.getString("description");
                String diffuculty = rs.getString("difficulty");

                int id = rs.getInt("ID");
                courses.getItems().add(new CourseDomain(name, Subject, description, diffuculty, id));
                Button edit = new Button("edit");
                edit.setOnAction((EventHandler) -> {
                    Stage stage = new Stage();
                    stage.setScene(editCourse(id));
                    stage.show();

                });

            }
        } catch (SQLException e) {
            System.out.println(e);
            // TODO: handle exception
        }

        NameInput.clear();
        subjectInput.clear();
        descriptionInput.clear();
        difficultyInput.clear();

    }

    public Scene editCourse(int id) {
        try {

            GridPane layout = new GridPane();

            ResultSet rs = con.getList("SELECT * FROM Courses where ID = " + id);

            while (rs.next()) {
                TextField NameInput = new TextField(rs.getString("name"));
                TextField subjectInput = new TextField(rs.getString("subject"));
                TextField descriptionInput = new TextField(rs.getString("description"));
                TextField difficultyInput = new TextField(rs.getString("difficulty"));
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
                        editButton(NameInput, subjectInput, descriptionInput, difficultyInput, id);
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

    private void editButton(TextField NameInput, TextField subjectInput, TextField descriptionInput,
            TextField difficultyInput, int id) throws SQLException {
        String name = NameInput.getText();
        String Subject = subjectInput.getText();
        String description = descriptionInput.getText();
        String difficulty = difficultyInput.getText();
        String SQL = "UPDATE Courses SET Name='" + name + "', subject='" + Subject + "', description = '" + description
                + "' ,difficulty = '" + difficulty + "' WHERE ID=" + id + ";";
        con.execute(SQL);

    }

    private void deleteCourse(int id) throws SQLException {
        String SQL = "DELETE FROM Courses WHERE ID=" + id + ";";
        con.execute(SQL);

    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setScene(printCourses());
        stage.show();

    }

}
