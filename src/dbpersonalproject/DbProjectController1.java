/***************************************************
 * File: DbProjectController1.java
 * Author: Marion Armbruster
 * Date: 9 November 2018
 ****************************************************/

package dbpersonalproject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

/**
 * This class is the controller for the first screen of the gui. It contains an action listener for
 * a button that connects to the database and displays in the console whether the connection was
 * successful or not. It also leads to the next screen of the gui. Check style says "Type name
 * 'DbProjectController1' must match pattern '^[A-Z][a-zA-Z0-9]*$'. (30:14) [TypeNameCheck]."
 */
public class DbProjectController1 {

  static final String DATABASE_URL = "jdbc:derby:lib//libraryproject";

  @FXML
  private Button connect;

  // for closing the window
  @FXML
  private Sphere logOff;

  /**
   * This method opens the connection to the database in a try-with-resources block and displays its
   * success or failure in the console window. A secondary action after the database has been opened
   * is to move to the new scene, linked by the action of clicking the button in the gui.
   *
   * @param event The standard event action listener.
   */
  @FXML
  void goToNext(ActionEvent event) throws IOException {

    try (
        Connection connection = DriverManager.getConnection(DATABASE_URL);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT* FROM EVENT")) {
      System.out.println("Database Connected.");
    } catch (SQLException sqlException) {
      System.out.println("SQL Exception.");
    } catch (IllegalStateException illegal) {
      System.out.println("Illegal State Exception.");
    }

    // @Author Damian Morgan - gave assistance with this code
    // also found here: https://stackoverflow.com/questions/16176701/switch-between-panes-in-javafx
    Parent secondScreen = FXMLLoader.load(getClass().getResource("dbPersonalProjectPage2.fxml"));
    Scene secondScene = new Scene(secondScreen);
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(secondScene);
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
  void closeWindow(ActionEvent event) throws IOException {
    // @Author Damian Morgan
    // This is supposed to close out the window as though it were the red "x" exit button.
    // FindBugs doesn't like system exit to be used at all.
    System.exit(0); // try Platform
  }
}