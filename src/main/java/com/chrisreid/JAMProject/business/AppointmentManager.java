package com.chrisreid.JAMProject.business;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import com.chrisreid.JAMProject.data.AppointmentBean;
import com.chrisreid.JAMProject.persistence.AppointmentDAO;
import com.chrisreid.JAMProject.persistence.AppointmentDAOImpl;

/**
 * AppointmentManager class
 * 
 * Phase 1
 * 
 * Business class that uses the persistence layer to retrieve appointment
 * records.
 * 
 * @author Christopher Reid 0934402
 */
public class AppointmentManager {

	private AppointmentDAO appointmentDAO;

	public AppointmentManager() throws IOException {
		super();
		appointmentDAO = new AppointmentDAOImpl();
	}

	/**
	 * Retrieves all the days in the year that have appointments and returns the
	 * data as an ArrayList of Integer objects
	 * 
	 * @param calendar
	 *            The calendar instance
	 * 
	 * @return The ArrayList of Integers (DAY_OF_YEAR for Calendar)
	 */
	public ArrayList<Integer> searchByYear(Calendar calendar)
			throws SQLException {

		ArrayList<AppointmentBean> data;

		// Determine the start of the first day of the current year...
		Calendar startOfYear = (Calendar) calendar.clone();
		startOfYear.set(Calendar.MONTH, 0);
		startOfYear.set(Calendar.DATE, 1);
		startOfYear.set(Calendar.HOUR_OF_DAY, 0);
		startOfYear.set(Calendar.MINUTE, 0);
		startOfYear.set(Calendar.SECOND, 0);
		startOfYear.set(Calendar.MILLISECOND, 0);

		// Determine the end of the last day of the current year...
		Calendar endOfYear = (Calendar) calendar.clone();
		endOfYear.set(Calendar.MONTH, 11);
		endOfYear.set(Calendar.DATE, 31);
		endOfYear.set(Calendar.HOUR_OF_DAY, 23);
		endOfYear.set(Calendar.MINUTE, 59);
		endOfYear.set(Calendar.SECOND, 59);
		endOfYear.set(Calendar.MILLISECOND, 999);

		Timestamp start = new Timestamp(startOfYear.getTimeInMillis());
		Timestamp end = new Timestamp(endOfYear.getTimeInMillis());

		data = appointmentDAO.searchByRange(start, end);

		/*
		 * Now that we have the data in an ArrayList, we can go through the
		 * ArrayList to determine which days of the current year have
		 * appointments.
		 */

		// Instantiate the ArrayList of Integer Objects...
		ArrayList<Integer> days = new ArrayList<>();

		// Creating a position holder (to avoid duplication)...
		Calendar position = (Calendar) startOfYear.clone();

		for (AppointmentBean ap : data) {

			// Correct the position (if necessary)...
			if (ap.getStartTime().after(position.getTime())) {
				position.setTime(ap.getStartTime());
			}
			// Correct the endTime of Appointment (if necessary)...
			if (ap.getEndTime().after(endOfYear.getTime())) {
				ap.setEndTime(new Timestamp(endOfYear.getTimeInMillis()));
			}

			// Loop while position has not passed the end of appointment...
			while (!position.getTime().after(ap.getEndTime())) {

				// Add the current day to the ArrayList...
				days.add(position.get(Calendar.DAY_OF_YEAR));

				// Increment the position to the start of the next day...
				position.add(Calendar.DATE, 1);
				position.set(Calendar.HOUR, 0);
				position.set(Calendar.MINUTE, 0);
				position.set(Calendar.SECOND, 0);
				position.set(Calendar.MILLISECOND, 0);

			} // end while
		} // end for

		return days;
	}

	/**
	 * Receives a Calendar object instance and returns all appointments for the
	 * given month.
	 * 
	 * @param calendar
	 *            The calendar instance
	 * @return The ArrayList of Beans
	 */
	public ArrayList<AppointmentBean> searchByMonth(Calendar calendar)
			throws SQLException {

		ArrayList<AppointmentBean> data;

		// Determine the start of the first day of the current month...
		Calendar startOfMonth = (Calendar) calendar.clone();
		startOfMonth.set(Calendar.DATE, 1);
		startOfMonth.set(Calendar.HOUR_OF_DAY, 0);
		startOfMonth.set(Calendar.MINUTE, 0);
		startOfMonth.set(Calendar.SECOND, 0);
		startOfMonth.set(Calendar.MILLISECOND, 0);

		// Determine the end of the last day of the current month...
		Calendar endOfMonth = (Calendar) calendar.clone();
		endOfMonth.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		endOfMonth.set(Calendar.HOUR_OF_DAY, 23);
		endOfMonth.set(Calendar.MINUTE, 59);
		endOfMonth.set(Calendar.SECOND, 59);
		endOfMonth.set(Calendar.MILLISECOND, 999);

		Timestamp start = new Timestamp(startOfMonth.getTimeInMillis());
		Timestamp end = new Timestamp(endOfMonth.getTimeInMillis());

		data = appointmentDAO.searchByRange(start, end);

		return data;
	}

