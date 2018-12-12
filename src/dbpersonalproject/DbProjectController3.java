/***************************************************
 * File: DbProjectController3.java
 * Author: Marion Armbruster
 * Date: 5 November 2018
 ****************************************************/

package dbpersonalproject;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * This class is the controller for the third screen of the gui. It contains action listener methods
 * for buttons that display the entirety of the database, and will eventually allow the user to
 * search the database with certain conditions, taken from user input. This class also contains a
 * method that leads back to the second screen of the gui should the user wish to add more events to
 * the database.
 */

public class DbProjectController3 {

  // A regular expression patter and match for both the date and the month as insurance that data
  // the user typed in is appropriate
  private Pattern patternMonth = Pattern.compile("(^[1-9]|1[0-2]$)");
  private Pattern patternDate = Pattern.compile("(^[1-9]|1[0-9]|2[0-9]|3[0-1]$)");

  private Matcher isMatchMonth;
  private Matcher isMatchDate;

  // for goToHome action
  @FXML
  private Button home;

  // search button
  @FXML
  private Button searchDatabase;

  // for displaying the full database
  @FXML
  private Button viewAll;

  // to delete something from the database
  @FXML
  private Button deleteFromDatabase;

  // for closing the window
  @FXML
  private Button logOff;

  @FXML
  private TextField month;

  @FXML
  private TextField date;

  @FXML
  private ComboBox<String> type;

  @FXML
  private TableView dbTable;

  // class name and data type with column name -- for the table view in the gui
  @FXML
  private TableColumn<DataForTable, Integer> keyNumColumn;

  @FXML
  private TableColumn<DataForTable, Timestamp> dateAndTimeColumn;

  @FXML
  private TableColumn<DataForTable, String> typeOfEventColumn;

  /**
   * This method is a special one that is called first the moment the scene is called and appears as
   * a window. It links the columns of the table in the gui to the properties and columns of the
   * database's table. Therefore, the table in the gui will reflect the data that is in the database
   * at any time.
   */
  @FXML
  public void initialize() {
    // The setCellValueFactory(...) that is set on the table columns are used to determine
    // which field inside the DataForTable objects should be used for the particular column.
    // Links the table view with the table in the database

    keyNumColumn.setCellValueFactory(cellData -> cellData.getValue().keyProperty().asObject());
    dateAndTimeColumn.setCellValueFactory(cellData -> cellData.getValue().dateTimeGroupProperty());
    typeOfEventColumn.setCellValueFactory(cellData -> cellData.getValue().eventType());

    // fills the combo-box with the two options available in the database
    type.getItems().add("in");
    type.getItems().add("out");

    //ToDo: make tableview seen at initialize; cannot do w/current fillTable method b/c ActionEvent
  }

  /**
   * This method moves back to the second scene, linked by the action of clicking the button in the
   * gui.
   *
   * @param event The standard event action listener.
   * @Author Damian Morgan - gave assistance with this code. Also found here:
   */
  @FXML
  void goToHome(ActionEvent event) throws IOException {
    Parent toSecondScreen = FXMLLoader.load(getClass().getResource("dbPersonalProjectPage2.fxml"));
    Scene fourthScene = new Scene(toSecondScreen);
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(fourthScene);
    window.show();
  }

  /**
   * This method calls the function in the DOA class that gathers the ResultSet of the database into
   * an ObservableList of the class type that links the database to the table view and then displays
   * all of the objects that were added to the ObservableList in the table view of the gui.
   *
   * @param event The standard event action listener.
   * @throws SQLException General exception in the event that data was not able to be retrieved from
   *                      the database.
   */
  @FXML
  private void fillTable(ActionEvent event) throws SQLException {
    String sqlstmt = "SELECT * FROM Event";
    databaseToTable(sqlstmt);
  }

