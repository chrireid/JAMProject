package com.chrisreid.JAMProject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chrisreid.JAMProject.data.AppointmentBean;
import com.chrisreid.JAMProject.persistence.AppointmentDAOImpl;

/**
 * AppointmentTests class
 * 
 * Phase 1
 * 
 * This runs the test cases for the appointment database queries.
 * 
 * @author Christopher Reid 0934402
 */
public class AppointmentTests {

	private AppointmentDAOImpl app;
	private final Logger log = LoggerFactory.getLogger(this.getClass()
			.getName());

	/**
	 * This method executes before each test is run. It drops the table, creates
	 * the table and fills the table with records for destructive testing.
	 * 
	 * @throws SQLException
	 */
	@Before
	public void recreateAndPopulateAppointmentsTable() throws SQLException {

		final String url = "jdbc:mysql://waldo2.dawsoncollege.qc.ca:3306/d934402";
		final String user = "D934402";
		final String password = "mploadia";

		// Drop the table
		String sql = "DROP TABLE IF EXISTS appointments";
		try (Connection connection = DriverManager.getConnection(url, user,
				password);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.execute();
		}

		// Create the table
		sql = "CREATE TABLE appointments ( "
				+ "ID int(11) zerofill NOT NULL auto_increment, "
				+ "TITLE varchar(35) NOT NULL default '', "
				+ "LOCATION varchar(35) NOT NULL default '', "
				+ "STARTTIME datetime, " + "ENDTIME datetime, "
				+ "DETAILS varchar(250) NOT NULL default '', "
				+ "ALLDAY boolean NOT NULL default false, "
				+ "ALARMREMINDER boolean NOT NULL default false, "
				+ "created TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
				+ "PRIMARY KEY  (ID) " + ") ENGINE=InnoDB; ";

		try (Connection connection = DriverManager.getConnection(url, user,
				password);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.execute();
		}

		// Insert record into table
		sql = "INSERT INTO appointments (TITLE, LOCATION, STARTTIME, "
				+ "ENDTIME, DETAILS, ALLDAY, ALARMREMINDER) VALUES "
				+ "('destructive testing', 'eclipse', '2013-01-01 00:00:01', "
				+ "'2013-01-02 23:59:59', 'inserting', 1, 1), "
				+ "('destructive weekly testing 1', 'week1', '2013-11-11 11:11:11', "
				+ "'2013-11-11 12:12:12', 'testing', 1, 1), "
				+ "('destructive weekly testing 2', 'week1', '2013-11-12 11:11:11', "
				+ "'2013-11-12 12:12:12', 'testing', 0, 0), "
				+ "('destructive weekly testing 3', 'week1', '2013-11-13 11:11:11', "
				+ "'2013-11-13 12:12:12', 'testing', 0, 1), "
				+ "('destructive monthly testing', 'same month', '2013-11-01 03:03:03', "
				+ "'2013-11-21 12:12:12', 'testing', 1, 1), "
				+ "('destructive yearly testing 1', 'year1', '2012-11-11 11:11:11', "
				+ "'2012-11-11 12:12:12', 'testing', 1, 1), "
				+ "('destructive yearly testing 2', 'year2', '2011-11-11 11:11:11', "
				+ "'2013-11-11 12:12:12', 'testing', 1, 1) ";

		try (Connection connection = DriverManager.getConnection(url, user,
				password);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.executeUpdate();
		}

		// Instantiate AppointmentDAOImpl class
		try {
			app = new AppointmentDAOImpl();
		} catch (IOException e) {
			log.error("IOException instantiating AppointmentDAOImpl");
		}

	}

	/**
	 * Tests insert method with a null value.
	 * 
	 * @throws SQLException
	 */
	@Test(expected = NullPointerException.class)
	public void testInsertAppointment_Null() throws SQLException {
		app.insert(null);
		fail("NullPointerException not thrown when expected");

	}

	/**
	 * Tests update method with a null value.
	 * 
	 * @throws SQLException
	 */
	@Test(expected = NullPointerException.class)
	public void testUpdateAppointment_Null() throws SQLException {
		app.update(null);
		fail("NullPointerException not thrown when expected");

	}

	/**
	 * Tests delete method with a null value.
	 * 
	 * @throws SQLException
	 */
	@Test(expected = NullPointerException.class)
	public void testDeleteAppointment_Null() throws SQLException {
		app.delete(null);
		fail("NullPointerException not thrown when expected");

	}

