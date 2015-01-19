/*
 * Data Access Object
 * - Open a database
 * - Retrieve/Create/Delete/Update appointments from table
 * - Close the database
 * 
 * @author Christopher Reid
 * @version 1.0
 */

package com.chrisreid.JAMProject.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chrisreid.JAMProject.data.AppointmentBean;

/**
 * AppointmentDAOImpl class
 * 
 * Phase 1
 * 
 * The implementation class for appointment database queries.
 * 
 * @author Christopher Reid 0934402
 */
public class AppointmentDAOImpl implements AppointmentDAO {

	private final Logger log = LoggerFactory.getLogger(this.getClass()
			.getName());
	private Properties props = new Properties();
	private String url;
	private String user;
	private String password;

	public AppointmentDAOImpl() throws IOException, NullPointerException {
		super();
		loadProperties("db.properties");
		log.debug("AppointmentDAOImpl instantiated");
	}

	/**
	 * Retrieve the properties file.
	 * 
	 * @param propertiesFileName
	 *            : Name of the properties file
	 * @throws IOException
	 *             : Error while reading the file
	 * @throws NullPointerException
	 *             : File not found
	 */
	private void loadProperties(String propertiesFileName) throws IOException,
			NullPointerException {

		// Throws NullPointerException if file is not found
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream stream = loader.getResourceAsStream(propertiesFileName);
		props.load(stream);

		// Load the properties into variables for making Connections
		url = props.getProperty("url");
		user = props.getProperty("user");
		password = props.getProperty("password");

		log.debug("Properties successfully loaded.");
	}

	/**
	 * Retrieves all the records for the Appointments table and returns the data
	 * as an ArrayList of AppointmentBean objects
	 * 
	 * @return The ArrayList
	 * 
	 */
	@Override
	public ArrayList<AppointmentBean> getAllRecords() throws SQLException {

		ArrayList<AppointmentBean> apps = new ArrayList<>();
		String preparedSQL = "SELECT * FROM appointments ORDER BY starttime";

		// Using Java 1.7 try with resources
		try (Connection connection = DriverManager.getConnection(url, user,
				password);
				PreparedStatement ps = connection.prepareStatement(preparedSQL);
				ResultSet resultSet = ps.executeQuery()) {

			while (resultSet.next()) {
				AppointmentBean ab = new AppointmentBean();
				ab.setTitle(resultSet.getString("TITLE"));
				ab.setLocation(resultSet.getString("LOCATION"));
				ab.setStartTime((Timestamp) resultSet.getObject("STARTTIME"));
				ab.setEndTime((Timestamp) resultSet.getObject("ENDTIME"));
				ab.setDetails(resultSet.getString("DETAILS"));
				ab.setAllDay(resultSet.getBoolean("ALLDAY"));
				ab.setAlarmReminder(resultSet.getBoolean("ALARMREMINDER"));
				ab.setCreated((Timestamp) resultSet.getObject("CREATED"));
				ab.setId(resultSet.getInt("ID"));

				apps.add(ab);
			}
		} catch (SQLException sqlex) {
			// Log the exception
			log.error("connect failed", sqlex);
			// Re-throw the exception
			throw sqlex;
		}
		log.debug("Total number of appointments [getAllRecords]=" + apps.size());
		return apps;
	}

	/**
	 * Retrieves all the records from the Appointments table that match the
	 * supplied parameters and returns the data as an ArrayList of ContactBean
	 * objects
	 * 
	 * @return The ArrayList
	 */
	@Override
	public ArrayList<AppointmentBean> searchByForm(
			AppointmentBean appointmentBean) throws SQLException {

		ArrayList<AppointmentBean> apps = new ArrayList<>();

		// Build SQL string
		String preparedSQL = createSQLStatement(appointmentBean);

		// Using Java 1.7 try with resources
		try (Connection connection = DriverManager.getConnection(url, user,
				password);
				PreparedStatement ps = connection.prepareStatement(preparedSQL);) {

			// Set the appropriate PreparedStatement variables
			setPreparedStatementVariables(appointmentBean, ps);

			try (ResultSet resultSet = ps.executeQuery();) {

				while (resultSet.next()) {
					AppointmentBean ab = new AppointmentBean();
					ab.setTitle(resultSet.getString("TITLE"));
					ab.setLocation(resultSet.getString("LOCATION"));
					ab.setStartTime(resultSet.getTimestamp("STARTTIME"));
					ab.setEndTime(resultSet.getTimestamp("ENDTIME"));
					ab.setDetails(resultSet.getString("DETAILS"));
					ab.setAllDay(resultSet.getBoolean("ALLDAY"));
					ab.setAlarmReminder(resultSet.getBoolean("ALARMREMINDER"));
					ab.setCreated(resultSet.getTimestamp("CREATED"));
					ab.setId(resultSet.getInt("ID"));

					apps.add(ab);
				}

			} catch (SQLException sqlex) {
				// Log the exception
				log.error("ResultSet failed", sqlex);
				// Re-throw the exception
				throw sqlex;
			}

		} catch (SQLException sqlex) {
			// Log the exception
			log.error("Connect failed", sqlex);
			// Re-throw the exception
			throw sqlex;
		}
		log.debug("Total number of appointments [searchByForm]=" + apps.size());
		return apps;
	}

