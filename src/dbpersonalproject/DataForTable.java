/***************************************************
 * File: DataForTable.java
 * Author: Marion Armbruster
 * Date: 17 November 2018
 ****************************************************/

package dbpersonalproject;

import java.util.Date;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * A class that is used to link a database with the table view contained in the program's gui. It
 * uses the property of the field(s), which is a value that represents the state of an instantiated
 * object. These can be retrieved and set in getter and setter methods. To be able to encapsulate
 * the value of a property in an object allows features to listen for changes in the property value
 * or to bind properties together. This class follows that set-up.
 */
public class DataForTable {

  // dataTypes and names of TableView columns
  private IntegerProperty key;
  private SimpleObjectProperty<Date> dateTimeGroup;
  private StringProperty eventType;

  /**
   * The default constructor that instantiates an object property of each field.
   */
  public DataForTable() {
    this.key = new SimpleIntegerProperty();
    this.dateTimeGroup = new SimpleObjectProperty<>();
    this.eventType = new SimpleStringProperty();
  }

  // unique key identifier
  public IntegerProperty keyProperty() {
    return key;
  }

  public int getKey() {
    return key.get();
  }

  public void setKey(int number) {
    this.key.set(number);
  }

  // system time stamp
  public SimpleObjectProperty<Date> dateTimeGroupProperty() {
    return dateTimeGroup;
  }

  public Object getDtg() {
    return dateTimeGroup.get();
  }

  public void setDate(Date date) {
    this.dateTimeGroup.set(date);
  }

  // event type - inbound or outbound
  public StringProperty eventType() {
    return eventType;
  }

  public String getEventT() {
    return eventType.get();
  }

  public void setEventT(String event) {
    this.eventType.set(event);
  }

}


