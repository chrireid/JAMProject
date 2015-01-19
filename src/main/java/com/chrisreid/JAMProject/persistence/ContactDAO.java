package com.chrisreid.JAMProject.persistence;

import com.chrisreid.JAMProject.data.ContactBean;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * ContactDAO Interface
 * 
 * Phase 1
 * 
 * The interface for contact database queries.
 * 
 * @author Christopher Reid 0934402
 */
public interface ContactDAO {

	public ArrayList<ContactBean> getAllRecords() throws SQLException;

	public ArrayList<ContactBean> searchByForm(ContactBean contactBean)
			throws SQLException;

	public ArrayList<ContactBean> searchByName(String parameter)
			throws SQLException;

	public int delete(ContactBean contactBean) throws SQLException;

	public int insert(ContactBean contactBean) throws SQLException;

	public int update(ContactBean contactBean) throws SQLException;

}