	/**
	 * Retrieves all the records in the Appointments table that fall between the
	 * specified time.
	 * 
	 * @param start
	 * @param end
	 * @return The ArrayList
	 */
	@Override
	public ArrayList<AppointmentBean> searchByRange(Timestamp start,
			Timestamp end) throws SQLException {

		// Check that each date is not null
		if (start == null || end == null) {
			return null;
		}

		// Swap the dates if end comes before start
		if (start.compareTo(end) == 1) {
			Timestamp t = start;
			start = end;
			end = t;
		}

		ArrayList<AppointmentBean> apps = new ArrayList<>();

		String preparedSQL = "SELECT * FROM appointments WHERE "
				+ "endtime >= ? AND starttime <= ? ORDER BY starttime";

		// Using Java 1.7 try with resources
		try (Connection connection = DriverManager.getConnection(url, user,
				password);
				PreparedStatement ps = connection.prepareStatement(preparedSQL);) {

			ps.setTimestamp(1, start);
			ps.setTimestamp(2, end);

			try (ResultSet resultSet = ps.executeQuery();) {

				while (resultSet.next()) {
					AppointmentBean ab = new AppointmentBean();
					ab.setTitle(resultSet.getString("TITLE"));
					ab.setLocation(resultSet.getString("LOCATION"));
					ab.setStartTime(resultSet.getTimestamp("STARTTIME"));
					ab.setEndTime(resultSet.getTimestamp("ENDTIME"));
					ab.setDetails(resultSet.getString("DETAILS"));
					ab.setAllDay(resultSet.getBoolean("ALLDAY"));
					ab.setAlarmReminder(resultSet.getBoolean("ALARMREMINDER"));
					ab.setCreated(resultSet.getTimestamp("CREATED"));
					ab.setId(resultSet.getInt("ID"));

					apps.add(ab);
				}
			} catch (SQLException sqlex) {
				// Log the exception
				log.error("ResultSet failed", sqlex);
				// Re-throw the exception
				throw sqlex;
			}
		} catch (SQLException sqlex) {
			// Log the exception
			log.error("Connect failed", sqlex);
			// Re-throw the exception
			throw sqlex;
		}
		log.debug("Total number of appointments [searchByRange]=" + apps.size());
		return apps;
	}

	/**
	 * Deletes an appointment from the Appointments table that matches the id.
	 * 
	 * @param appointmentBean
	 */
	@Override
	public int delete(AppointmentBean appointmentBean) throws SQLException {

		String preparedSQL = "DELETE FROM appointments WHERE id = ?";
		int affected;

		// Using Java 1.7 try with resources
		try (Connection connection = DriverManager.getConnection(url, user,
				password);
				PreparedStatement ps = connection.prepareStatement(preparedSQL)) {

			ps.setInt(1, appointmentBean.getId());
			affected = ps.executeUpdate();

		} catch (SQLException sqlex) {
			// Log the exception
			log.error("Connect failed", sqlex);
			// Re-throw the exception
			throw sqlex;
		}
		log.debug("Total number of appointments affected [delete]=" + affected);
		return affected;
	}

