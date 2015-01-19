/*
 * Data Access Object
 * - Open a database
 * - Retrieve/Create/Delete/Update contacts from table
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

import com.chrisreid.JAMProject.data.ContactBean;

/**
 * ContactDAOImpl class
 * 
 * Phase 1
 * 
 * The implementation class for contact database queries.
 * 
 * @author Christopher Reid 0934402
 */
public class ContactDAOImpl implements ContactDAO {

	private final Logger log = LoggerFactory.getLogger(this.getClass()
			.getName());
	private Properties props = new Properties();
	private String url;
	private String user;
	private String password;

	public ContactDAOImpl() throws IOException {
		super();
		loadProperties("db.properties");
		log.debug("ContactDAOImpl instantiated");
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
	 * Retrieves all the records for the Contacts table and returns the data as
	 * an ArrayList of ContactBean objects
	 * 
	 * @return The ArrayList
	 * 
	 */
	@Override
	public ArrayList<ContactBean> getAllRecords() throws SQLException {

		ArrayList<ContactBean> cons = new ArrayList<>();
		String preparedSQL = "SELECT * FROM contacts";

		// Using Java 1.7 try with resources
		try (Connection connection = DriverManager.getConnection(url, user,
				password);
				PreparedStatement ps = connection.prepareStatement(preparedSQL);
				ResultSet resultSet = ps.executeQuery()) {

			while (resultSet.next()) {
				ContactBean cb = new ContactBean();
				cb.setLastName(resultSet.getString("LASTNAME"));
				cb.setFirstName(resultSet.getString("FIRSTNAME"));
				cb.setMiddleName(resultSet.getString("MIDDLENAME"));
				cb.setCompanyName(resultSet.getString("COMPANYNAME"));
				cb.setAddress1(resultSet.getString("ADDRESS1"));
				cb.setAddress2(resultSet.getString("ADDRESS2"));
				cb.setAddress3(resultSet.getString("ADDRESS3"));
				cb.setCity(resultSet.getString("CITY"));
				cb.setProvince(resultSet.getString("PROVINCE"));
				cb.setPostalCode(resultSet.getString("POSTALCODE"));
				cb.setCountry(resultSet.getString("COUNTRY"));
				cb.setPhoneNumber(resultSet.getString("PHONENUMBER"));
				cb.setCellNumber(resultSet.getString("CELLNUMBER"));
				cb.setFaxNumber(resultSet.getString("FAXNUMBER"));
				cb.setEmail(resultSet.getString("EMAIL"));
				cb.setId(resultSet.getInt("ID"));

				cons.add(cb);
			}
		} catch (SQLException sqlex) {
			// Log the exception
			log.error("connect failed", sqlex);
			// Re-throw the exception
			throw sqlex;
		}
		log.debug("Total number of contacts [getAllRecords]=" + cons.size());
		return cons;
	}

	/**
	 * Retrieves all the records from the Contacts table that match the supplied
	 * parameters and returns the data as an ArrayList of ContactBean objects
	 * 
	 * @return The ArrayList
	 */
	@Override
	public ArrayList<ContactBean> searchByForm(ContactBean contactBean)
			throws SQLException {

		ArrayList<ContactBean> cons = new ArrayList<>();

		String preparedSQL = createSQLStatement(contactBean);

		// Using Java 1.7 try with resources
		try (Connection connection = DriverManager.getConnection(url, user,
				password);
				PreparedStatement ps = connection.prepareStatement(preparedSQL)) {

			setPreparedStatementVariables(contactBean, ps);

			try (ResultSet resultSet = ps.executeQuery();) {

				while (resultSet.next()) {
					ContactBean cb = new ContactBean();
					cb.setLastName(resultSet.getString("LASTNAME"));
					cb.setFirstName(resultSet.getString("FIRSTNAME"));
					cb.setMiddleName(resultSet.getString("MIDDLENAME"));
					cb.setCompanyName(resultSet.getString("COMPANYNAME"));
					cb.setAddress1(resultSet.getString("ADDRESS1"));
					cb.setAddress2(resultSet.getString("ADDRESS2"));
					cb.setAddress3(resultSet.getString("ADDRESS3"));
					cb.setCity(resultSet.getString("CITY"));
					cb.setProvince(resultSet.getString("PROVINCE"));
					cb.setPostalCode(resultSet.getString("POSTALCODE"));
					cb.setCountry(resultSet.getString("COUNTRY"));
					cb.setPhoneNumber(resultSet.getString("PHONENUMBER"));
					cb.setCellNumber(resultSet.getString("CELLNUMBER"));
					cb.setFaxNumber(resultSet.getString("FAXNUMBER"));
					cb.setEmail(resultSet.getString("EMAIL"));
					cb.setId(resultSet.getInt("ID"));

					cons.add(cb);
				}
			} catch (SQLException sqlex) {
				// Log the exception
				log.error("ResultSet failed", sqlex);
				// Re-throw the exception
				throw sqlex;
			}
		} catch (SQLException sqlex) {
			// Log the exception
			log.error("connect failed", sqlex);
			// Re-throw the exception
			throw sqlex;
		}
		log.debug("Total number of contacts [searchByForm]=" + cons.size());
		return cons;

	}

	/**
	 * Retrieves all the records for the Contacts table that contain the String
	 * parameter in either LASTNAME, FIRSTNAME or MIDDLENAME fields and returns
	 * the data as an ArrayList of ContactBean objects.
	 * 
	 * @return The ArrayList
	 * 
	 */
	@Override
	public ArrayList<ContactBean> searchByName(String parameter)
			throws SQLException {

		ArrayList<ContactBean> cons = new ArrayList<>();
		String preparedSQL = "SELECT * FROM contacts WHERE "
				+ "lastname LIKE ? OR " + "firstname LIKE ? OR "
				+ "middlename LIKE ?";

		// Using Java 1.7 try with resources
		try (Connection connection = DriverManager.getConnection(url, user,
				password);
				PreparedStatement ps = connection.prepareStatement(preparedSQL);) {

			ps.setString(1, parameter);
			ps.setString(2, parameter);
			ps.setString(3, parameter);

			try (ResultSet resultSet = ps.executeQuery();) {

				while (resultSet.next()) {
					ContactBean cb = new ContactBean();
					cb.setLastName(resultSet.getString("LASTNAME"));
					cb.setFirstName(resultSet.getString("FIRSTNAME"));
					cb.setMiddleName(resultSet.getString("MIDDLENAME"));
					cb.setCompanyName(resultSet.getString("COMPANYNAME"));
					cb.setAddress1(resultSet.getString("ADDRESS1"));
					cb.setAddress2(resultSet.getString("ADDRESS2"));
					cb.setAddress3(resultSet.getString("ADDRESS3"));
					cb.setCity(resultSet.getString("CITY"));
					cb.setProvince(resultSet.getString("PROVINCE"));
					cb.setPostalCode(resultSet.getString("POSTALCODE"));
					cb.setCountry(resultSet.getString("COUNTRY"));
					cb.setPhoneNumber(resultSet.getString("PHONENUMBER"));
					cb.setCellNumber(resultSet.getString("CELLNUMBER"));
					cb.setFaxNumber(resultSet.getString("FAXNUMBER"));
					cb.setEmail(resultSet.getString("EMAIL"));
					cb.setId(resultSet.getInt("ID"));

					cons.add(cb);
				}
			} catch (SQLException sqlex) {
				// Log the exception
				log.error("ResultSet failed", sqlex);
				// Re-throw the exception
				throw sqlex;
			}
		} catch (SQLException sqlex) {
			// Log the exception
			log.error("connect failed", sqlex);
			// Re-throw the exception
			throw sqlex;
		}
		log.debug("Total number of contacts [searchByName]=" + cons.size());
		return cons;
	}

	/**
	 * Deletes a record from the Contacts table with the record that matches the
	 * id.
	 * 
	 * @param id
	 *            The id of the record to delete
	 */
	@Override
	public int delete(ContactBean contactBean) throws SQLException {

		String preparedSQL = "DELETE FROM contacts " + "WHERE id = ?";
		int affected;

		// Using Java 1.7 try with resources
		try (Connection connection = DriverManager.getConnection(url, user,
				password);
				PreparedStatement ps = connection.prepareStatement(preparedSQL)) {

			ps.setInt(1, contactBean.getId());
			affected = ps.executeUpdate();
		} catch (SQLException sqlex) {
			// Log the exception
			log.error("connect failed", sqlex);
			// Re-throw the exception
			throw sqlex;
		}
		log.debug("Total number of contacts affected [delete]=" + affected);
		return affected;
	}

	/**
	 * Inserts a record into the Contacts table by using a ContactBean object.
	 * 
	 * @param contactBean
	 *            The ContactBean to insert
	 */
	@Override
	public int insert(ContactBean contactBean) throws SQLException {

		String preparedSQL = "INSERT INTO contacts (LASTNAME, "
				+ "FIRSTNAME, MIDDLENAME, COMPANYNAME, "
				+ "ADDRESS1, ADDRESS2, ADDRESS3, CITY, "
				+ "PROVINCE, POSTALCODE, COUNTRY, PHONENUMBER, "
				+ "CELLNUMBER, FAXNUMBER, EMAIL) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		int affected;

		// Using Java 1.7 try with resources
		try (Connection connection = DriverManager.getConnection(url, user,
				password);
				PreparedStatement ps = connection.prepareStatement(preparedSQL)) {

			ps.setString(1, contactBean.getLastName());
			ps.setString(2, contactBean.getFirstName());
			ps.setString(3, contactBean.getMiddleName());
			ps.setString(4, contactBean.getCompanyName());
			ps.setString(5, contactBean.getAddress1());
			ps.setString(6, contactBean.getAddress2());
			ps.setString(7, contactBean.getAddress3());
			ps.setString(8, contactBean.getCity());
			ps.setString(9, contactBean.getProvince());
			ps.setString(10, contactBean.getPostalCode());
			ps.setString(11, contactBean.getCountry());
			ps.setString(12, contactBean.getPhoneNumber());
			ps.setString(13, contactBean.getCellNumber());
			ps.setString(14, contactBean.getFaxNumber());
			ps.setString(15, contactBean.getEmail());

			affected = ps.executeUpdate();

		} catch (SQLException sqlex) {
			// Log the exception
			log.error("connect failed", sqlex);
			// Re-throw the exception
			throw sqlex;
		}
		log.debug("Total number of contacts affected [insert]=" + affected);
		return affected;
	}

	/**
	 * Updates a record into the Contacts table by using a ContactBean.
	 * 
	 * @param contactBean
	 *            The ContactBean used for the update
	 */
	@Override
	public int update(ContactBean contactBean) throws SQLException {

		String preparedSQL = "UPDATE contacts SET "
				+ "LASTNAME = ?, FIRSTNAME = ?, MIDDLENAME = ?, "
				+ "COMPANYNAME = ?, ADDRESS1 = ?, ADDRESS2 = ?, "
				+ "ADDRESS3 = ?, CITY = ?, PROVINCE = ?, "
				+ "POSTALCODE = ?, COUNTRY = ?, PHONENUMBER = ?, "
				+ "CELLNUMBER = ?, FAXNUMBER = ?, EMAIL = ? " + "WHERE ID = ?";
		int affected;

		// Using Java 1.7 try with resources
		try (Connection connection = DriverManager.getConnection(url, user,
				password);
				PreparedStatement ps = connection.prepareStatement(preparedSQL)) {

			ps.setString(1, contactBean.getLastName());
			ps.setString(2, contactBean.getFirstName());
			ps.setString(3, contactBean.getMiddleName());
			ps.setString(4, contactBean.getCompanyName());
			ps.setString(5, contactBean.getAddress1());
			ps.setString(6, contactBean.getAddress2());
			ps.setString(7, contactBean.getAddress3());
			ps.setString(8, contactBean.getCity());
			ps.setString(9, contactBean.getProvince());
			ps.setString(10, contactBean.getPostalCode());
			ps.setString(11, contactBean.getCountry());
			ps.setString(12, contactBean.getPhoneNumber());
			ps.setString(13, contactBean.getCellNumber());
			ps.setString(14, contactBean.getFaxNumber());
			ps.setString(15, contactBean.getEmail());
			ps.setInt(16, contactBean.getId());

			affected = ps.executeUpdate();

		} catch (SQLException sqlex) {
			// Log the exception
			log.error("connect failed", sqlex);
			// Re-throw the exception
			throw sqlex;
		}
		log.debug("Total number of contacts affected [update]=" + affected);
		return affected;
	}

	private String createSQLStatement(ContactBean contactBean) {
		// Build SQL string
		String preparedSQL = "SELECT * FROM contacts WHERE";

		if (contactBean.getLastName() != "") {
			preparedSQL += " lastname = ? AND ";
		}
		if (contactBean.getFirstName() != "") {
			preparedSQL += " firstname = ? AND ";
		}
		if (contactBean.getMiddleName() != "") {
			preparedSQL += " middlename = ? AND ";
		}
		if (contactBean.getCompanyName() != "") {
			preparedSQL += " companyname = ? AND ";
		}
		if (contactBean.getAddress1() != "") {
			preparedSQL += " address1 = ? AND ";
		}
		if (contactBean.getAddress2() != "") {
			preparedSQL += " address2 = ? AND ";
		}
		if (contactBean.getAddress3() != "") {
			preparedSQL += " address3 = ? AND ";
		}
		if (contactBean.getCity() != "") {
			preparedSQL += " city = ? AND ";
		}
		if (contactBean.getProvince() != "") {
			preparedSQL += " province = ? AND ";
		}
		if (contactBean.getPostalCode() != "") {
			preparedSQL += " postalcode = ? AND ";
		}
		if (contactBean.getCountry() != "") {
			preparedSQL += " country = ? AND ";
		}
		if (contactBean.getPhoneNumber() != "") {
			preparedSQL += " phonenumber = ? AND ";
		}
		if (contactBean.getCellNumber() != "") {
			preparedSQL += " cellnumber = ? AND ";
		}
		if (contactBean.getFaxNumber() != "") {
			preparedSQL += " faxnumber = ? AND ";
		}
		if (contactBean.getEmail() != "") {
			preparedSQL += " email = ? AND ";
		}

		// Take appropriate substring
		preparedSQL = preparedSQL.substring(0, preparedSQL.length() - 5);

		return preparedSQL;
	}

	private void setPreparedStatementVariables(ContactBean contactBean,
			PreparedStatement ps) throws SQLException {

		int counter = 1;
		if (contactBean.getLastName() != "") {
			ps.setString(counter, contactBean.getLastName());
			counter++;
		}
		if (contactBean.getFirstName() != "") {
			ps.setString(counter, contactBean.getFirstName());
			counter++;
		}
		if (contactBean.getMiddleName() != "") {
			ps.setString(counter, contactBean.getMiddleName());
			counter++;
		}
		if (contactBean.getCompanyName() != "") {
			ps.setString(counter, contactBean.getCompanyName());
			counter++;
		}
		if (contactBean.getAddress1() != "") {
			ps.setString(counter, contactBean.getAddress1());
			counter++;
		}
		if (contactBean.getAddress2() != "") {
			ps.setString(counter, contactBean.getAddress2());
			counter++;
		}
		if (contactBean.getAddress3() != "") {
			ps.setString(counter, contactBean.getAddress3());
			counter++;
		}
		if (contactBean.getCity() != "") {
			ps.setString(counter, contactBean.getCity());
			counter++;
		}
		if (contactBean.getProvince() != "") {
			ps.setString(counter, contactBean.getProvince());
			counter++;
		}
		if (contactBean.getPostalCode() != "") {
			ps.setString(counter, contactBean.getPostalCode());
			counter++;
		}
		if (contactBean.getCountry() != "") {
			ps.setString(counter, contactBean.getCountry());
			counter++;
		}
		if (contactBean.getPhoneNumber() != "") {
			ps.setString(counter, contactBean.getPhoneNumber());
			counter++;
		}
		if (contactBean.getCellNumber() != "") {
			ps.setString(counter, contactBean.getCellNumber());
			counter++;
		}
		if (contactBean.getFaxNumber() != "") {
			ps.setString(counter, contactBean.getFaxNumber());
			counter++;
		}
		if (contactBean.getEmail() != "") {
			ps.setString(counter, contactBean.getEmail());
			counter++;
		}

	}

}