	/**
	 * Receives a Calendar object instance and returns all appointments for the
	 * given week.
	 * 
	 * @param calendar
	 *            The calendar instance
	 * @return The ArrayList of Beans
	 */
	public ArrayList<AppointmentBean> searchByWeek(Calendar calendar)
			throws SQLException {

		ArrayList<AppointmentBean> data;

		// Determine the start of the first day of the current week...
		Calendar startOfWeek = (Calendar) calendar.clone();
		while (startOfWeek.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
			startOfWeek.add(Calendar.DAY_OF_WEEK, -1);
		}
		startOfWeek.set(Calendar.HOUR_OF_DAY, 0);
		startOfWeek.set(Calendar.MINUTE, 0);
		startOfWeek.set(Calendar.SECOND, 0);
		startOfWeek.set(Calendar.MILLISECOND, 0);

		// Determine the end of the last day of the current week...
		Calendar endOfWeek = (Calendar) startOfWeek.clone();
		endOfWeek.add(Calendar.DAY_OF_WEEK, 6);
		endOfWeek.set(Calendar.HOUR_OF_DAY, 23);
		endOfWeek.set(Calendar.MINUTE, 59);
		endOfWeek.set(Calendar.SECOND, 59);
		endOfWeek.set(Calendar.MILLISECOND, 999);

		Timestamp start = new Timestamp(startOfWeek.getTimeInMillis());
		Timestamp end = new Timestamp(endOfWeek.getTimeInMillis());

		data = appointmentDAO.searchByRange(start, end);

		return data;
	}

	/**
	 * Receives a Calendar object instance and returns all appointments for the
	 * given day.
	 * 
	 * @param calendar
	 *            The calendar instance
	 * @return The ArrayList of Beans
	 */
	public ArrayList<AppointmentBean> searchByDay(Calendar calendar)
			throws SQLException {

		ArrayList<AppointmentBean> data;

		// Determine the start of the current day...
		Calendar startOfDay = (Calendar) calendar.clone();
		startOfDay.set(Calendar.HOUR_OF_DAY, 0);
		startOfDay.set(Calendar.MINUTE, 0);
		startOfDay.set(Calendar.SECOND, 0);
		startOfDay.set(Calendar.MILLISECOND, 0);

		// Determine the end of the current day...
		Calendar endOfDay = (Calendar) calendar.clone();
		endOfDay.set(Calendar.HOUR_OF_DAY, 23);
		endOfDay.set(Calendar.MINUTE, 59);
		endOfDay.set(Calendar.SECOND, 59);
		endOfDay.set(Calendar.MILLISECOND, 999);

		Timestamp start = new Timestamp(startOfDay.getTimeInMillis());
		Timestamp end = new Timestamp(endOfDay.getTimeInMillis());

		data = appointmentDAO.searchByRange(start, end);

		return data;
	}

	/**
	 * This method is used for testing, please ignore
	 * 
	 * @return data
	 */
	public String testMethod() {

		Calendar calendar = Calendar.getInstance();
		// calendar.set(Calendar.DATE, 6);
		// calendar.set(Calendar.MONTH, 3);
		// calendar.set(Calendar.YEAR, 2013);

		System.out.println("CURRENT TIME: \t" + calendar.getTime());
		System.out.println();

		// ArrayList<AppointmentBean> data = searchByDay(calendar); // fixed
		// ArrayList<AppointmentBean> data = searchByWeek(calendar); // fixed
		// ArrayList<AppointmentBean> data = searchByMonth(calendar); // fixed
		// ArrayList<Integer> data = searchByYear(calendar); // fixed

		// ---------- TEST CASE

		// Calendar test = Calendar.getInstance();
		// test.add(Calendar.DATE, -3);
		// test.set(Calendar.MONTH, 11);
		// test.set(Calendar.YEAR, 2009);
		// test.set(Calendar.DATE, 30);
		//
		// Calendar test2 = Calendar.getInstance();
		// test2.add(Calendar.DATE, 3);
		// test2.set(Calendar.MONTH, 0);
		// test2.set(Calendar.YEAR, 2011);
		// test2.set(Calendar.DATE, 3);
		//
		// AppointmentBean bean = new AppointmentBean();
		// bean.setTitle("Debugging the calendar...bug?");
		// bean.setStartTime(test.getTime());
		// bean.setEndTime(test2.getTime());
		// bean.setDetails("Let's see if this works");
		//
		// appointmentDAO.insertAppointment(bean);

		// ArrayList<AppointmentBean> data = appointmentDAO.getAllRecords();

		// ---------- TEST CASE

		StringBuilder sb = new StringBuilder();

		// for (AppointmentBean ab : data)
		// sb.append(ab.toString()).append("\n\n");

		// for (Integer i : data)
		// sb.append(i.toString()).append("\n\n");

		return sb.toString();
	}
}