  /**
   * A method that updates the table view to reflect a query statement given with specific
   * conditions. It is connected to the "search database" button.
   *
   * @param event The standard event action listener.
   * @throws SQLException General exception in the event that data was not able to be retrieved from
   *                      the database.
   */
  @FXML
  private void updateTableView(ActionEvent event) throws SQLException {
    isMatchMonth = patternMonth.matcher(month.getText());
    isMatchDate = patternDate.matcher(date.getText());

    // if month AND date are not empty; i.e something was typed into both fields
    if (!(month.getText().trim().isEmpty()) && !(date.getText().trim().isEmpty())) {
      // if both fields match their regex format
      if (isMatchMonth.matches() && isMatchDate.matches()) {
        // declares an sql SELECT statement using the values from the two text fields
        String monthAndDate = "SELECT * FROM Event WHERE MONTH(DATETIMEGROUP) = " + month.getText()
            + " AND DAY(DATETIMEGROUP) = " + date.getText();
        // call function to kick off the rest of it
        databaseToTable(monthAndDate);
      } else {
        System.out.println("Input did not match regular expression for month or date.");
      }
    } else if (!(month.getText().trim().isEmpty()) && type.getValue() != null) {
      // if month AND combo-box are not empty; i.e something was typed into both fields
      // if month matches its regex format; combo-box was already checked
      if (isMatchMonth.matches()) {
        // declares an sql SELECT statement using the values from the two text fields
        String monthAndBox = "SELECT * FROM Event WHERE MONTH(DATETIMEGROUP) = " + month.getText()
            + " AND EVENTTYPE = '" + type.getValue() + "'";
        // call function to kick off the rest of it
        databaseToTable(monthAndBox);
      } else {
        System.out.println("Input did not match regular expression for month or type.");
      }
    } else if (!(date.getText().trim().isEmpty()) && type.getValue() != null) {
      // if date AND combo-box are not empty; i.e something was typed into both fields
      // if date matches its regex format; combo-box was already checked
      if (isMatchDate.matches()) {
        // declares an sql SELECT statement using the values from the two text fields
        String dateAndBox = "SELECT * FROM Event WHERE DAY(DATETIMEGROUP) = " + date.getText()
            + " AND EVENTTYPE = '" + type.getValue() + "'";
        // call function to kick off the rest of it
        databaseToTable(dateAndBox);
      } else {
        System.out.println("Input did not match regular expression date or type.");
      }
    } else if (type.getValue() != null) {
      // i.e. if something was selected from combo-box
      // declares an sql SELECT statement using the value from the combo-box
      String sqlStmt = "SELECT * FROM Event WHERE EVENTTYPE = '" + type.getValue() + "'";
      // call function to kick off the rest of it
      databaseToTable(sqlStmt);
      /**FindBugs says that this "passes a non-constant String to an execute or addBatch method on
       an SQL statement", however, this and one other are the only complaints out of all my
       statements that are exactly like this one.*/
    } else if (!(month.getText().trim().isEmpty())) {
      // i.e. if something was typed into month field
      // if month matches its regex format
      if (isMatchMonth.matches()) {
        // declares an sql SELECT statement using the value from the month text field
        String monthStmt = "SELECT * FROM Event WHERE MONTH(DATETIMEGROUP) = " + month.getText();
        // call function to kick off the rest of it
        databaseToTable(monthStmt);
      } else {
        System.out.println("Input did not match regular expression for month.");
      }
    } else if (!(date.getText().trim().isEmpty())) {
      // i.e. if something was typed into the date field
      // if date matches its regex format
      if (isMatchDate.matches()) {
        // declares an sql SELECT statement using the value from the date text field
        String dateStmt = "SELECT * FROM Event WHERE DAY(DATETIMEGROUP) = " + date.getText();
        // call function to kick off the rest of it
        databaseToTable(dateStmt);
      } else {
        System.out.println("Input did not match regular expression for date.");
      }
    }

    // clear the fields after the database/tableview has been populated
    month.clear();
    date.clear();
    type.setValue(null);
  } // end of updateTableView method

  /**
   * This method uses a chain of method calls to retrieve all of the records that satisfy the sql
   * statement passed in and then another set of method calls with this data to fill up the
   * tableview of the gui with the records that are in the database.
   *
   * @param string The sql statement that had been created locally in the method that called
   *               databaseToTable. This string will vary dependent upon what is being done with
   *               the database.
   * @throws SQLException General exception in the event that data was not able to be retrieved from
   *                      the database.
   */
  @FXML
  private void databaseToTable(String string) throws SQLException {
    try {
      // gets all table data information
      ObservableList<DataForTable> tableData = DataForTableDao.makeTableAppear(string);
      // populates the data into TableView
      populateData(tableData);
    } catch (SQLException sql) {
      System.out.println("Error occurred while getting data from DB.\n" + sql);
      throw sql;
    }
  }

  /**
   * This method takes the ResultSet linked to the ObservableList and sets these objects in the
   * table view of the gui. This allows the table view to mirror what is in the database, depending
   * on the query statement used. It populates the data into the TableView.
   *
   * @param data This is the object passed in that holds all objects in the observable list of the
   *             class datatype "DataForTable".
   */
  @FXML
  private void populateData(ObservableList<DataForTable> data) {
    // Set items to the database Table (the TableView)
    dbTable.setItems(data);
  }

