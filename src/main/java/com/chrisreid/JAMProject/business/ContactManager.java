package com.chrisreid.JAMProject.business;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import com.chrisreid.JAMProject.data.ContactBean;
import com.chrisreid.JAMProject.persistence.ContactDAO;
import com.chrisreid.JAMProject.persistence.ContactDAOImpl;

/**
 * ContactManager class
 * 
 * Phase 1
 * 
 * Business class that uses the persistence layer to retrieve contact records.
 * 
 * @author Christopher Reid 0934402
 */
public class ContactManager {

	private ContactDAO contactDAO;

	public ContactManager() throws IOException {
		super();
		contactDAO = new ContactDAOImpl();
	}

	/**
	 * This method is used for testing, please ignore
	 * 
	 * @return data
	 */
	public String testMethod() {

		ContactBean bean = new ContactBean();
		bean.setLastName("Doe");
		bean.setFirstName("John");
		bean.setPhoneNumber("0000000000");

		ArrayList<ContactBean> data = new ArrayList<>();

		try {
			// contactDAO.insertContact(bean);
			data = contactDAO.getAllRecords();
		} catch (SQLException e) {
			System.out.println("Catch SQLException: " + e);
		}
		//
		// contactDAO.insertNewRecord(bean);
		//
		// contactDAO.deleteRecord(6);

		// ArrayList<ContactBean> data = contactDAO.getAllRecords();

		// ArrayList<ContactBean> data = contactDAO.searchByName("rodi");

		StringBuilder sb = new StringBuilder();
		for (ContactBean cb : data) {
			sb.append(cb.toString()).append("\n\n");
		}
		return sb.toString();
	}

}
