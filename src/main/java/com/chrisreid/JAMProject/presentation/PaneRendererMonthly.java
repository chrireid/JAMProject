package com.chrisreid.JAMProject.presentation;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JEditorPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SuppressWarnings("serial")
class PaneRendererMonthly extends JEditorPane implements TableCellRenderer {
	@SuppressWarnings("unused")
	private final Logger log = LoggerFactory.getLogger(this.getClass()
			.getName());

	/**
	 * Constructor set the JEditorPane to accept HTML
	 */
	public PaneRendererMonthly() {
		setContentType("text/html");
		//Font tableFont = new Font(Font.SANS_SERIF, Font.PLAIN, 24);
		//this.setFont(tableFont);
	}
	
	/*
	 * This method is called every time the JTable needs to update a cell. The
	 * update can occur because a new object is being written to the cell or if
	 * the cell is selected. The contents of the styledString should be HTML
	 * that contains what will appear in the cell.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax
	 * .swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	public Component getTableCellRendererComponent(JTable table,
			Object styledString, boolean isSelected, boolean hasFocus, int row,
			int column) {
				
		if (isSelected) {
			String styledText = (String) styledString;
			setText(styledText);
			setBackground(getSelectionColor());
		} else {
			String styledText = (String) styledString;
			setText(styledText);
			setBackground(Color.white);
		}
		
		return this;
	}
}

