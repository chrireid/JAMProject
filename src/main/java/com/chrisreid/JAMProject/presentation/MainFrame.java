package com.chrisreid.JAMProject.presentation;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.KeyStroke;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.InputEvent;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * MainFrame class
 * 
 * Phase 2, 3
 * 
 * The frame that contains all program presentation panels.
 * 
 * @author Christopher Reid 0934402
 */
public class MainFrame extends JFrame {

	private JPanel contentPane;
	private ResourceBundle resources;
	private final Logger log = LoggerFactory.getLogger(this.getClass()
			.getName());

	private CardPanel cardPanel;
	private CardLayout cl;
	
	/**
	 * Create the application.
	 */
	public MainFrame() {

		// Locale currentLocale = new Locale("en", "CA");
		// Locale currentLocale = new Locale("fr","CA");
		// Locale currentLocale = Locale.CANADA;
		// Locale currentLocale = Locale.CANADA_FRENCH;

		// messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);
		resources = ResourceBundle.getBundle("MessagesBundle");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 800);

		// --- FILE MENU -----------------

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu(resources.getString("file"));
		mnFile.setMnemonic(KeyStroke.getKeyStroke(resources.getString("mnFile"))
				.getKeyCode());
		mnFile.getAccessibleContext().setAccessibleDescription("descFile");
		menuBar.add(mnFile);

		JMenu mnViewCalendar = new JMenu(resources.getString("viewCalendar"));
		mnViewCalendar.setIcon(new ImageIcon(MainFrame.class
				.getResource("/images/jam_menu_icon_calendarview.png")));
		mnViewCalendar.setMnemonic(KeyStroke.getKeyStroke(
				resources.getString("mnViewCalendar")).getKeyCode());
		mnViewCalendar.getAccessibleContext().setAccessibleDescription(
				"descViewCalendar");
		mnFile.add(mnViewCalendar);

