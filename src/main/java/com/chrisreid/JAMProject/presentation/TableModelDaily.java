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
public class TableModelDaily extends AbstractTableModel {

	private final Logger log = LoggerFactory.getLogger(this.getClass()
			.getName());

	private ResourceBundle resources = ResourceBundle
			.getBundle("CalendarBundle");

	private String[] columnNames = { resources.getString("Time"),
			resources.getString("Appointments") };

	private Calendar date;
	
	private ArrayList<AppointmentBean> appointments;
	

	// Time & Appointment String arrays
	private Object[][] data = { { "0:00", "" }, { "0:30", "" }, { "1:00", "" },
			{ "1:30", "" }, { "2:00", "" }, { "2:30", "" }, { "3:00", "" },
			{ "3:30", "" }, { "4:00", "" }, { "4:30", "" }, { "5:00", "" },
			{ "5:30", "" }, { "6:00", "" }, { "6:30", "" }, { "7:00", "" },
			{ "7:30", "" }, { "8:00", "" }, { "8:30", "" }, { "9:00", "" },
			{ "9:30", "" }, { "10:00", "" }, { "10:30", "" }, { "11:00", "" },
			{ "11:30", "" }, { "12:00", "" }, { "12:30", "" }, { "13:00", "" },
			{ "13:30", "" }, { "14:00", "" }, { "14:30", "" }, { "15:00", "" },
			{ "15:30", "" }, { "16:00", "" }, { "16:30", "" }, { "17:00", "" },
			{ "17:30", "" }, { "18:00", "" }, { "18:30", "" }, { "19:00", "" },
			{ "19:30", "" }, { "20:00", "" }, { "20:30", "" }, { "21:00", "" },
			{ "21:30", "" }, { "22:00", "" }, { "22:30", "" }, { "23:00", "" },
			{ "23:30", "" }, };

	/**
	 * No-parameter constructor
	 */
	public TableModelDaily() {
		
		log.debug("TableModelDaily: No-Parameter constructor");
		
		// Set the date variable to the current system's date.
		date = Calendar.getInstance();
		appointments = new ArrayList<AppointmentBean>();
		fetchAppointmentsForToday();
		getAppointmentData();
	}
	
	public TableModelDaily(Calendar date) {
		
		log.debug("TableModelDaily: One-Parameter constructor (Calendar date)");
		
		// Set the date variable to the parameter date
		this.date = date;
		appointments = new ArrayList<AppointmentBean>();
		fetchAppointmentsForToday();
		getAppointmentData();
	}
	
	public Calendar getDate() {
		return date;
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
	 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int, int)
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
	
	
	private void fetchAppointmentsForToday() {

		// For Phase 4:
		// Remove test data and use db
		try {
			AppointmentManager am = new AppointmentManager();
			appointments = am.searchByDay(date);
			
			log.debug("number of appointments for day (" + date.getTime().toString() + "): " + appointments.size());
			
			if (appointments.size() > 0) {
				// do stuff here
			}
		} catch (IOException ioe) {
			log.error("IOException error in fetchAppointmentData: ", ioe);
		} catch (SQLException sqle) {
			log.error("SQLException error in fetchAppointmentData: ", sqle);
		}
		
	}
	
	private void getAppointmentData() {
		
		Timestamp start = Timestamp.valueOf(date.get(Calendar.YEAR) + "-" 
				+ (date.get(Calendar.MONTH) + 1) + "-" 
				+ date.get(Calendar.DAY_OF_MONTH)
				+ " 00:00:00.000");
		
		Timestamp end = Timestamp.valueOf(date.get(Calendar.YEAR) + "-" 
				+ (date.get(Calendar.MONTH) + 1) + "-" 
				+ date.get(Calendar.DAY_OF_MONTH)
				+ " 23:59:59");
		
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		
		// Go through the ArrayList of appointments to modify start/end if necessary
		for (AppointmentBean ab : appointments) {
			
			// Modify start of appointment if necessary
			if (ab.getStartTime().before(start))
				ab.setStartTime(start);
				
			// Modify end of appointment if necessary
			if (ab.getEndTime().after(end))
				ab.setEndTime(end);
				
			log.debug("Bean: " + ab.getTitle() + ", Starts=" + ab.getStartTime() + ", Ends=" + ab.getEndTime());
			
		}
		
		for (int i=0; i< data.length; i++) {

			Calendar time = (Calendar) date.clone();
			time.set(Calendar.HOUR_OF_DAY, i/2);
			time.set(Calendar.MINUTE, i%2*30);
			
			start = Timestamp.valueOf(time.get(Calendar.YEAR) + "-" 
					+ (time.get(Calendar.MONTH) + 1) + "-" 
					+ time.get(Calendar.DAY_OF_MONTH) + " " 
					+ time.get(Calendar.HOUR_OF_DAY) + ":" 
					+ time.get(Calendar.MINUTE) + ":00");
			
			time.add(Calendar.MINUTE, 30);
			
			end = Timestamp.valueOf(time.get(Calendar.YEAR) + "-" 
					+ (time.get(Calendar.MONTH) + 1) + "-" 
					+ time.get(Calendar.DAY_OF_MONTH) + " " 
					+ time.get(Calendar.HOUR_OF_DAY) + ":" 
					+ time.get(Calendar.MINUTE) + ":00");

			for (AppointmentBean ab : appointments) {
				
				if ((ab.getStartTime().after(start) && ab.getStartTime().before(end)) ||
					(ab.getStartTime().equals(start))) {
					
					// Color and Display appointment info
					
					String s = (String) data[i][1];
					s += "<b>" + ab.getTitle() + "</b>"
							+ " (" + timeFormat.format(ab.getStartTime()) 
							+ " - " + timeFormat.format(ab.getEndTime()) + ")\n";
					
					setValueAt(s, i, 1);
					
				} else if (ab.getStartTime().before(start) && ab.getEndTime().after(start)) {
					
					// Color here
					setValueAt(" ", i, 1);
				}
			}
		}
	} // end getAppointmentData()
	
	public ArrayList<AppointmentBean> findAppointmentByTitle(String title) {
		
		ArrayList<AppointmentBean> result = new ArrayList<AppointmentBean>();
		
		for (AppointmentBean ab : appointments) {
			if (ab.getTitle().equals(title)) {
				result.add(ab);
			}
		}
		return result;
	}
	

}
