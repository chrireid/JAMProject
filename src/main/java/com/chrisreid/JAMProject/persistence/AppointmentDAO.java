package com.chrisreid.JAMProject.persistence;

import com.chrisreid.JAMProject.data.AppointmentBean;

import java.sql.Timestamp;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * AppointmentDAO Interface
 * 
 * Phase 1
 * 
 * The interface for appointment database queries.
 * 
 * @author Christopher Reid 0934402
 */
public interface AppointmentDAO {

	public ArrayList<AppointmentBean> getAllRecords() throws SQLException;

	public ArrayList<AppointmentBean> searchByForm(
			AppointmentBean appointmentBean) throws SQLException;

	public ArrayList<AppointmentBean> searchByRange(Timestamp start,
			Timestamp end) throws SQLException;

	public int delete(AppointmentBean appointmentBean) throws SQLException;

	public int insert(AppointmentBean appointmentBean) throws SQLException;

	public int update(AppointmentBean appointmentBean) throws SQLException;

}
