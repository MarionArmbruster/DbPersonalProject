/***************************************************
 * File: DbProjectController2.java
 * Author: Marion Armbruster
 * Date: 4 October 2018
 ****************************************************/

package dbpersonalproject;

import java.io.IOException;
import java.sql.SQLException;
import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This class is the controller for the second screen of the gui. It contains action listeners for
 * two buttons that each log the system information such as current time as well as the type of
 * event, determined by which button is pressed. This information is stored in the database. This
 * class also contains a method that leads to the next screen of the gui.
 */
public class DbProjectController2 {

  // linked with the logBeginEvent method
  @FXML
  private Button goToInEvent;

  // linked with the logEndEvent method
  @FXML
  private Button goToOutEvent;

  // linked with the goToSearch method
  @FXML
  private Button search;

  @FXML
  private Label inEvent;

  @FXML
  private Label outEvent;

  // linked with the closeWindow method
  @FXML
  private Button logOff;

  @FXML
  private Rectangle rectangle1;

  @FXML
  private Rectangle rectangle2;

  /**
   * This method is a special initializer that is called first the moment the scene is called and
   * appears as a window. It calls the method for beginning the rectangle shape's animation
   * sequences.
   */

  public void initialize() {
    movingShape();
  }

  /**
   * This method logs an inbound event into the database when the button has been pushed. It also
   * displays a message below the button for a few seconds to notify the user that an action has
   * occurred. The message disappears after a few seconds.
   *
   * @param event The standard event action listener.
   */
  @FXML
  void logBeginEvent(ActionEvent event) throws SQLException {
    String inStmt = "INSERT INTO EVENT (DATETIMEGROUP, EVENTTYPE) VALUES (CURRENT_TIMESTAMP, 'in')";
    DbUtil.dbExecuteUpdate(inStmt);

    inEvent.setText("   Event in has been logged.");
    PauseTransition pause = new PauseTransition(Duration.seconds(2));

    // does not allow for the reuse of the parameter ActionEvent; therefore needs to be renamed
    pause.setOnFinished((ActionEvent event2) -> inEvent.setText(" "));
    pause.play();
  }

  /**
   * This method logs an outbound event into the database when the button has been pushed. It also
   * displays a message below the button for a few seconds to notify the user that an action has
   * occurred. The message disappears after a few seconds.
   *
   * @param event The standard event action listener.
   */
  @FXML
  void logEndEvent(ActionEvent event) throws SQLException {
    String outStmt = "INSERT INTO EVENT (DATETIMEGROUP, EVENTTYPE)"
        + " VALUES (CURRENT_TIMESTAMP, 'out')";
    DbUtil.dbExecuteUpdate(outStmt);

    outEvent.setText("   Event out has been logged.");
    PauseTransition pause = new PauseTransition(Duration.seconds(2));

    // does not allow for the reuse of the parameter ActionEvent; therefore needs to be renamed
    pause.setOnFinished((ActionEvent event2) -> outEvent.setText(" "));
    pause.play();
  }

  /**
   * This method moves to the next scene, linked by the action of clicking the button in the gui.
   *
   * @param event The standard event action listener.
   * @Author Damian Morgan - gave assistance with this code.
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
   * For the two rectangle shapes that were created in SceneBuilder, this method creates some
   * animations for them. As they are two different rectangle objects, they each have their own set
   * of the same transitions and play method so that they appear synchronized on the scene.
   */
  void movingShape() {
    // begins and ends the rotation on a corner of the rectangle, rather than a flat edge
    rectangle1.setRotate(45);
    rectangle2.setRotate(45);

    // sets the properties of the fill transition for the first rectangle, and then the second
    FillTransition fill = new FillTransition(Duration.seconds(5), rectangle1, Color.RED,
        Color.YELLOW);
    fill.setCycleCount(Animation.INDEFINITE);
    fill.setAutoReverse(true);
    fill.play();

    FillTransition fill2 = new FillTransition(Duration.seconds(5), rectangle2, Color.BLUE,
        Color.ORANGE);
    fill2.setCycleCount(Animation.INDEFINITE);
    fill2.setAutoReverse(true);
    fill2.play();

    // sets the properties of the rotate transition for the first rectangle, and then the second
    RotateTransition rotate = new RotateTransition(Duration.seconds(5));
    rotate.setByAngle(180);
    rotate.setCycleCount(Animation.INDEFINITE);
    rotate.setAutoReverse(true);

    RotateTransition rotate2 = new RotateTransition(Duration.seconds(5));
    rotate2.setByAngle(180);
    rotate2.setCycleCount(Animation.INDEFINITE);
    rotate2.setAutoReverse(true);

    // creates an object of the parallel transition for each rectangle and passes the objects of
    // the other transition so that they all run in tandem with each other
    ParallelTransition pt1 = new ParallelTransition(rectangle1, fill, rotate);
    ParallelTransition pt2 = new ParallelTransition(rectangle2, fill2, rotate2);

    // play the animations
    pt1.play();
    pt2.play();
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