  /**
   * A method that deletes a record from the table view using a specific query statement. It is
   * connected to the "delete from database" button.
   *
   * @param event The standard event action listener.
   * @throws SQLException General exception in the event that data was not able to be retrieved from
   *                      the database.
   */
  @FXML
  void deleteRecord(ActionEvent event) throws SQLException {
    isMatchMonth = patternMonth.matcher(month.getText());
    isMatchDate = patternDate.matcher(date.getText());

    // if month AND date are not empty; i.e something was typed into both fields
    if (!(month.getText().trim().isEmpty()) && !(date.getText().trim().isEmpty())) {
      // if both fields match their regex format
      if (isMatchMonth.matches() && isMatchDate.matches()) {
        // declares an sql DELETE statement using the values from the two text fields
        String deleteMoDa = "DELETE FROM Event WHERE MONTH(DATETIMEGROUP) = " + month.getText()
            + " AND DAY(DATETIMEGROUP) = " + date.getText();
        // call function to kick off the rest of it
        DbUtil.dbExecuteUpdate(deleteMoDa);
        System.out.println("DELETE FROM Event WHERE MONTH(DATETIMEGROUP) = " + month.getText()
             +  " AND DAY(DATETIMEGROUP) = " + date.getText());
      } else {
        System.out.println("Input did not match regular expression for month or date.");
      }
    } else if (!(month.getText().trim().isEmpty()) && type.getValue() != null) {
      // if month AND combo-box are not empty; i.e something was typed into both fields
      // if month matches its regex format; combo-box was already checked
      if (isMatchMonth.matches()) {
        // declares an sql DELETE statement using the values from the two text fields
        String deleteMoCb = "DELETE FROM Event WHERE MONTH(DATETIMEGROUP) = " + month.getText()
            + " AND EVENTTYPE = '" + type.getValue() + "'";
        // call function to kick off the rest of it
        DbUtil.dbExecuteUpdate(deleteMoCb);
        System.out.println("DELETE FROM Event WHERE MONTH(DATETIMEGROUP) = " + month.getText()
            +  " AND EVENTTYPE = '" + date.getText() + "'");
      } else {
        System.out.println("Input did not match regular expression for month or type.");
      }
    } else if (!(date.getText().trim().isEmpty()) && type.getValue() != null) {
      // if date AND combo-box are not empty; i.e something was typed into both fields
      // if date matches its regex format; combo-box was already checked
      if (isMatchDate.matches()) {
        // declares an sql DELETE statement using the values from the two text fields
        String deleteDaCb = "DELETE FROM Event WHERE DAY(DATETIMEGROUP) = " + date.getText()
            + " AND EVENTTYPE = '" + type.getValue() + "'";
        // call function to kick off the rest of it
        DbUtil.dbExecuteUpdate(deleteDaCb);
        System.out.println("DELETE FROM Event WHERE DAY(DATETIMEGROUP) = " + date.getText()
            +  " AND EVENTTYPE = '" + type.getValue() + "'");
      } else {
        System.out.println("Input did not match regular expression for date or month.");
      }
    } else if (type.getValue() != null) {
      // i.e. if something was selected from combo-box
      // declares an sql DELETE statement using the value from the combo-box
      String sqlStmt = "DELETE FROM Event WHERE EVENTTYPE = '" + type.getValue() + "'";
      // call function to kick off the rest of it
      DbUtil.dbExecuteUpdate(sqlStmt);
      /**FindBugs says that this "passes a non-constant String to an execute or addBatch method on
       an SQL statement", however, this and one other are the only complaints out of all my
       statements that are exactly like this one.*/
      System.out.println("DELETE FROM Event WHERE EVENTTYPE = '" + type.getValue() + "'");
    } else if (!(month.getText().trim().isEmpty())) {
      // i.e. if something was typed into month field
      // if month matches its regex format
      if (isMatchMonth.matches()) {
        // declares an sql DELETE statement using the value from the month text field
        String deleteMonth = "DELETE FROM Event WHERE MONTH(DATETIMEGROUP) = " + month.getText();
        // call function to kick off the rest of it
        DbUtil.dbExecuteUpdate(deleteMonth);
        System.out.println("DELETE FROM Event WHERE MONTH(DATETIMEGROUP) = " + month.getText());
      } else {
        System.out.println("Input did not match regular expression for month.");
      }
    } else if (!(date.getText().trim().isEmpty())) {
      // i.e. if something was typed into the date field
      // if date matches its regex format
      if (isMatchDate.matches()) {
        // declares an sql DELETE statement using the value from the date text field
        String deleteDate = "DELETE FROM Event WHERE DAY(DATETIMEGROUP) = " + date.getText();
        // call function to kick off the rest of it
        DbUtil.dbExecuteUpdate(deleteDate);
        System.out.println("DELETE FROM Event WHERE DAY(DATETIMEGROUP) = " + date.getText());
      } else {
        System.out.println("Input did not match regular expression for date.");
      }
    }

    // clear the fields on the gui after the database has been updated
    month.clear();
    date.clear();
    type.setValue(null);
  }

  /**
   * This is a method that will close the window and terminate the program. It is present on each
   * scene of the gui.
   *
   * @param event The standard event action listener.
   * @Author Damian Morgan
   */
  @FXML
  void closeWindow(ActionEvent event) throws IOException {
    Platform.exit();
  }
}