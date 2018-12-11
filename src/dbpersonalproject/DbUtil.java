/***************************************************
 * File: DbUtil.java
 * Author: Marion Armbruster
 * Date: 17 November 2018
 ****************************************************/

package dbpersonalproject;

import com.sun.rowset.CachedRowSetImpl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A class that handles the connection, disconnection, and query executions of the database.
 * Modified code from ONUR BASKIRT. https://www.swtestacademy.com/database-operations-javafx/
 */
public class DbUtil {

  // the connection, at class-level.
  private static Connection connection = null;

  // class-level String that contains the url of where to find the database information
  static final String DATABASE_URL = "jdbc:derby:lib//libraryproject";

  /**
   * The method of which the only purpose is to successfully connect to the database.
   *
   * @throws SQLException if the connection failed for any reason.
   */
  public static void dbConnect() throws SQLException {

    // establish the Connection to the database using the connection String with a standard
    // try-catch
    try {
      connection = DriverManager.getConnection(DATABASE_URL);
    } catch (SQLException e) {
      System.out.println("Connection Failed! Check output console" + e);
      e.printStackTrace();
      throw e;
    }
  }

  /**
   * The method of which the only purpose is to successfully disconnect from, or close, the
   * database.
   *
   * @throws SQLException if the connection to the database was not closed for any reason.
   */
  public static void dbDisconnect() throws SQLException {
    try {
      if (connection != null && !connection.isClosed()) {
        connection.close();
      }
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * This is the method that executes any database query operation passed into it.
   *
   * @param queryStmt A String that holds whichever query statement is to be performed on the
   *                  database.
   * @return Returns the CachedRowSet which holds the ResultSet.
   * @throws SQLException In the event that the query has nothing to act upon in the database or
   *                      if something else goes wrong.
   */
  public static ResultSet dbExecuteQuery(String queryStmt)
      throws SQLException {

    // declare statement, resultSet and CachedResultSet as null
    Statement stmt = null;
    ResultSet resultSet = null;
    CachedRowSetImpl crs = null;
    try {
      // connect to the database
      dbConnect();
      System.out.println("Select statement: " + queryStmt + "\n");

      /* create a statement
       * FindBugs says "Method may fail to clean up stream or resource on checked exception."
       * This resource will close after the operation is finished, by calling the disconnect
       * method.*/
      stmt = connection.createStatement();

      // execute the selected (query) operation that was passed in
      resultSet = stmt.executeQuery(queryStmt);

      /* In order to prevent "java.sql.SQLRecoverableException: Closed Connection: next" error,
         the CachedRowSet is being used.*/
      crs = new CachedRowSetImpl();
      crs.populate(resultSet);
    } catch (SQLException e) {
      System.out.println("Problem occurred at executeQuery operation : " + e);
      throw e;
    } finally {
      if (resultSet != null) {
        // closes the resultSet
        resultSet.close();
      }
      if (stmt != null) {
        // closes Statement
        stmt.close();
      }
      // calls the method the close the connection to the database
      dbDisconnect();
    }
    // return CachedRowSet
    return crs;
  }

  /**
   * This method performs an update operation on the database; this could be an update to a record
   * already in the database, to insert (or add) to the database, or to delete a record from the
   * database.
   *
   * @param sqlStmt A String that holds whichever type-query statement is to be performed on the
   *                database.
   * @throws SQLException In the event that the query has nothing to act upon in the database or if
   *                      something else goes wrong.
   */
  public static void dbExecuteUpdate(String sqlStmt) throws SQLException {
    // declares statement as null
    Statement stmt = null;
    try {
      // connects to the database
      dbConnect();

      // create  the statement
      stmt = connection.createStatement();

      // uses the executeUpdate command with the sql statement that was passed in
      stmt.executeUpdate(sqlStmt);
    } catch (SQLException e) {
      System.out.println("Problem occurred at executeUpdate operation : " + e);
      throw e;
    } finally {
      if (stmt != null) {
        // close the statement
        stmt.close();
      }
      // closes the connection to the database
      dbDisconnect();
    }
  }
}
