package com.chrisreid.JAMProject.presentation;

import java.awt.CardLayout;
import java.util.Calendar;

import javax.swing.JPanel;

import com.chrisreid.JAMProject.data.AppointmentBean;
import com.chrisreid.JAMProject.data.ContactBean;

@SuppressWarnings("serial")
public class CardPanel extends JPanel {

	private AppointmentForm appForm;
	private ContactForm conForm;
	private CalendarPanelYearlyView yearlyPanel;
	private CalendarPanelMonthlyView monthlyPanel;
	private CalendarPanelDailyView dailyPanel;
	private Calendar date;
	
	/**
	 * Create the panels and place them into the named location in the card
	 * layout
	 */
	public CardPanel() {
		setLayout(new CardLayout(0, 0));
		
		this.date = Calendar.getInstance();
		
		// Every panel you add needs a string name
		add(getAppointmentForm(), "Appointment");
		add(getContactForm(), "Contact");
		add(getDailyPanel(), "Day");
		add(getMonthlyPanel(), "Month");
		add(getYearlyPanel(), "Year");
		
	}
	
	public Calendar getDate() {
		return date;
	}
	
	/**
	 * Make a Panel
	 * 
	 * @return the panel
	 */
	private CalendarPanelYearlyView getYearlyPanel() {
		if (yearlyPanel == null) {
			yearlyPanel = new CalendarPanelYearlyView(this);
		}
		return yearlyPanel;
	}
	
	/**
	 * Make a Panel
	 * 
	 * @return the panel
	 */
	private CalendarPanelMonthlyView getMonthlyPanel() {
		if (monthlyPanel == null) {
			monthlyPanel = new CalendarPanelMonthlyView(this);
		}
		return monthlyPanel;
	}
	
	public void setMonthyPanel(Calendar date) {
		monthlyPanel.setDate(date);
		monthlyPanel.update();
	}
	
	/**
	 * Make a Panel
	 * 
	 * @return the panel
	 */
	private CalendarPanelDailyView getDailyPanel() {
		if (dailyPanel == null) {
			dailyPanel = new CalendarPanelDailyView(this, date);
		}
		return dailyPanel;
	}
	
	public void setDailyPanel(Calendar date) {
		dailyPanel.setDate(date);
		dailyPanel.update();
	}

	/**
	 * Make a Panel
	 * 
	 * @return the panel
	 */
	private AppointmentForm getAppointmentForm() {
		if (appForm == null) {
			appForm = new AppointmentForm();
		}
		
		return appForm;
	}
	
	public void editAppointmentForm(AppointmentBean ab, Calendar date) {
		appForm.setAppointmentBean(ab);
		appForm.setDate(date);
		appForm.update("edit");
	}
	
	public void addAppointmentForm(Calendar date) {
		appForm.setDate(date);
		appForm.update("add");
	}
	
	public void searchAppointmentForm() {
		appForm.setDate(date);
		appForm.update("search");
	}

	/**
	 * Make a Panel
	 * 
	 * @return the panel
	 */
	private ContactForm getContactForm() {
		if (conForm == null) {
			conForm = new ContactForm();
		}
		
		return conForm;
	}
	
	public void editContactForm(ContactBean cb) {
		conForm.setContactBean(cb);
		conForm.update("edit");
	}
	
	public void addContactForm() {
		conForm.update("add");
	}
	
	public void searchContactForm() {
		conForm.update("search");
	}
	

}