	/**
	 * Tests insert method with a valid bean.
	 * 
	 * @throws SQLException
	 */
	@Test(timeout = 1000)
	public void testInsertAppointment_Valid() throws SQLException {

		AppointmentBean ab = new AppointmentBean();
		ab.setTitle("Testing an insert");
		ab.setStartTime(new Timestamp(new Date().getTime()));
		ab.setEndTime(new Timestamp(new Date().getTime()));
		ab.setDetails("JUnit testing rocks!");

		int result = app.insert(ab);
		assertEquals("1 row affected: ", 1, result);
	}

	/**
	 * Tests update method with a valid bean.
	 * 
	 * @throws SQLException
	 */
	@Test(timeout = 1000)
	public void testUpdateAppointment_Valid() throws SQLException {

		AppointmentBean ab = new AppointmentBean();
		ab.setTitle("Testing an insert");
		ab.setStartTime(new Timestamp(new Date().getTime()));
		ab.setEndTime(new Timestamp(new Date().getTime()));
		ab.setDetails("JUnit testing rocks!");
		ab.setId(1);

		int result = app.update(ab);
		assertEquals("1 row affected: ", 1, result);
	}

	/**
	 * Tests delete method with a valid bean.
	 * 
	 * @throws SQLException
	 */
	@Test(timeout = 1000)
	public void testDeleteAppointment_Valid() throws SQLException {

		AppointmentBean ab = new AppointmentBean();
		ab.setId(1);

		int result = app.delete(ab);
		assertEquals("1 row affected: ", 1, result);
	}

	/**
	 * Tests update method with a valid bean.
	 * 
	 * @throws SQLException
	 */
	@Test(timeout = 1000)
	public void testUpdateAppointment_Invalid() throws SQLException {

		AppointmentBean ab = new AppointmentBean();
		ab.setTitle("Testing an insert with invalid data");
		ab.setStartTime(new Timestamp(new Date().getTime()));
		ab.setEndTime(new Timestamp(new Date().getTime()));
		ab.setDetails("JUnit testing rocks!");
		ab.setId(37);

		int result = app.update(ab);
		assertEquals("0 rows affected: ", 0, result);
	}

	/**
	 * Tests delete method with a valid bean.
	 * 
	 * @throws SQLException
	 */
	@Test(timeout = 1000)
	public void testDeleteAppointment_Invalid() throws SQLException {

		AppointmentBean ab = new AppointmentBean();
		ab.setId(37);

		int result = app.delete(ab);
		assertEquals("0 rows affected: ", 0, result);
	}

	/**
	 * Test for
	 * {@link com.chrisreid.JAMProject.persistence.AppointmentDAOImpl#getAllRecords()}
	 * 
	 * @throws SQLException
	 */
	@Test(timeout = 1000)
	public void testGetAllRecords() throws SQLException {
		ArrayList<AppointmentBean> ab = app.getAllRecords();
		assertEquals("7 records", 7, ab.size());
	}

	/**
	 * Tests for
	 * {@link com.chrisreid.JAMProject.persistence.AppointmentDAOImpl#searchByForm(com.chrisreid.JAMProject.data.AppointmentBean)}
	 * 
	 * @throws SQLException
	 */
	@Test(timeout = 1000)
	public void testSearchByForm_Single() throws SQLException {

		AppointmentBean bean = new AppointmentBean();
		bean.setTitle("destructive testing");
		bean.setLocation("eclipse");
		bean.setDetails("inserting");
		bean.setAllDay(true);
		bean.setAlarmReminder(true);

		ArrayList<AppointmentBean> ab = app.searchByForm(bean);

		if (ab.size() != 1) {
			fail("There should only be one bean that matches these parameters");
		} else {
			assertEquals("bean.getId()=1", 1, ab.get(0).getId());
		}
	}

	@Test(timeout = 1000)
	public void testSearchByForm_Multiple() throws SQLException {

		AppointmentBean bean = new AppointmentBean();
		bean.setLocation("week1");

		ArrayList<AppointmentBean> ab = app.searchByForm(bean);

		assertEquals("3 records", 3, ab.size());
	}

	@Test(timeout = 1000)
	public void testSearchByForm_None() throws SQLException {

		AppointmentBean bean = new AppointmentBean();
		bean.setTitle("This should not exist");

		ArrayList<AppointmentBean> ab = app.searchByForm(bean);

		assertEquals("0 records", 0, ab.size());
	}

