package com.chrisreid.JAMProject.presentation;

import java.awt.Dimension;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chrisreid.JAMProject.data.AppointmentBean;
import com.chrisreid.JAMProject.persistence.AppointmentDAOImpl;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import java.util.Date;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 * AppointmentForm class
 * 
 * Phase 3 
 * 
 * Description of this class
 * 
 * @author Christopher Reid 0934402
 */
public class AppointmentForm extends JPanel {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass()
			.getName());
	
	private ResourceBundle resources;
	private AppointmentBean ab;
	private Calendar date;
	
	private JFormattedTextField txtTitle;
	private JFormattedTextField txtLocation;
	private JFormattedTextField txtDetails;
	private JSpinner spinnerStartTime;
	private JSpinner spinnerEndTime;
	private JCheckBox chckbxAllDay;
	private JCheckBox chckbxAlarm;
	private JFormattedTextField txtCreated;
	private JButton btnSubmit;
	private JButton btnRemove;
	
	
	
	/**
	 * Default no-parameter constructor
	 */
	public AppointmentForm() {
		log.debug("AppointmentForm: No-Parameter constructor");
		
		setPreferredSize(new Dimension(600, 500));
		setLayout(null);
		
		date = Calendar.getInstance();
		
		constructPanel();
	}
	
	public void setAppointmentBean(AppointmentBean ab) {
		this.ab = ab;
	}
	
	public void setDate(Calendar date) { 
		this.date = date;
	}
	
	/**
	 * Updates the appointment form depending on the type of form required
	 * (add, edit/delete, search)
	 * 
	 * @param type the type of form
	 */
	public void update(String type) {
		
		for (ActionListener a : btnSubmit.getActionListeners()) {
			btnSubmit.removeActionListener(a);
		}
		
		for (ActionListener a : btnRemove.getActionListeners()) {
			btnRemove.removeActionListener(a);
		}
		

		// Make blank form
		SpinnerDateModel start = new SpinnerDateModel();
		SpinnerDateModel end = new SpinnerDateModel();
		// blank form
		txtTitle.setText("");
		txtLocation.setText("");
		txtDetails.setText("");
		
		Calendar c = (Calendar) date.clone();
		start.setValue(c.getTime());
		c.add(Calendar.MINUTE, 30);
		end.setValue(c.getTime());
		
		spinnerStartTime.setModel(start);
		spinnerEndTime.setModel(end);
		
		chckbxAllDay.setSelected(false);
		chckbxAlarm.setSelected(false);
		
		txtCreated.setText("");
		
		
		switch (type) {
			case "add":
				btnSubmit.setText(resources.getString("Add"));
				btnSubmit.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						
						AppointmentDAOImpl app;
						try {
							app = new AppointmentDAOImpl();
							AppointmentBean ab = new AppointmentBean();
							
							log.debug("title='" + txtTitle.getText() + "'");
							log.debug("location='" + txtLocation.getText() + "'");
							log.debug("details='" + txtDetails.getText() + "'");
							log.debug("starttime='" + spinnerStartTime.getValue() + "'");
							log.debug("endtime='" + spinnerEndTime.getValue() + "'");
							log.debug("allday='" + chckbxAllDay.isSelected() + "'");
							log.debug("alarm='" + chckbxAlarm.isSelected() + "'");
							log.debug("created='" + txtCreated.getText() + "'");
							
							Date start = (Date)spinnerStartTime.getValue();
							Date end = (Date)spinnerEndTime.getValue();
							
							ab.setTitle(txtTitle.getText());
							ab.setLocation(txtLocation.getText());
							ab.setDetails(txtDetails.getText());
							ab.setStartTime(new Timestamp(start.getTime()));
							ab.setEndTime(new Timestamp(end.getTime()));
							ab.setAllDay(chckbxAllDay.isSelected());
							ab.setAlarmReminder(chckbxAlarm.isSelected());
							
							int result = app.insert(ab);
							
							if (result == 1) {
								log.debug("Added appointment");
							} else {
								log.debug("No appointments added (result=" + result + ")");
							}
							
						} catch (IOException ioe) {
							log.error("IOException: " + ioe.getMessage());
						} catch (SQLException sqle) {
							log.error("SQLException: " + sqle.getMessage());
						}
						
					}
				});
				btnRemove.setText(resources.getString("Reset"));
				btnRemove.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						
						SpinnerDateModel start = new SpinnerDateModel();
						SpinnerDateModel end = new SpinnerDateModel();
						
						// blank form
						txtTitle.setText("");
						txtLocation.setText("");
						txtDetails.setText("");
						
						Calendar c = (Calendar) date.clone();
						start.setValue(c.getTime());
						c.add(Calendar.MINUTE, 30);
						end.setValue(c.getTime());
						
						spinnerStartTime.setModel(start);
						spinnerEndTime.setModel(end);
						
						chckbxAllDay.setSelected(false);
						chckbxAlarm.setSelected(false);
						
						txtCreated.setText("");
						log.debug("Reset appointment");
					}
				});
				break;
				
			case "edit":
				if (ab != null) {

					SpinnerDateModel model = new SpinnerDateModel();
					
					// fill in the ab into form
					txtTitle.setText(ab.getTitle());
					txtLocation.setText(ab.getLocation());
					txtDetails.setText(ab.getDetails());
					
					model.setValue(ab.getStartTime());
					spinnerStartTime.setModel(model);

					model.setValue(ab.getEndTime());
					spinnerEndTime.setModel(model);
					
					chckbxAllDay.setSelected(ab.isAllDay());
					chckbxAlarm.setSelected(ab.isAlarmReminder());
					
					txtCreated.setText(ab.getCreated().toString());
				}
				
				btnSubmit.setText(resources.getString("Edit"));
				btnSubmit.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						AppointmentDAOImpl app;
						try {
							app = new AppointmentDAOImpl();

							Date start = (Date)spinnerStartTime.getValue();
							Date end = (Date)spinnerEndTime.getValue();
							
							ab.setTitle(txtTitle.getText());
							ab.setLocation(txtLocation.getText());
							ab.setDetails(txtDetails.getText());
							ab.setStartTime(new Timestamp(start.getTime()));
							ab.setEndTime(new Timestamp(end.getTime()));
							ab.setAllDay(chckbxAllDay.isSelected());
							ab.setAlarmReminder(chckbxAlarm.isSelected());
							
							int result = app.update(ab);
							
							if (result == 1) {
								log.debug("Updated appointment");
							} else {
								log.debug("Appointment not updated (result=" + result + ")");
							}
							
						} catch (IOException ioe) {
							log.error("IOException: " + ioe.getMessage());
						} catch (SQLException sqle) {
							log.error("SQLException: " + sqle.getMessage());
						}
						log.debug("Edit appointment");
					}
				});
				btnRemove.setText(resources.getString("Delete"));
				btnRemove.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						AppointmentDAOImpl app;
						try {
							app = new AppointmentDAOImpl();
							
							int result = app.delete(ab);
							
							if (result == 1) {
								log.debug("Deleted appointment");
							} else {
								log.debug("Appointment not deleted (result=" + result + ")");
							}
							
						} catch (IOException ioe) {
							log.error("IOException: " + ioe.getMessage());
						} catch (SQLException sqle) {
							log.error("SQLException: " + sqle.getMessage());
						}
						log.debug("Delete appointment");
					}
				});
				break;
				
			case "search":
				btnSubmit.setText(resources.getString("Search"));
				btnSubmit.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						AppointmentDAOImpl app;
						try {
							app = new AppointmentDAOImpl();
							AppointmentBean ab = new AppointmentBean();
							
							log.debug("title='" + txtTitle.getText() + "'");
							log.debug("location='" + txtLocation.getText() + "'");
							log.debug("details='" + txtDetails.getText() + "'");
							log.debug("starttime='" + spinnerStartTime.getValue() + "'");
							log.debug("endtime='" + spinnerEndTime.getValue() + "'");
							log.debug("allday='" + chckbxAllDay.isSelected() + "'");
							log.debug("alarm='" + chckbxAlarm.isSelected() + "'");
							log.debug("created='" + txtCreated.getText() + "'");
							
							Date start = (Date)spinnerStartTime.getValue();
							Date end = (Date)spinnerEndTime.getValue();
							
							ab.setTitle(txtTitle.getText());
							ab.setLocation(txtLocation.getText());
							ab.setDetails(txtDetails.getText());
							ab.setStartTime(new Timestamp(start.getTime()));
							ab.setEndTime(new Timestamp(end.getTime()));
							ab.setAllDay(chckbxAllDay.isSelected());
							ab.setAlarmReminder(chckbxAlarm.isSelected());
							
							int result = app.insert(ab);
							
							if (result == 1) {
								log.debug("Added appointment");
							} else {
								log.debug("No appointments added (result=" + result + ")");
							}
							
						} catch (IOException ioe) {
							log.error("IOException: " + ioe.getMessage());
						} catch (SQLException sqle) {
							log.error("SQLException: " + sqle.getMessage());
						}
						log.debug("Search appointment");
					}
				});
				btnRemove.setText(resources.getString("Reset"));
				btnRemove.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						
						SpinnerDateModel start = new SpinnerDateModel();
						SpinnerDateModel end = new SpinnerDateModel();
						
						// blank form
						txtTitle.setText("");
						txtLocation.setText("");
						txtDetails.setText("");
						
						Calendar c = (Calendar) date.clone();
						start.setValue(c.getTime());
						end.setValue(c.getTime());
						
						spinnerStartTime.setModel(start);
						spinnerEndTime.setModel(end);
						
						chckbxAllDay.setSelected(false);
						chckbxAlarm.setSelected(false);
						
						txtCreated.setText("");
						log.debug("Reset appointment");
					}
				});
				break;
		}
	}
	
	private void constructPanel() {
		
		//Locale currentLocale = new Locale("en", "CA");
		resources = ResourceBundle.getBundle("FormBundle");
		
		JLabel lblAppointment = new JLabel(resources.getString("Appointments"));
		lblAppointment.setHorizontalAlignment(SwingConstants.CENTER);
		lblAppointment.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblAppointment.setBounds(10, 11, 254, 25);
		add(lblAppointment);
		
		JLabel lblTitle = new JLabel(resources.getString("Title"));
		lblTitle.setBounds(20, 56, 145, 14);
		add(lblTitle);
		
		txtTitle = new JFormattedTextField();
		txtTitle.setBounds(10, 70, 145, 20);
		add(txtTitle);
		
		JLabel lblLocation = new JLabel(resources.getString("Location"));
		lblLocation.setBounds(20, 101, 145, 14);
		add(lblLocation);
		
		txtLocation = new JFormattedTextField();
		txtLocation.setBounds(10, 115, 145, 20);
		add(txtLocation);
		
		JLabel lblDetails= new JLabel(resources.getString("Details"));
		lblDetails.setBounds(20, 146, 145, 14);
		add(lblDetails);
		
		txtDetails = new JFormattedTextField();
		txtDetails.setBounds(10, 160, 145, 20);
		add(txtDetails);
		
		JLabel lblStartTime = new JLabel(resources.getString("StartTime"));
		lblStartTime.setBounds(20, 191, 145, 14);
		add(lblStartTime);
		
		SpinnerDateModel start = new SpinnerDateModel();
		start.setValue(date.getTime());
		
		spinnerStartTime = new JSpinner();
		spinnerStartTime.setModel(start);
		spinnerStartTime.setBounds(10, 205, 145, 20);
		add(spinnerStartTime);
		
		JLabel lblEndTime = new JLabel(resources.getString("EndTime"));
		lblEndTime.setBounds(20, 236, 145, 14);
		add(lblEndTime);
		
		SpinnerDateModel end = new SpinnerDateModel();
		end.setValue(date.getTime());
		
		spinnerEndTime = new JSpinner();
		spinnerEndTime.setModel(end);
		spinnerEndTime.setBounds(10, 250, 145, 20);
		add(spinnerEndTime);
		
		chckbxAllDay = new JCheckBox(resources.getString("AllDay"));
		chckbxAllDay.setBounds(20, 277, 145, 23);
		add(chckbxAllDay);
		
		chckbxAlarm = new JCheckBox(resources.getString("AlarmReminder"));
		chckbxAlarm.setBounds(20, 303, 145, 23);
		add(chckbxAlarm);
		
		JLabel lblCreated = new JLabel(resources.getString("Created"));
		lblCreated.setBounds(20, 331, 145, 14);
		add(lblCreated);
		
		txtCreated = new JFormattedTextField();
		txtCreated.setEnabled(false);
		txtCreated.setBounds(10, 345, 145, 20);
		add(txtCreated);
		
		btnSubmit = new JButton();
		btnSubmit.setBounds(175, 70, 89, 23);
		add(btnSubmit);
		
		btnRemove = new JButton();
		btnRemove.setBounds(175, 95, 89, 23);
		add(btnRemove);
		
	}
}
