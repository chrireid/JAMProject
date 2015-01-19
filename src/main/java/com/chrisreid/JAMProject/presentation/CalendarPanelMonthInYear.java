package com.chrisreid.JAMProject.presentation;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chrisreid.JAMProject.presentation.CalendarPanelMonthlyView.MouseListener;

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
public class CalendarPanelMonthInYear extends JPanel {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass()
			.getName());
	private ResourceBundle resources = ResourceBundle
			.getBundle("CalendarBundle");
	

	private TableModelYearly model;
	private Calendar date;
	private CardPanel cardPanel;
	private JTable table;
	private final int CELL_HEIGHT = 25;
	private final int CELL_WIDTH = 35;
	
	public CalendarPanelMonthInYear(CardPanel cardPanel, Calendar date) {
		
		this.cardPanel = cardPanel;
		this.date = date;
		

		constructPanel();
	}
	
	public Calendar getDate() {
		return date;
	}
	
	public void setDate(Calendar date) {
		this.date = date;
	}
	

	private void constructPanel() {
		

		table = new JTable();
		
		update();
		
		// Set table properties
		table.setCellSelectionEnabled(true);
		table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setRowHeight(CELL_HEIGHT);
		
		table.addMouseListener(new MouseListener());

		add(table.getTableHeader(), BorderLayout.NORTH);
		add(table, BorderLayout.CENTER);
		
	}
	
	class MouseListener extends MouseAdapter {
		
		public void mousePressed(MouseEvent e) {
			
			// Gather information about the click
			JTable t = (JTable) e.getSource();
			int row = t.rowAtPoint(e.getPoint());
			int col = t.columnAtPoint(e.getPoint());
			
			date.set(Calendar.DATE, model.getDateAt(row, col));
			
			if (e.getClickCount() == 1) {
				
				// Add code here
				log.debug("Single click on row=" + row + ", col=" + col);
				log.debug("date: " + date.getTime().toString());
				
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
		
		model = new TableModelYearly(date);
		table.setModel(model);
		
		// Add renderer for coloring cells / html
		PaneRendererYearly renderer = new PaneRendererYearly();
		for (int i=0; i<model.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(renderer);
			table.getColumnModel().getColumn(i).setPreferredWidth(CELL_WIDTH);
		}
	}
}