		JMenuItem mntmAnnualView = new JMenuItem(
				resources.getString("annualView"));
		mntmAnnualView.setIcon(new ImageIcon(MainFrame.class
				.getResource("/images/jam_menu_icon_yearlyview.png")));
		mntmAnnualView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				log.debug("MouseClick on AnnualView");
				viewYearlyCalendar();
			}
		});
		mntmAnnualView.setMnemonic(KeyStroke.getKeyStroke(
				resources.getString("mnAnnualView")).getKeyCode());
		mntmAnnualView.setAccelerator(KeyStroke.getKeyStroke(KeyStroke
				.getKeyStroke(resources.getString("mnAnnualView")).getKeyCode(),
				InputEvent.ALT_MASK));
		mntmAnnualView.getAccessibleContext().setAccessibleDescription(
				"descAnnualView");
		mnViewCalendar.add(mntmAnnualView);

		JMenuItem mntmMonthlyView = new JMenuItem(
				resources.getString("monthlyView"));
		mntmMonthlyView.setIcon(new ImageIcon(MainFrame.class
				.getResource("/images/jam_menu_icon_monthlyview.png")));
		mntmMonthlyView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				log.debug("MouseClick on MonthlyView");
				viewMonthlyCalendar();
			}
		});
		mntmMonthlyView.setMnemonic(KeyStroke.getKeyStroke(
				resources.getString("mnMonthlyView")).getKeyCode());
		mntmMonthlyView.setAccelerator(KeyStroke.getKeyStroke(
				KeyStroke.getKeyStroke(resources.getString("mnMonthlyView"))
						.getKeyCode(), InputEvent.ALT_MASK));
		mntmMonthlyView.getAccessibleContext().setAccessibleDescription(
				"descMonthlyView");
		mnViewCalendar.add(mntmMonthlyView);

		JMenuItem mntmDailyView = new JMenuItem(resources.getString("dailyView"));
		mntmDailyView.setIcon(new ImageIcon(MainFrame.class
				.getResource("/images/jam_menu_icon_dailyview.png")));
		mntmDailyView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				log.debug("MouseClick on DailyView");
				viewDailyCalendar();
			}
		});
		mntmDailyView.setMnemonic(KeyStroke.getKeyStroke(
				resources.getString("mnDailyView")).getKeyCode());
		mntmDailyView.setAccelerator(KeyStroke.getKeyStroke(KeyStroke
				.getKeyStroke(resources.getString("mnDailyView")).getKeyCode(),
				InputEvent.ALT_MASK));
		mntmDailyView.getAccessibleContext().setAccessibleDescription(
				"descDailyView");
		mnViewCalendar.add(mntmDailyView);

		
		JMenuItem mntmViewContacts = new JMenuItem(
				resources.getString("viewContacts"));
		mntmViewContacts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				log.debug("MouseClick on ViewContacts");
			}
		});
		mntmViewContacts.setIcon(new ImageIcon(MainFrame.class
				.getResource("/images/jam_menu_icon_contactview.png")));
		mntmViewContacts.setMnemonic(KeyStroke.getKeyStroke(
				resources.getString("mnViewContacts")).getKeyCode());
		mntmViewContacts.setAccelerator(KeyStroke.getKeyStroke(KeyStroke
				.getKeyStroke(resources.getString("mnViewContacts"))
				.getKeyCode(), InputEvent.ALT_MASK));
		mntmViewContacts.getAccessibleContext().setAccessibleDescription(
				"descViewContacts");
		mnFile.add(mntmViewContacts);

		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(Color.LIGHT_GRAY);
		mnFile.add(separator_2);

		JMenuItem mntmExit = new JMenuItem(resources.getString("exit"));
		mntmExit.setMnemonic(KeyStroke.getKeyStroke(
				resources.getString("mnExit")).getKeyCode());
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(
				KeyStroke.getKeyStroke(resources.getString("mnExit"))
						.getKeyCode(), InputEvent.ALT_MASK));
		mntmExit.getAccessibleContext().setAccessibleDescription("descExit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				log.debug("MouseClick on Exit");
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);

		// --- APPOINTMENTS MENU ---------

		JMenu mnAppointment = new JMenu(resources.getString("appointments"));
		mnAppointment.setMnemonic(KeyStroke.getKeyStroke(
				resources.getString("mnAppointment")).getKeyCode());
		mnAppointment.getAccessibleContext().setAccessibleDescription(
				"descAppointment");
		menuBar.add(mnAppointment);

		JMenuItem mntmAddAppointment = new JMenuItem(
				resources.getString("addApp"));
		mntmAddAppointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				log.debug("MouseClick on AddAppointment");
				addAppointmentForm();
			}
		});
		mntmAddAppointment.setMnemonic(KeyStroke.getKeyStroke(
				resources.getString("mnAddApp")).getKeyCode());
		mntmAddAppointment.setAccelerator(KeyStroke.getKeyStroke(KeyStroke
				.getKeyStroke(resources.getString("mnAddApp")).getKeyCode(),
				InputEvent.ALT_MASK));
		mntmAddAppointment.getAccessibleContext().setAccessibleDescription(
				"descAddApp");
		mnAppointment.add(mntmAddAppointment);

		JMenuItem mntmFindAppointment = new JMenuItem(
				resources.getString("findApp"));
		mntmFindAppointment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				log.debug("MouseClick on FindAppointment");
				searchAppointmentForm();
			}
		});
		mntmFindAppointment.setMnemonic(KeyStroke.getKeyStroke(
				resources.getString("mnFindApp")).getKeyCode());
		mntmFindAppointment.setAccelerator(KeyStroke.getKeyStroke(KeyStroke
				.getKeyStroke(resources.getString("mnFindApp")).getKeyCode(),
				InputEvent.ALT_MASK));
		mntmFindAppointment.getAccessibleContext().setAccessibleDescription(
				"descFindApp");
		mnAppointment.add(mntmFindAppointment);

		// --- CONTACTS MENU -------------

		JMenu mnContact = new JMenu(resources.getString("contacts"));
		mnContact.setMnemonic(KeyStroke.getKeyStroke(
				resources.getString("mnContacts")).getKeyCode());
		mnContact.getAccessibleContext().setAccessibleDescription(
				"descContacts");
		menuBar.add(mnContact);

		JMenuItem mntmAddContact = new JMenuItem(resources.getString("addCon"));
		mntmAddContact.setIcon(new ImageIcon(MainFrame.class
				.getResource("/images/jam_menu_icon_addcontact.png")));
		mntmAddContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				log.debug("MouseClick on AddContact");
				addContactForm();
				
			}
		});
		mntmAddContact.setMnemonic(KeyStroke.getKeyStroke(
				resources.getString("mnAddCon")).getKeyCode());
		mntmAddContact.setAccelerator(KeyStroke.getKeyStroke(KeyStroke
				.getKeyStroke(resources.getString("mnAddCon")).getKeyCode(),
				InputEvent.ALT_MASK));
		mntmAddContact.getAccessibleContext().setAccessibleDescription(
				"descAddCon");
		mnContact.add(mntmAddContact);

		JMenuItem mntmFindContact = new JMenuItem(resources.getString("findCon"));
		mntmFindContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				log.debug("MouseClick on FindContact");
				searchContactForm();
			}
		});
		mntmFindContact.setMnemonic(KeyStroke.getKeyStroke(
				resources.getString("mnFindCon")).getKeyCode());
		mntmFindContact.setAccelerator(KeyStroke.getKeyStroke(KeyStroke
				.getKeyStroke(resources.getString("mnFindCon")).getKeyCode(),
				InputEvent.ALT_MASK));
		mntmFindContact.getAccessibleContext().setAccessibleDescription(
				"descFindCon");
		mnContact.add(mntmFindContact);
		
		// --- HELP MENU -----------------

		JMenu mnHelp = new JMenu(resources.getString("help"));
		mnHelp.setMnemonic(KeyStroke.getKeyStroke(resources.getString("mnHelp"))
				.getKeyCode());
		mnHelp.getAccessibleContext().setAccessibleDescription("descHelp");
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem(resources.getString("about"));
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				log.debug("MouseClick on About");
			}
		});
		mntmAbout.setMnemonic(KeyStroke.getKeyStroke(
				resources.getString("mnAbout")).getKeyCode());
		mntmAbout.setAccelerator(KeyStroke.getKeyStroke(
				KeyStroke.getKeyStroke(resources.getString("mnAbout"))
						.getKeyCode(), InputEvent.ALT_MASK));
		mntmAbout.getAccessibleContext().setAccessibleDescription("descAbout");
		mnHelp.add(mntmAbout);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JToolBar toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.NORTH);

		JButton btnAddContact = new JButton("");
		btnAddContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnAddContact.setToolTipText(resources.getString("toolContact"));
		btnAddContact.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				log.debug("MouseClick on Toolbar AddContact");
				addContactForm();
			}
		});
		btnAddContact.setIcon(new ImageIcon(MainFrame.class
				.getResource("/images/jam_toolbar_icon_addcontact.png")));
		toolBar.add(btnAddContact);
		
		JButton btnYearlyView = new JButton("");
		btnYearlyView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnYearlyView.setToolTipText(resources.getString("toolYearly"));
		btnYearlyView.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				log.debug("MouseClick on Toolbar YearlyView");
				viewYearlyCalendar();
			}
		});
		btnYearlyView.setIcon(new ImageIcon(MainFrame.class
				.getResource("/images/jam_toolbar_icon_yearlyview.png")));
		toolBar.add(btnYearlyView);

		JButton btnMonthlyView = new JButton("");
		btnMonthlyView.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				log.debug("MouseClick on Toolbar MonthlyView");
				viewMonthlyCalendar();
			}
		});
		btnMonthlyView.setToolTipText(resources.getString("toolMonthly"));
		btnMonthlyView.setIcon(new ImageIcon(MainFrame.class
				.getResource("/images/jam_toolbar_icon_monthlyview.png")));
		toolBar.add(btnMonthlyView);

		JButton btnDailyView = new JButton("");
		btnDailyView.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				log.debug("MouseClick on Toolbar DailyView");
				viewDailyCalendar();
			}
		});
		btnDailyView.setToolTipText(resources.getString("toolDaily"));
		btnDailyView.setIcon(new ImageIcon(MainFrame.class
				.getResource("/images/jam_toolbar_icon_dailyview.png")));
		toolBar.add(btnDailyView);

		cardPanel = new CardPanel();
		cl = (CardLayout)(cardPanel.getLayout());
		
		contentPane.add(cardPanel, BorderLayout.CENTER);
		viewMonthlyCalendar();
	}
	
	private void viewDailyCalendar() {
		log.debug("view daily calendar");
		cl.show(cardPanel, "Day");
	}
	
	private void viewMonthlyCalendar() {
		log.debug("view monthly calendar");
		cl.show(cardPanel, "Month");
	}
	
	private void viewYearlyCalendar() {
		log.debug("view yearly calendar");
		cl.show(cardPanel, "Year");
	}
	
	private void addAppointmentForm() {
		log.debug("add appointment form");
		cardPanel.addAppointmentForm(Calendar.getInstance());
		cl.show(cardPanel, "Appointment");
	}
	
	private void searchAppointmentForm() {
		log.debug("search appointment form");
		cardPanel.searchAppointmentForm();
		cl.show(cardPanel, "Appointment");
	}
	
	private void addContactForm() {
		log.debug("view contact form");
		cardPanel.addContactForm();
		cl.show(cardPanel, "Contact");
	}
	
	private void searchContactForm() {
		log.debug("view contact form");
		cardPanel.searchContactForm();
		cl.show(cardPanel, "Contact");
	}
	
	

}
