/***************************************************
 * File: DbProjectController2.java
 * Author: Marion Armbruster
 * Date: 4 October 2018
 ****************************************************/

package dbpersonalproject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

/**
 * This class is the controller for the second screen of the gui. It contains action listeners for
 * two buttons that each log the system information such as current time as well as the type of
 * event, determined by which button is pressed. This information is stored in the database. This
 * class also contains a method that leads to the next screen of the gui. Check style says "Type
 * name 'DbProjectController1' must match pattern '^[A-Z][a-zA-Z0-9]*$'. (30:14) [TypeNameCheck]."
 */
public class DbProjectController2 {

  static final String DATABASE_URL = "jdbc:derby:lib//libraryproject";

  @FXML
  private Button goToInEvent;

  @FXML
  private Button goToOutEvent;

  @FXML
  private Button search;

  @FXML
  private Label inEvent;

  @FXML
  private Label outEvent;

  // for closing the window
  @FXML
  private Sphere logOff;

  /**
   * This method logs an inbound event into the database when the button has been pushed. The sql
   * query statement has been hard-coded for now. It also displays a message below the button to
   * notify the user that an action has occurred.
   *
   * @param event The standard event action listener.
   */
  @FXML
  void logBeginEvent(ActionEvent event) {

    try {
      /* FindBugs says "Method may fail to clean up stream or resource."
       * This resource will closed after the operation is finished, by calling the disconnect
       * method in the DbUtil class*/
      Connection connection = DriverManager.getConnection(DATABASE_URL);
      Statement statement = connection.createStatement();
      statement
          .execute("INSERT INTO EVENT (DATETIMEGROUP, EVENTTYPE) VALUES (CURRENT_TIMESTAMP, 'in')");
    } catch (SQLException sqlException) {
      System.out.println("SQL Exception InEvent.");

    }
    inEvent.setText("   Event in has been logged.");
  }

  /**
   * This method logs an outbound event into the database when the button has been pushed. The sql
   * query statement has been hard-coded for now. It also displays a message below the button to
   * notify the user that an action has occurred.
   *
   * @param event The standard event action listener.
   */
  @FXML
  void logEndEvent(ActionEvent event) {

    try {
      /* FindBugs says "Method may fail to clean up stream or resource."
       * This resource will closed after the operation is finished, by calling the
       * disconnect method in the DbUtil class*/
      Connection connection = DriverManager.getConnection(DATABASE_URL);
      Statement statement = connection.createStatement();
      statement
          .execute(
              "INSERT INTO EVENT (DATETIMEGROUP, EVENTTYPE) VALUES (CURRENT_TIMESTAMP, 'out')");
    } catch (SQLException sqlException) {
      System.out.println("SQL Exception OutEvent.");

    }
    outEvent.setText("   Event out has been logged.");
  }

  /**
   * This method moves to the next scene, linked by the action of clicking the button in the gui.
   *
   * @param event The standard event action listener.
   * @Author Damian Morgan - gave assistance with this code. Also found here:
   *     https://stackoverflow.com/questions/16176701/switch-between-panes-in-javafx
   */
  @FXML
  void goToSearch(ActionEvent event) throws IOException {
    Parent thirdScreen = FXMLLoader.load(getClass().getResource("dbPersonalProjectPage3.fxml"));
    Scene thirdScene = new Scene(thirdScreen);
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(thirdScene);
    window.show();
  }

  /**
   * This is a method that will close the window and terminate the program. It is not yet
   * implemented as the functionality of the program will improve over time. It is present on each
   * scene of the gui.
   *
   * @param event The standard event action listener.
   */
  @FXML
  void closeWindow(MouseEvent event) throws IOException {
    // @Author Damian Morgan
    // This is supposed to close out the window as though it were the red "x" exit button.
    // FindBugs doesn't like system exit to be used at all.
    System.exit(0); // try Platform
  }
}
