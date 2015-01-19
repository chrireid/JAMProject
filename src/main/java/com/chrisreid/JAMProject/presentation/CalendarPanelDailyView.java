package com.chrisreid.JAMProject.presentation;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chrisreid.JAMProject.data.AppointmentBean;

/**
 * CalendarPanelDailyView class 
 * 
 * Phase 3 
 * 
 * Description of this class
 * 
 * @author Christopher Reid 0934402
 */
@SuppressWarnings("serial")
public class CalendarPanelDailyView extends JPanel {

	private final Logger log = LoggerFactory.getLogger(this.getClass()
			.getName());
	private ResourceBundle resources = ResourceBundle
			.getBundle("CalendarBundle");
	
	private TableModelDaily model;
	private Calendar date;
	private JTable table;
	private JScrollPane scrollPane;
	private JPanel northPanel;
	private JLabel lblDate;
	private CardPanel cardPanel;
	
	private final int CELL_HEIGHT = 50;
	
	
	public CalendarPanelDailyView (CardPanel cardPanel, Calendar date) {
		super();
		
		log.debug("CalendarPanelDailyyView: constructor");
		
		this.date = date;
		this.cardPanel = cardPanel;
		log.debug("date: " + date.getTime().toString());
		
		constructPanel();
	}
	
	public Calendar getDate() {
		return date;
	}
	
	public void setDate(Calendar date) {
		this.date = date;
	}
	
	private void constructPanel() {
		
		setLayout(new BorderLayout());
		
		
		table = new JTable();

		lblDate = new JLabel();
		lblDate.setFont(new Font(lblDate.getName(), Font.BOLD, 20));
		lblDate.setHorizontalAlignment(SwingConstants.CENTER);
		
		update();
		
		// Set table properties
		table.setCellSelectionEnabled(true);
		table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getColumnModel().getColumn(1).setPreferredWidth(500);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setRowHeight(CELL_HEIGHT);
		
		table.addMouseListener(new MouseListener());
		

		
		
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);
		
		northPanel = new JPanel(new BorderLayout());
		
		JButton btnPrevious = new JButton(resources.getString("PreviousDay"));
		btnPrevious.setPreferredSize(new Dimension(125, 25));
		btnPrevious.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// Add code here
				date.add(Calendar.DAY_OF_MONTH, -1);
				
				update();
				
				log.debug("previous day clicked, date=" + date.getTime().toString());
			}
		});

		JButton btnNext = new JButton(resources.getString("NextDay"));
		btnNext.setPreferredSize(new Dimension(125, 25));
		btnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Add code here
				date.add(Calendar.DAY_OF_MONTH, 1);
				
				update();

				log.debug("next day clicked, date=" + date.getTime().toString());
			}
		});
		
		northPanel.add(lblDate, BorderLayout.CENTER);
		northPanel.add(btnPrevious, BorderLayout.WEST);
		northPanel.add(btnNext, BorderLayout.EAST);
		
		add(northPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
	}

	
	class MouseListener extends MouseAdapter {
	
		public void mousePressed(MouseEvent e) {
			
			// Gather information about the click
			JTable t = (JTable) e.getSource();
			int row = t.rowAtPoint(e.getPoint());
			int col = t.columnAtPoint(e.getPoint());
			
			date.setTimeInMillis(Timestamp.valueOf(date.get(Calendar.YEAR) + "-"
					+ (date.get(Calendar.MONTH) + 1) + "-"
					+ date.get(Calendar.DATE) + " " 
					+ (String) model.getValueAt(row, 0) + ":00.000").getTime());
			
			if (e.getClickCount() == 1) {
				
				// Add code here
				log.debug("Single click on row=" + row + ", col=" + col);
				
				log.debug("timeblock: " + date.getTime().toString());
				
			} else if (e.getClickCount() == 2) {
				
				// Add code here
				log.debug("Double click on row=" + row + ", col=" + col);
				
				if (!t.getValueAt(row, 1).equals("") && !t.getValueAt(row, 1).equals(" ")) {
					// view appointment
					
					//findAppointmentByTitle(string title)
					String title = (String)t.getValueAt(row, 1);
					title = title.substring(title.indexOf("<b>")+3, title.indexOf("</b>"));
					log.debug("title=" + title);
					
					ArrayList<AppointmentBean> ab = model.findAppointmentByTitle(title);
					log.debug("appointments: " + ab.size());
					
					AppointmentBean send = null;
					if (ab.size() > 0) {
						send = ab.get(0);
					} 
					
					cardPanel.editAppointmentForm(send, date);
					log.debug("sending bean to appointment form");
					CardLayout cl = (CardLayout)(cardPanel.getLayout());
					cl.show(cardPanel, "Appointment");
					
					
				} else {
					// add appointment
					
					cardPanel.addAppointmentForm(date);

					log.debug("Showing blank appointment form");
					
					CardLayout cl = (CardLayout)(cardPanel.getLayout());
					cl.show(cardPanel, "Appointment");
					
				}
				
			}
		}
	}
	
	protected void update() {
		
		model = new TableModelDaily(date);
		table.setModel(model);
		
		table.getColumnModel().getColumn(1).setPreferredWidth(500);
		
		// Add renderer for coloring cells / html
		PaneRendererDaily renderer = new PaneRendererDaily();
		for (int i=0; i<model.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}
		
		lblDate.setText(new SimpleDateFormat("EEEE, MMM d, yyyy").format(date.getTime()));
	}
}


