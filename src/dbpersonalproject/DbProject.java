/***************************************************
 * File: DbProject.java
 * Author: Marion Armbruster
 * Date: 4 October 2018
 ****************************************************/

package dbpersonalproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The beginning of this database project. It contains the start method of JavaFX to set and begin
 * the gui, along with the main method needed by JavaFX. Check style says "Type name 'DbProject'
 * must match pattern '^[A-Z][a-zA-Z0-9]*$'."
 */
public class DbProject extends Application {

  /**
   * The standard start method for JavaFX program to create a gui window interface.
   *
   * @param primaryStage the stage that the window will be set on. Standard for JavaFX programs.
   * @throws Exception A general exception for anything that may throw one.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {

    Parent root = FXMLLoader.load(getClass().getResource("dbPersonalProjectPage1.fxml"));
    // window size set to be optimal for personal computer, may not be appropriate for other
    // resolutions on other personal computers
    Scene scene = new Scene(root, 725, 425);
    primaryStage.setTitle("Database Project");
    primaryStage.setScene(scene);

    // retrieved from Stack Overflow, this forces the window to not be resizable, up until such a
    // time that a more useful method is implemented.
    primaryStage.setResizable(false);

    primaryStage.show();
  }

  /**
   * The basic main method that "launches" the program.
   *
   * @param args an array of command-line arguments for the application
   */
  public static void main(String[] args) {
    launch(args);

  } // end main method
}
