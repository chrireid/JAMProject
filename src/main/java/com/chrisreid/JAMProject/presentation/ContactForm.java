package com.chrisreid.JAMProject.presentation;

import java.awt.Dimension;
import java.util.ResourceBundle;

import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chrisreid.JAMProject.data.AppointmentBean;
import com.chrisreid.JAMProject.data.ContactBean;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import java.util.Date;
import java.util.Calendar;
import java.util.Locale;

import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

/**
 * AppointmentForm class
 * 
 * Phase 3 
 * 
 * Description of this class
 * 
 * @author Christopher Reid 0934402
 */
public class ContactForm extends JPanel {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass()
			.getName());
	
	private ResourceBundle resources;
	private ContactBean cb;
	private Calendar date;
	
	private JFormattedTextField txtFirstName;
	private JFormattedTextField txtMiddleName;
	private JFormattedTextField txtLastName;
	private JFormattedTextField txtCompany;
	private JFormattedTextField txtAddress1;
	private JFormattedTextField txtAddress2;
	private JFormattedTextField txtAddress3;
	private JFormattedTextField txtCity;
	private JFormattedTextField txtProvince;
	private JFormattedTextField txtPostalCode;
	private JFormattedTextField txtCountry;
	private JFormattedTextField txtPhoneNumber;
	private JFormattedTextField txtCellNumber;
	private JFormattedTextField txtFaxNumber;
	private JFormattedTextField txtEmail; 
	
	private JButton btnSubmit;
	private JButton btnRemove;
	
	String phoneMask = "###-###-####"; //$NON-NLS-1$
    String postalMask = "U#U #U#"; //$NON-NLS-1$
	
	
	
	/**
	 * Default no-parameter constructor
	 */
	public ContactForm() {
		log.debug("ContactForm: No-Parameter constructor");
		
		setPreferredSize(new Dimension(600, 500));
		setLayout(null);
		
		date = Calendar.getInstance();
		
		constructPanel();
	}
	
	public void setContactBean(ContactBean cb) {
		this.cb = cb;
	}
	
	public void setDate(Calendar date) { 
		this.date = date;
	}
	
	private void constructPanel() {
		
		//Locale currentLocale = new Locale("en", "CA");
		resources = ResourceBundle.getBundle("FormBundle");
		
		JLabel lblContact = new JLabel(resources.getString("Contacts"));
		lblContact.setHorizontalAlignment(SwingConstants.CENTER);
		lblContact.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblContact.setBounds(10, 11, 420, 25);
		add(lblContact);
		
		JLabel lblFirstName = new JLabel(resources.getString("FirstName"));
		lblFirstName.setBounds(20, 56, 200, 14);
		add(lblFirstName);
		
		txtFirstName = new JFormattedTextField();
		txtFirstName.setBounds(10, 70, 200, 20);
		add(txtFirstName);
		
		JLabel lblMiddleName = new JLabel(resources.getString("MiddleName"));
		lblMiddleName.setBounds(20, 101, 200, 14);
		add(lblMiddleName);
		
		txtMiddleName = new JFormattedTextField();
		txtMiddleName.setBounds(10, 115, 200, 20);
		add(txtMiddleName);
		
		JLabel lblLastName= new JLabel(resources.getString("LastName"));
		lblLastName.setBounds(20, 146, 200, 14);
		add(lblLastName);
		
		txtLastName = new JFormattedTextField();
		txtLastName.setBounds(10, 160, 200, 20);
		add(txtLastName);
		
		JLabel lblCompany = new JLabel(resources.getString("CompanyName"));
		lblCompany.setBounds(20, 191, 200, 14);
		add(lblCompany);
		
		txtCompany = new JFormattedTextField();
		txtCompany.setBounds(10, 205, 200, 20);
		add(txtCompany);
		
		JLabel lblAddress1 = new JLabel(resources.getString("Address1"));
		lblAddress1.setBounds(240, 56, 200, 14);
		add(lblAddress1);
		
		txtAddress1 = new JFormattedTextField();
		txtAddress1.setBounds(230, 70, 200, 20);
		add(txtAddress1);
		
		JLabel lblAddress2 = new JLabel(resources.getString("Address2"));
		lblAddress2.setBounds(240, 101, 200, 14);
		add(lblAddress2);
		
		txtAddress2 = new JFormattedTextField();
		txtAddress2.setBounds(230, 115, 200, 20);
		add(txtAddress2);
		
		JLabel lblAddress3 = new JLabel(resources.getString("Address3"));
		lblAddress3.setBounds(240, 146, 200, 14);
		add(lblAddress3);
		
		txtAddress3 = new JFormattedTextField();
		txtAddress3.setBounds(230, 160, 200, 20);
		add(txtAddress3);
		
		JLabel lblCity = new JLabel(resources.getString("City"));
		lblCity.setBounds(240, 191, 200, 14);
		add(lblCity);
		
		txtCity = new JFormattedTextField();
		txtCity.setBounds(230, 205, 200, 20);
		add(txtCity);
		
		JLabel lblProvince = new JLabel(resources.getString("Province"));
		lblProvince.setBounds(240, 236, 200, 14);
		add(lblProvince);
		
		txtProvince = new JFormattedTextField();
		txtProvince.setBounds(230, 250, 200, 20);
		add(txtProvince);
		
		JLabel lblPostalCode = new JLabel(resources.getString("PostalCode"));
		lblPostalCode.setBounds(240, 281, 200, 14);
		add(lblPostalCode);
		
		txtPostalCode = new JFormattedTextField(createFormatter(postalMask));
		txtPostalCode.setBounds(230, 295, 200, 20);
		add(txtPostalCode);
		
		JLabel lblCountry = new JLabel(resources.getString("Country"));
		lblCountry.setBounds(240, 326, 200, 14);
		add(lblCountry);
		
		txtCountry = new JFormattedTextField();
		txtCountry.setBounds(230, 340, 200, 20);
		add(txtCountry);
		
		JLabel lblPhoneNumber = new JLabel(resources.getString("PhoneNumber"));
		lblPhoneNumber.setBounds(20, 236, 200, 14);
		add(lblPhoneNumber);
		
		txtPhoneNumber = new JFormattedTextField(createFormatter(phoneMask));
		txtPhoneNumber.setBounds(10, 250, 200, 20);
		add(txtPhoneNumber);
		
		JLabel lblCellNumber = new JLabel(resources.getString("CellNumber"));
		lblCellNumber.setBounds(20, 281, 200, 14);
		add(lblCellNumber);
		
		txtCellNumber = new JFormattedTextField(createFormatter(phoneMask));
		txtCellNumber.setBounds(10, 295, 200, 20);
		add(txtCellNumber);
		
		JLabel lblFaxNumber = new JLabel(resources.getString("FaxNumber"));
		lblFaxNumber.setBounds(20, 326, 200, 14);
		add(lblFaxNumber);
		
		txtFaxNumber = new JFormattedTextField(createFormatter(phoneMask));
		txtFaxNumber.setBounds(10, 340, 200, 20);
		add(txtFaxNumber);
		
		JLabel lblEmail = new JLabel(resources.getString("Email"));
		lblEmail.setBounds(20, 371, 200, 14);
		add(lblEmail);
		
		txtEmail = new JFormattedTextField();
		txtEmail.setBounds(10, 385, 200, 20);
		add(txtEmail);
		
		
		// Buttons ----------
		
		btnSubmit = new JButton();
		btnSubmit.setBounds(230, 380, 95, 25);
		
		btnRemove = new JButton();
		btnRemove.setBounds(335, 380, 95, 25);
		

		add(btnSubmit);
		add(btnRemove);
		
	}
	
	protected void update(String type) {
		

		for (ActionListener a : btnSubmit.getActionListeners()) {
			btnSubmit.removeActionListener(a);
		}
		
		for (ActionListener a : btnRemove.getActionListeners()) {
			btnRemove.removeActionListener(a);
		}
		
		switch (type) {
			case "add":
				btnSubmit.setText(resources.getString("Add"));
				btnSubmit.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						log.debug("Add Contact");
					}
				});
				btnRemove.setText(resources.getString("Reset"));
				btnRemove.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						log.debug("Reset Contact");
					}
				});
				break;
			case "edit":
				btnSubmit.setText(resources.getString("Edit"));
				btnSubmit.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						log.debug("Edit Contact");
					}
				});
				btnRemove.setText(resources.getString("Delete"));
				btnRemove.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						log.debug("Delete Contact");
					}
				});
				break;
			case "search":
				btnSubmit.setText(resources.getString("Search"));
				btnSubmit.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						log.debug("Search Contact");
					}
				});
				btnRemove.setText(resources.getString("Reset"));
				btnRemove.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						log.debug("Reset Contact");
					}
				});
				break;
		}
	}
	
	/**
     * This method returns a MaskFormatter for the JFormattedTextField
     *
     * @return javax.swing.MaskFormatter
     */
    private MaskFormatter createFormatter(String s) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
        } catch (java.text.ParseException exc) {
            System.err.println("formatter is bad: " + exc.getMessage()); //$NON-NLS-1$
            System.exit(-1);
        }
        return formatter;
    }
}
