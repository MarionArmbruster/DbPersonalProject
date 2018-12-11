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
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * This class is the controller for the first screen of the gui. It contains an action listener for
 * a button that connects to the database and displays in the console whether the connection was
 * successful or not. It also leads to the next screen of the gui.
 */
public class DbProjectController1 {

  static final String DATABASE_URL = "jdbc:derby:lib//libraryproject";

  @FXML
  private Button connect;

  // for closing the window
  @FXML
  private Button logOff;

  /**
   * This method opens the connection to the database in a try-with-resources block and displays its
   * success or failure in the console window. A secondary action after the database has been opened
   * is to move to the new scene, linked by the action of clicking the button in the gui. In order
   * to use a database, a database url is necessary, along with a Connection object, a Statement
   * object, and a resultSet. Having these allow the use of sql statements and  to affect the
   * database.
   *
   * @param event The standard event action listener.
   */
  @FXML
  void goToNext(ActionEvent event) throws IOException {

    try (
        Connection connection = DriverManager.getConnection(DATABASE_URL);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM EVENT")) {
      System.out.println("Database Connected.");
    } catch (SQLException sqlException) {
      System.out.println("SQL Exception.");
    } catch (IllegalStateException illegal) {
      System.out.println("Illegal State Exception.");
    }

    // @Author Damian Morgan - gave assistance with this code
    Parent secondScreen = FXMLLoader.load(getClass().getResource("dbPersonalProjectPage2.fxml"));
    Scene secondScene = new Scene(secondScreen);
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(secondScene);
    window.show();
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