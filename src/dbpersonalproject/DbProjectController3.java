/***************************************************
 * File: DbProjectController3.java
 * Author: Marion Armbruster
 * Date: 5 November 2018
 ****************************************************/

package dbpersonalproject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
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
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

/**
 * This class is the controller for the third screen of the gui. It contains action listener methods
 * for buttons that display the entirety of the database, and will eventually allow the user to
 * search the database with certain conditions, taken from user input. This class also contains a
 * method that leads back to the second screen of the gui should the user wish to add more events to
 * the database. Check style says "Type name 'DbProjectController1' must match pattern
 * '^[A-Z][a-zA-Z0-9]*$'. (30:14) [TypeNameCheck]."
 */
public class DbProjectController3 {

  //for goToHome action
  @FXML
  private Button home;

  @FXML
  private Button searchDatabase;

  @FXML
  private Button viewAll;

  // for closing the window
  @FXML
  private Sphere logOff;

  @FXML
  private TextField month;

  @FXML
  private TextField date;

  @FXML
  private ComboBox<?> type;

  @FXML
  private TableView dbTable;

  // class name and data type with column name -- for the table view in the gui
  @FXML
  private TableColumn<DataForTable, Integer> keyNumColumn;

  @FXML
  private TableColumn<DataForTable, Date> dateAndTimeColumn;

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
  }

  /**
   * This is a method that will close the window and terminate the program. It is not yet
   * implemented as the functionality of the program will improve over time. It is present on each
   * scene of the gui.
   *
   * @param event The standard event action listener.
   */
  @FXML
  void closeWindow(ActionEvent event) throws IOException {
    // @Author Damian Morgan
    // This is supposed to close out the window as though it were the red "x" exit button.
    // FindBugs doesn't like system exit to be used at all.
    System.exit(0); // try Platform
  }

  /**
   * This method moves back to the second scene, linked by the action of clicking the button in the
   * gui.
   *
   * @param event The standard event action listener.
   * @Author Damian Morgan - gave assistance with this code. Also found here:
   *     https://stackoverflow.com/questions/16176701/switch-between-panes-in-javafx
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
   * This method calls other function that gather the ResultSet of the database into an
   * ObservableList of the class type that links the database to the table view and then displays
   * the objects that were added to the ObservableList in the table view of the gui.
   *
   * @param event The standard event action listener.
   * @throws SQLException General exception in the event that data was not able to be retrieved
   *                      from the database.
   */
  @FXML
  private void fillTable(ActionEvent event) throws SQLException {
    try {
      // gets all table data information
      ObservableList<DataForTable> tableData = DataForTableDao.makeTableAppear();
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
   * on the query statement used.
   */
  //Populate data for TableView
  @FXML
  private void populateData(ObservableList<DataForTable> data) {
    //Set items to the database Table (the table view)
    dbTable.setItems(data);
  }

  /**
   * A method that has yet to be implemented. It's functionality will be to update the table view to
   * reflect a query statement given with specific conditions.
   *
   * @param event The standard event action listener.
   */
  @FXML
  void updateTableView(ActionEvent event) {

  }

}