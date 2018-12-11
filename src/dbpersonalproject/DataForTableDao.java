/***************************************************
 * File: DataForTableDao.java
 * Author: Marion Armbruster
 * Date: 17 November 2018
 *
 * Modified code snippets from ONUR BASKIRT.
 * https://www.swtestacademy.com/database-operations-javafx/
 ****************************************************/

package dbpersonalproject;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A "Data Access Object" class that acts as an in-between for the database and JavaFX (with gui).
 * It allows for modularity and handles any possible related database operations that are needed and
 * "informs" the JavaFX classes of any changes made to the database. Check style says "Abbreviation
 * in name 'DataForTableDao' must contain no more than '2' consecutive capital letters. However, as
 * this code was supplied by another, it was deemed fitting to maintain the convention for a data
 * access object class file.
 */

public class DataForTableDao {

  /**
   * This method is responsible for taking the data from the database and displaying it in the table
   * view of the gui program.
   *
   * @return The object of the ObservableList that has been filled with the database's data.
   * @throws SQLException if the select fails to select from the database
   */
  public static ObservableList<DataForTable> makeTableAppear(String sql) throws SQLException {

    // an sql statement that is passed in from the calling method
    String selectStmt = sql;

    // try to execute the sql statement
    try {
      // gets the ResultSet from the dbExecuteQuery method
      ResultSet rsData = DbUtil.dbExecuteQuery(selectStmt);

      // sends ResultSet to the getOList method and get DataForTable object
      ObservableList<DataForTable> list = getOList(rsData);

      // returns the data table object
      return list;
    } catch (SQLException e) {
      System.out.println("SQL select operation has been failed: " + e);
      // return exception
      throw e;
    }
  }

  /**
   * This method performs the actual Select * from Event operation. It has the data type of the
   * ObservableList of the class type that contains the properties of the fields used in the table.
   * It also uses the ResultSet of the database to loop through the data and pull it into the class
   * object.
   *
   * @param rs the ResultSet of the database
   * @return class object that has the data from the database (the ObservableList)
   * @throws SQLException A generic sql exception in the event that the sql statement fails i.e.
   *                      the ResultSet is bad.
   */
  private static ObservableList<DataForTable> getOList(ResultSet rs) throws SQLException {

    // declares a observable List which comprises of the DataForTable objects
    ObservableList<DataForTable> tableData = FXCollections.observableArrayList();

    // while there is still an object in the ResultSet, the class object calls the setter in the
    // class to receive the object and data-type from the database table from the column specified
    while (rs.next()) {
      DataForTable data = new DataForTable();
      data.setKey(rs.getInt("KeyID"));
      data.setDate(rs.getTimestamp("DateTimeGroup"));
      data.setEventT(rs.getString("EventType"));
      // Add data to the ObservableList
      tableData.add(data);
    }
    // return the table data (ObservableList of DataForTable, now filled with the data from
    // the database table)
    return tableData;
  }
}