	@Test(expected = NullPointerException.class, timeout = 1000)
	public void testSearchByForm_NullPointer() throws SQLException {

		AppointmentBean bean = null;

		ArrayList<AppointmentBean> ap = app.searchByForm(bean);
		fail("NullPointerException not thrown when expected");
	}

	/**
	 * Tests for
	 * {@link com.chrisreid.JAMProject.persistence.AppointmentDAOImpl#searchByRange(java.util.Calendar, java.util.Calendar)}
	 * 
	 * @throws SQLException
	 */
	@Test(timeout = 1000)
	public void testSearchByRange_Day() throws SQLException {

		Calendar c = Calendar.getInstance();
		Calendar d = Calendar.getInstance();

		// Set the search range to 2013.11.11 - 2013.11.11
		c.set(Calendar.YEAR, 2013);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.DATE, 11);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		d.set(Calendar.YEAR, 2013);
		d.set(Calendar.MONTH, 10);
		d.set(Calendar.DATE, 11);
		d.set(Calendar.HOUR_OF_DAY, 23);
		d.set(Calendar.MINUTE, 59);
		d.set(Calendar.SECOND, 59);
		d.set(Calendar.MILLISECOND, 999);

		Timestamp start = new Timestamp(c.getTimeInMillis());
		Timestamp end = new Timestamp(d.getTimeInMillis());

		ArrayList<AppointmentBean> ab = app.searchByRange(start, end);

		assertEquals("3 records", 3, ab.size());
	}

	@Test(timeout = 1000)
	public void successfulSearchByRange_Week() throws SQLException {

		Calendar c = Calendar.getInstance();
		Calendar d = Calendar.getInstance();

		// Set the search range to 2013.11.10 - 2013.11.16
		c.set(Calendar.YEAR, 2013);
		c.set(Calendar.MONTH, 10);
		c.set(Calendar.DATE, 10);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		d.set(Calendar.YEAR, 2013);
		d.set(Calendar.MONTH, 10);
		d.set(Calendar.DATE, 16);
		d.set(Calendar.HOUR_OF_DAY, 23);
		d.set(Calendar.MINUTE, 59);
		d.set(Calendar.SECOND, 59);
		d.set(Calendar.MILLISECOND, 999);

		Timestamp start = new Timestamp(c.getTimeInMillis());
		Timestamp end = new Timestamp(d.getTimeInMillis());

		ArrayList<AppointmentBean> ab = app.searchByRange(start, end);

		assertEquals("5 records", 5, ab.size());
	}

	@Test(timeout = 1000)
	public void successfulSearchByRange_Month() throws SQLException {

		Calendar c = Calendar.getInstance();
		Calendar d = Calendar.getInstance();

		// Set the search range to 2011.12.01 - 2011.12.31
		c.set(Calendar.YEAR, 2011);
		c.set(Calendar.MONTH, 11);
		c.set(Calendar.DATE, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		d.set(Calendar.YEAR, 2011);
		d.set(Calendar.MONTH, 11);
		d.set(Calendar.DATE, 31);
		d.set(Calendar.HOUR_OF_DAY, 23);
		d.set(Calendar.MINUTE, 59);
		d.set(Calendar.SECOND, 59);
		d.set(Calendar.MILLISECOND, 999);

		Timestamp start = new Timestamp(c.getTimeInMillis());
		Timestamp end = new Timestamp(d.getTimeInMillis());

		ArrayList<AppointmentBean> ab = app.searchByRange(start, end);

		assertEquals("1 record", 1, ab.size());
	}

	@Test(timeout = 1000)
	public void successfulSearchByRange_Year() throws SQLException {

		Calendar c = Calendar.getInstance();
		Calendar d = Calendar.getInstance();

		// Set the search range to 2011.01.01 - 2011.12.31
		c.set(Calendar.YEAR, 2011);
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DATE, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		d.set(Calendar.YEAR, 2011);
		d.set(Calendar.MONTH, 11);
		d.set(Calendar.DATE, 31);
		d.set(Calendar.HOUR_OF_DAY, 23);
		d.set(Calendar.MINUTE, 59);
		d.set(Calendar.SECOND, 59);
		d.set(Calendar.MILLISECOND, 999);

		Timestamp start = new Timestamp(c.getTimeInMillis());
		Timestamp end = new Timestamp(d.getTimeInMillis());

		ArrayList<AppointmentBean> ab = app.searchByRange(start, end);

		assertEquals("1 record", 1, ab.size());
	}

}
