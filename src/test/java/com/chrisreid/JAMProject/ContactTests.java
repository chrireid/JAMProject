package com.chrisreid.JAMProject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chrisreid.JAMProject.data.AppointmentBean;
import com.chrisreid.JAMProject.data.ContactBean;
import com.chrisreid.JAMProject.persistence.AppointmentDAOImpl;
import com.chrisreid.JAMProject.persistence.ContactDAOImpl;

/**
 * ContactTests class
 * 
 * Phase 1
 * 
 * This runs the test cases for the contact database queries.
 * 
 * @author Christopher Reid 0934402
 */
public class ContactTests {

	private ContactDAOImpl con;
	private final Logger log = LoggerFactory.getLogger(this.getClass()
			.getName());

	/**
	 * This method executes before each test is run. It drops the table, creates
	 * the table and fills the table with records for destructive testing.
	 * 
	 * @throws SQLException
	 */
	@Before
	public void recreateAndPopulateContactsTable() throws SQLException {

		final String url = "jdbc:mysql://waldo2.dawsoncollege.qc.ca:3306/d934402";
		final String user = "D934402";
		final String password = "mploadia";

		// Drop the table
		String sql = "DROP TABLE IF EXISTS contacts";
		try (Connection connection = DriverManager.getConnection(url, user,
				password);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.execute();
		}

		// Create the table
		sql = "CREATE TABLE contacts ( "
				+ "ID int(11) zerofill NOT NULL auto_increment, "
				+ "LASTNAME varchar(35) NOT NULL default '', "
				+ "FIRSTNAME varchar(35) NOT NULL default '', "
				+ "MIDDLENAME varchar(35) NOT NULL default '', "
				+ "COMPANYNAME varchar(35) NOT NULL default '', "
				+ "ADDRESS1 varchar(35) NOT NULL default '', "
				+ "ADDRESS2 varchar(35) NOT NULL default '', "
				+ "ADDRESS3 varchar(35) NOT NULL default '', "
				+ "CITY varchar(30) NOT NULL default '', "
				+ "PROVINCE varchar(5) NOT NULL default '', "
				+ "POSTALCODE varchar(9) NOT NULL default '', "
				+ "COUNTRY varchar(48) NOT NULL default '', "
				+ "PHONENUMBER varchar(15) NOT NULL default '', "
				+ "CELLNUMBER varchar(15) NOT NULL default '', "
				+ "FAXNUMBER varchar(15) NOT NULL default '', "
				+ "EMAIL varchar(50) NOT NULL default '', "
				+ "PRIMARY KEY  (ID) " + ") ENGINE=InnoDB; ";

		try (Connection connection = DriverManager.getConnection(url, user,
				password);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.execute();
		}

		// Insert record into table
		sql = "INSERT INTO contacts (LASTNAME, FIRSTNAME, MIDDLENAME, COMPANYNAME, "
				+ "ADDRESS1, ADDRESS2, ADDRESS3, CITY, PROVINCE, POSTALCODE, COUNTRY, "
				+ "PHONENUMBER, CELLNUMBER, FAXNUMBER, EMAIL) VALUES "
				
				// Chris
				+ "('Reid', 'Chris', 'Gingerbeard', 'Google', '1337 Awesome Boul.', "
				+ "'Suite 007', '', 'Montreal', 'QC', 'H0H 0H0', 'Canada', "
				+ "'514-123-4567', '514-765-4321', '514-000-000', 'chris@reid.com'), "
				
				// Sandro
				+ "('Victoria-Arena', 'Sandro', 'Legay', 'Google', '1337 Awesome Boul.', "
				+ "'Suite 008', '', 'Montreal', 'QC', 'H0H 0H0', 'Canada', "
				+ "'514-234-4567', '514-876-5432', '514-000-001', 'sandro@arena.com'), "
				
				// Alessandro
				+ "('Rodi', 'Alessandro', 'Yoloswag', 'Google', '1337 Awesome Boul.', "
				+ "'Suite 009', '', 'Montreal', 'QC', 'H0H 0H0', 'Canada', "
				+ "'514-112-2334', '514-433-2211', '514-000-002', 'alessandro@rodi.com'), "
				
				// Ryan
				+ "('Prussin', 'Ryan', 'McLovin', 'Google', '1337 Awesome Boul.', "
				+ "'Suite 010', '', 'Montreal', 'QC', 'H0H 0H0', 'Canada', "
				+ "'514-987-6543', '514-345-6789', '514-000-003', 'ryan@prussin.com'), "
				
				// Polina
				+ "('Rolich', 'Polina', 'Awesome-O', 'Google', '1337 Awesome Boul.', "
				+ "'Suite 011', '', 'Montreal', 'QC', 'H0H 0H0', 'Canada', "
				+ "'514-332-2644', '514-555-7887', '514-000-004', 'polina@rolich.com'), "
				
				// Ritis
				+ "('Paulauskas', 'Ritis', 'The-Human-Compiler', 'Google', '1600 Amphitheatre Parkway', "
				+ "'', '', 'Mountain View', 'CA', '94043', 'USA', "
				+ "'1-650-253-0000', '1-650-332-6500', '1-650-253-0001', 'ritis@paulauskas.com') ";
		
		try (Connection connection = DriverManager.getConnection(url, user,
				password);
				PreparedStatement ps = connection.prepareStatement(sql);) {
			ps.executeUpdate();
		}

		// Instantiate AppointmentDAOImpl class
		try {
			con = new ContactDAOImpl();
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
	public void testInsertContact_Null() throws SQLException {
		con.insert(null);
		fail("NullPointerException not thrown when expected");

	}

	/**
	 * Tests update method with a null value.
	 * 
	 * @throws SQLException
	 */
	@Test(expected = NullPointerException.class)
	public void testUpdateContact_Null() throws SQLException {
		con.update(null);
		fail("NullPointerException not thrown when expected");

	}

	/**
	 * Tests delete method with a null value.
	 * 
	 * @throws SQLException
	 */
	@Test(expected = NullPointerException.class)
	public void testDeleteContact_Null() throws SQLException {
		con.delete(null);
		fail("NullPointerException not thrown when expected");

	}

	/**
	 * Tests insert method with a valid bean.
	 * 
	 * @throws SQLException
	 */
	@Test(timeout = 1000)
	public void testInsertContact_Valid() throws SQLException {

		ContactBean cb = new ContactBean();
		cb.setFirstName("Rodrigo");
		cb.setMiddleName("Sanchez");
		cb.setLastName("The Mexican");
		cb.setPhoneNumber("514-234-1111");
		cb.setEmail("JUNIT@TESTING.ROCKS");

		int result = con.insert(cb);
		assertEquals("1 row affected: ", 1, result);
	}

	/**
	 * Tests update method with a valid bean.
	 * 
	 * @throws SQLException
	 */
	@Test(timeout = 1000)
	public void testUpdateAppointment_Valid() throws SQLException {

		ContactBean cb = new ContactBean();
		cb.setFirstName("Rodrigo");
		cb.setMiddleName("Sanchez");
		cb.setLastName("The Mexican");
		cb.setPhoneNumber("514-234-1111");
		cb.setEmail("JUNIT@TESTING.ROCKS");
		cb.setId(3);

		int result = con.update(cb);
		assertEquals("1 row affected: ", 1, result);
	}

	/**
	 * Tests delete method with a valid bean.
	 * 
	 * @throws SQLException
	 */
	@Test(timeout = 1000)
	public void testDeleteAppointment_Valid() throws SQLException {

		ContactBean cb = new ContactBean();
		cb.setId(1);

		int result = con.delete(cb);
		assertEquals("1 row affected: ", 1, result);
	}

	/**
	 * Tests update method with a valid bean.
	 * 
	 * @throws SQLException
	 */
	@Test(timeout = 1000)
	public void testUpdateAppointment_Invalid() throws SQLException {

		ContactBean cb = new ContactBean();
		cb.setFirstName("Rodrigo");
		cb.setMiddleName("Sanchez");
		cb.setLastName("The Mexican");
		cb.setPhoneNumber("514-234-1111");
		cb.setEmail("JUNIT@TESTING.ROCKS");
		cb.setId(37);

		int result = con.update(cb);
		assertEquals("0 rows affected: ", 0, result);
	}

	/**
	 * Tests delete method with a valid bean.
	 * 
	 * @throws SQLException
	 */
	@Test(timeout = 1000)
	public void testDeleteAppointment_Invalid() throws SQLException {

		ContactBean cb = new ContactBean();
		cb.setId(37);

		int result = con.delete(cb);
		assertEquals("0 rows affected: ", 0, result);
	}

	/**
	 * Test for
	 * {@link com.chrisreid.JAMProject.persistence.ContactDAOImpl#getAllRecords()}
	 * 
	 * @throws SQLException
	 */
	@Test(timeout = 1000)
	public void testGetAllRecords() throws SQLException {
		ArrayList<ContactBean> cb = con.getAllRecords();
		assertEquals("6 records", 6, cb.size());
	}

	/**
	 * Tests for
	 * {@link com.chrisreid.JAMProject.persistence.ContactDAOImpl#searchByForm(com.chrisreid.JAMProject.data.ContactBean)}
	 * 
	 * @throws SQLException
	 */
	@Test(timeout = 1000)
	public void testSearchByForm_Single() throws SQLException {

		ContactBean bean = new ContactBean();
		bean.setLastName("Reid");
		bean.setFirstName("Chris");

		ArrayList<ContactBean> cb = con.searchByForm(bean);

		if (cb.size() != 1) {
			fail("There should only be one bean that matches these parameters");
		} else {
			assertEquals("The bean ID should be 1", 1, cb.get(0).getId());
		}
	}

	@Test(timeout = 1000)
	public void testSearchByForm_Multiple() throws SQLException {

		ContactBean bean = new ContactBean();
		bean.setCity("Montreal");

		ArrayList<ContactBean> cb = con.searchByForm(bean);

		assertEquals("5 records", 5, cb.size());
	}

	@Test(timeout = 1000)
	public void testSearchByForm_None() throws SQLException {

		ContactBean bean = new ContactBean();
		bean.setLastName("Anderson");

		ArrayList<ContactBean> cb = con.searchByForm(bean);

		assertEquals("0 beans", 0, cb.size());
	}

	@Test(expected = NullPointerException.class, timeout = 1000)
	public void testSearchByForm_NullPointer() throws SQLException {

		ContactBean bean = null;

		ArrayList<ContactBean> cb = con.searchByForm(bean);
		fail("NullPointerException not thrown when expected");
	}

}
