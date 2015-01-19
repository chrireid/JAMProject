package com.chrisreid.JAMProject.presentation;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CalendarPanelYearlyView class
 * 
 * Phase 3
 * 
 * Description of this class
 * 
 * @author Christopher Reid 0934402
 */
public class CalendarPanelYearlyView extends JPanel {

	private final Logger log = LoggerFactory.getLogger(this.getClass()
			.getName());
	private ResourceBundle resources = ResourceBundle
			.getBundle("CalendarBundle");

	private JPanel panel;
	private Calendar date;
	private JPanel northPanel;
	private JLabel lblYear;
	private CardPanel cardPanel;
	private JPanel[] months;
	CalendarPanelMonthInYear[] monthlyPanel;

	/**
	 * No-parameter constructor Creates the year panel with the current system's
	 * date.
	 */
	public CalendarPanelYearlyView(CardPanel cardPanel) {
		
		this.cardPanel = cardPanel;
		date = Calendar.getInstance();

		constructPanel();
	}

	public CalendarPanelYearlyView(CardPanel cardPanel, Calendar date) {
		
		this.cardPanel = cardPanel;
		this.date = date;

		constructPanel();

	}

	private void constructPanel() {
		
		setLayout(new BorderLayout());
		panel = new JPanel(new GridLayout(3, 4, 20, 20));
		
		months = new JPanel[12];
		monthlyPanel = new CalendarPanelMonthInYear[12];
		for (int i=0; i<months.length; i++) {
			
			months[i] = new JPanel(new BorderLayout());
			date.set(Calendar.MONTH, i);
			Calendar c = (Calendar) date.clone();
			
			JLabel lblMonth = new JLabel();
			lblMonth.setText(new SimpleDateFormat("MMMMM").format(c.getTime()));
			lblMonth.setHorizontalAlignment(SwingConstants.CENTER);
//			lblMonth.addMouseListener(new MouseAdapter() {
//				@Override
//				public void mousePressed(MouseEvent e) {
//					
//					//determine month and set the month in card panel
//					cardPanel.setMonthyPanel(date);
//			
//					CardLayout cl = (CardLayout)(cardPanel.getLayout());
//					cl.show(cardPanel, "Month");
//					
//				}
//			});
			
			monthlyPanel[i] = new CalendarPanelMonthInYear(cardPanel, c);

			months[i].add(lblMonth, BorderLayout.NORTH);
			months[i].add(monthlyPanel[i], BorderLayout.CENTER);
			panel.add(months[i]);
		}
		
		
		lblYear = new JLabel();
		lblYear.setHorizontalAlignment(SwingConstants.CENTER);
		lblYear.setFont(new Font(lblYear.getName(), Font.BOLD, 20));
		lblYear.setText(new SimpleDateFormat("yyyy").format(date.getTime()));
		
		northPanel = new JPanel(new BorderLayout());
		
		JButton btnPrevious = new JButton(resources.getString("PreviousYear"));
		btnPrevious.setPreferredSize(new Dimension(125, 25));
		btnPrevious.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// Add code here
				date.add(Calendar.YEAR, -1);
				
				update();
				
				log.debug("previous year clicked, date=" + date.getTime().toString());
			}
		});

		JButton btnNext = new JButton(resources.getString("NextYear"));
		btnNext.setPreferredSize(new Dimension(125, 25));
		btnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Add code here
				date.add(Calendar.YEAR, 1);

				update();
				
				log.debug("next year clicked, date=" + date.getTime().toString());
			}
		});
		
		northPanel.add(lblYear, BorderLayout.CENTER);
		northPanel.add(btnPrevious, BorderLayout.WEST);
		northPanel.add(btnNext, BorderLayout.EAST);
		
		add(northPanel, BorderLayout.NORTH);
		add(panel, BorderLayout.CENTER);
	}
	
	protected void update() {
		
		for (int i=0;i<monthlyPanel.length; i++) {
			Calendar c = (Calendar) date.clone();
			c.set(Calendar.MONTH, i);
			
			log.debug("month, year:" + c.get(Calendar.MONTH) + ", " + c.get(Calendar.YEAR));
	
			monthlyPanel[i].setDate(c);
			monthlyPanel[i].update();
		}

		lblYear.setText(new SimpleDateFormat("yyyy").format(date.getTime()));
	}
}