	/**
	 * Inserts an appointment into the Appointments table by using a
	 * AppointmentBean object.
	 * 
	 * @param appointmentBean
	 */
	@Override
	public int insert(AppointmentBean appointmentBean) throws SQLException {

		String preparedSQL = "INSERT INTO appointments"
				+ "(title, location, starttime, endtime, "
				+ "details, allday, alarmreminder, created)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ? )";
		int affected;

		// Using Java 1.7 try with resources
		try (Connection connection = DriverManager.getConnection(url, user,
				password);
				PreparedStatement ps = connection.prepareStatement(preparedSQL)) {

			ps.setString(1, appointmentBean.getTitle());
			ps.setString(2, appointmentBean.getLocation());
			ps.setTimestamp(3, appointmentBean.getStartTime());
			ps.setTimestamp(4, appointmentBean.getEndTime());
			ps.setString(5, appointmentBean.getDetails());
			ps.setBoolean(6, appointmentBean.isAllDay());
			ps.setBoolean(7, appointmentBean.isAlarmReminder());
			ps.setTimestamp(8, appointmentBean.getCreated());

			affected = ps.executeUpdate();

		} catch (SQLException sqlex) {
			// Log the exception
			log.error("Connect failed", sqlex);
			// Re-throw the exception
			throw sqlex;
		}
		log.debug("Total number of appointments affected [insert]=" + affected);
		return affected;
	}

	/**
	 * Updates an appointment into the Appointments table by using an
	 * AppointmentBean.
	 * 
	 * @param appointmentBean
	 */
	@Override
	public int update(AppointmentBean appointmentBean) throws SQLException {

		String preparedSQL = "UPDATE appointments SET "
				+ "title = ?, location = ?, starttime = ?, "
				+ "endtime = ?, details = ?, allday = ?, "
				+ "alarmreminder = ?, created = ? WHERE ID = ?";
		int affected;

		// Using Java 1.7 try with resources
		try (Connection connection = DriverManager.getConnection(url, user,
				password);
				PreparedStatement ps = connection.prepareStatement(preparedSQL)) {

			ps.setString(1, appointmentBean.getTitle());
			ps.setString(2, appointmentBean.getLocation());
			ps.setTimestamp(3, appointmentBean.getStartTime());
			ps.setTimestamp(4, appointmentBean.getEndTime());
			ps.setString(5, appointmentBean.getDetails());
			ps.setBoolean(6, appointmentBean.isAllDay());
			ps.setBoolean(7, appointmentBean.isAlarmReminder());
			ps.setTimestamp(8, appointmentBean.getCreated());
			ps.setInt(9, appointmentBean.getId());

			affected = ps.executeUpdate();

		} catch (SQLException sqlex) {
			// Log the exception
			log.error("Connect failed", sqlex);
			// Re-throw the exception
			throw sqlex;
		}
		log.debug("Total number of appointments affected [update]=" + affected);
		return affected;
	}

	private String createSQLStatement(AppointmentBean appointmentBean) {
		// Build SQL string
		String preparedSQL = "SELECT * FROM appointments WHERE";

		if (appointmentBean.getTitle() != "") {
			preparedSQL += " title = ? AND ";
		}
		if (appointmentBean.getLocation() != "") {
			preparedSQL += " location = ? AND ";
		}
		if (appointmentBean.getStartTime() != null) {
			preparedSQL += " starttime >= ? AND ";
		}
		if (appointmentBean.getEndTime() != null) {
			preparedSQL += " endtime <= ? AND ";
		}
		if (appointmentBean.getDetails() != "") {
			preparedSQL += " details = ? AND ";
		}
		if (appointmentBean.isAllDay() != false) {
			preparedSQL += " allday = ? AND ";
		}
		if (appointmentBean.isAlarmReminder() != false) {
			preparedSQL += " alarmReminder = ? AND ";
		}
		// Take appropriate substring
		preparedSQL = preparedSQL.substring(0, preparedSQL.length() - 5);
		
		preparedSQL += " ORDER BY starttime ";

		return preparedSQL;
	}

	private void setPreparedStatementVariables(AppointmentBean appointmentBean,
			PreparedStatement ps) throws SQLException {

		int counter = 1;
		if (appointmentBean.getTitle() != "") {
			ps.setString(counter, appointmentBean.getTitle());
			counter++;
		}
		if (appointmentBean.getLocation() != "") {
			ps.setString(counter, appointmentBean.getLocation());
			counter++;
		}
		if (appointmentBean.getStartTime() != null) {
			ps.setTimestamp(counter, appointmentBean.getStartTime());
			counter++;
		}
		if (appointmentBean.getEndTime() != null) {
			ps.setTimestamp(counter, appointmentBean.getEndTime());
			counter++;
		}
		if (appointmentBean.getDetails() != "") {
			ps.setString(counter, appointmentBean.getDetails());
			counter++;
		}
		if (appointmentBean.isAllDay() != false) {
			ps.setBoolean(counter, appointmentBean.isAllDay());
			counter++;
		}
		if (appointmentBean.isAlarmReminder() != false) {
			ps.setBoolean(counter, appointmentBean.isAlarmReminder());
			counter++;
		}
	}

}
