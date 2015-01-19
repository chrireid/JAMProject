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
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CalendarPanelMonthlyView class 
 * 
 * Phase 3 
 * 
 * Description of this class
 * 
 * @author Christopher Reid 0934402
 */
@SuppressWarnings("serial")
public class CalendarPanelMonthlyView extends JPanel {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass()
			.getName());
	private ResourceBundle resources = ResourceBundle
			.getBundle("CalendarBundle");

	private TableModelMonthly model;
	private JTable table;
	private Calendar date;
	private JPanel northPanel;
	private JLabel lblMonth;
	private CardPanel cardPanel;
	private final int CELL_HEIGHT = 100;
	
	public CalendarPanelMonthlyView(CardPanel cardPanel) {
		
		super();
		
		log.debug("CalendarPanelMonthlyView: constructor");
		
		this.date = Calendar.getInstance();
		this.cardPanel = cardPanel;
		
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
		
		lblMonth = new JLabel();
		lblMonth.setHorizontalAlignment(SwingConstants.CENTER);
		lblMonth.setFont(new Font(lblMonth.getName(), Font.BOLD, 20));
		
		update();
		
		northPanel = new JPanel(new BorderLayout());
		
		// Set table properties
		table.setCellSelectionEnabled(true);
		table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setRowHeight(CELL_HEIGHT);
		
		table.addMouseListener(new MouseListener());
		
		JPanel monthPanel = new JPanel(new BorderLayout());
		monthPanel.add(table.getTableHeader(), BorderLayout.NORTH);
		monthPanel.add(table, BorderLayout.CENTER);
		
		JButton btnPrevious = new JButton(resources.getString("PreviousMonth"));
		btnPrevious.setPreferredSize(new Dimension(125, 25));
		btnPrevious.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// Add code here
				date.add(Calendar.MONTH, -1);
				
				update();
				
				log.debug("previous month clicked, date=" + date.getTime().toString());
			}
		});

		JButton btnNext = new JButton(resources.getString("NextMonth"));
		btnNext.setPreferredSize(new Dimension(125, 25));
		btnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Add code here
				date.add(Calendar.MONTH, 1);

				update();
				
				log.debug("next month clicked, date=" + date.getTime().toString());
			}
		});
		
		northPanel.add(lblMonth, BorderLayout.CENTER);
		northPanel.add(btnPrevious, BorderLayout.WEST);
		northPanel.add(btnNext, BorderLayout.EAST);
		
		add(northPanel, BorderLayout.NORTH);
		add(monthPanel, BorderLayout.CENTER);
	}
	
	class MouseListener extends MouseAdapter {
		
		public void mousePressed(MouseEvent e) {
			
			// Gather information about the click
			JTable t = (JTable) e.getSource();
			int row = t.rowAtPoint(e.getPoint());
			int col = t.columnAtPoint(e.getPoint());
			
			if (e.getClickCount() == 1) {
				
				// Add code here
				log.debug("Single click on row=" + row + ", col=" + col);
				log.debug("date: " + model.getDateAt(row, col));
				
			} else if (e.getClickCount() == 2) {
				
				// Add code here
				log.debug("Double click on row=" + row + ", col=" + col);
				
				int day = model.getDateAt(row, col);
				if (day != 0) {
					date.set(Calendar.DATE, day);
					cardPanel.setDailyPanel(date);

					log.debug("Showing day view for: " + date.getTime().toString());
					
					CardLayout cl = (CardLayout)(cardPanel.getLayout());
					cl.show(cardPanel, "Day");
					
				}
			}
		}
	}
	
	protected void update() {
		
		model = new TableModelMonthly(date);
		table.setModel(model);
		
		// Add renderer for coloring cells / html
		PaneRendererMonthly renderer = new PaneRendererMonthly();
		for (int i=0; i<model.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}
		

		lblMonth.setText(new SimpleDateFormat("MMMMM, yyyy").format(date.getTime()));
	}
	
	
	
	
}
