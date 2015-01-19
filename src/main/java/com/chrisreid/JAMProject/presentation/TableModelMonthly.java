package com.chrisreid.JAMProject.presentation;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import javax.swing.table.AbstractTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chrisreid.JAMProject.business.AppointmentManager;
import com.chrisreid.JAMProject.data.AppointmentBean;

/**
 * TableModelDaily class
 * 
 * Phase 3
 * 
 * Description of this class
 * 
 * @author Christopher Reid 0934402
 */
@SuppressWarnings("serial")
public class TableModelMonthly extends AbstractTableModel {

	private final Logger log = LoggerFactory.getLogger(this.getClass()
			.getName());

	private ResourceBundle resources = ResourceBundle
			.getBundle("CalendarBundle");

	private String[] columnNames = { resources.getString("Sunday"),
			resources.getString("Monday"), resources.getString("Tuesday"),
			resources.getString("Wednesday"), resources.getString("Thursday"),
			resources.getString("Friday"), resources.getString("Saturday") };

	private Calendar date;

	private CalendarRoutines routines = new CalendarRoutines();
	private Object[][] data;
	private int[][] dates;

	/**
	 * No-parameter constructor
	 */
	public TableModelMonthly() {

		log.debug("TableModelMonthly: No-Parameter constructor");

		// Set the date variable to the current system's date.
		date = Calendar.getInstance();
		constructMonth(date);

	}

	public TableModelMonthly(Calendar date) {

		log.debug("TableModelMonthly: One-Parameter constructor (Calendar date)");

		// Set the date variable to the parameter date
		this.date = date;
		constructMonth(date);

	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getColumnCount() This is one of the
	 * three abstract methods that must be implemented
	 */
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getRowCount() This is one of the three
	 * abstract methods that must be implemented
	 */
	@Override
	public int getRowCount() {
		return data.length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.TableModel#getValueAt(int, int) This is one of the
	 * three abstract methods that must be implemented Returns the value from
	 * the data structure that corresponds to a position in the table. When the
	 * table is rendered this method is called.
	 */
	@Override
	public Object getValueAt(int row, int col) {

		return data[row][col];
	}

	public int getDateAt(int row, int col) {

		return dates[row][col];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int) Called upon
	 * to return a string as the name of the specified column
	 */
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object,
	 * int, int)
	 */
	@Override
	public void setValueAt(Object value, int row, int col) {
		// Change the data only if it has changed
		if (!data[row][col].equals(value)) {

			// Change the data
			data[row][col] = value;
			// If the change came from outside the table then this notifies the
			// table that is must be updated with a new value.
			fireTableCellUpdated(row, col);
		}
	}

	private ArrayList<AppointmentBean> fetchAppointments() {

		// For Phase 4:
		// Remove test data and use db

		ArrayList<AppointmentBean> appointments = new ArrayList<AppointmentBean>();

		try {
			AppointmentManager am = new AppointmentManager();
			appointments = am.searchByMonth(date);

			log.debug("number of appointments for month ("
					+ date.getTime().toString() + "): " + appointments.size());

			if (appointments.size() > 0) {
				// do something?
			}
		} catch (IOException ioe) {
			log.error("IOException error in fetchAppointmentData: ", ioe);
		} catch (SQLException sqle) {
			log.error("SQLException error in fetchAppointmentData: ", sqle);
		}

		return appointments;

	}

	private void constructMonth(Calendar date) {

		data = routines.getMonthlyStructure(date);
		dates = new int[6][7];

		ArrayList<AppointmentBean> appointments = fetchAppointments();

		for (int row = 0; row < data.length; row++) {
			for (int col = 0; col < data[row].length; col++) {

				String daily = "";
				if (data[row][col] != "") {

					dates[row][col] = Integer.parseInt((String) data[row][col]);

					daily = "<b>" + data[row][col] + "</b>";

					Timestamp start = Timestamp.valueOf(date.get(Calendar.YEAR)
							+ "-" + (date.get(Calendar.MONTH) + 1) + "-"
							+ data[row][col] + " 00:00:00.000");

					Timestamp end = Timestamp.valueOf(date.get(Calendar.YEAR)
							+ "-" + (date.get(Calendar.MONTH) + 1) + "-"
							+ data[row][col] + " 23:59:59.999");

					int counter = 1;
					for (AppointmentBean ab : appointments) {

						// If this appointment is on the specified day
						if (ab.getStartTime().before(end)
								&& ab.getEndTime().after(start)) {

							String title = ab.getTitle();
							if (title.length() > 20) {
								title = title.substring(0, 20) + "...";
							}

							if (counter < 4) {
								daily += "<br/><i>" + counter + ": " + title
										+ "</i>";
								counter++;
							} else {
								daily += "<br/>...";
								counter++;
							}

						}

					}
				}

				setValueAt(daily, row, col);

			}
		}

	}
}
