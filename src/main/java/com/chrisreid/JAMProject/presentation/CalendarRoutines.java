package com.chrisreid.JAMProject.presentation;

import java.util.ArrayList;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CalendarRoutines class 
 * Phase 3 
 * Description of this class
 * 
 * @author Christopher Reid 0934402
 */
public class CalendarRoutines {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass()
			.getName());
	

	/**
	 * The default no param constructor for the calendar routines. 
	 */
	public CalendarRoutines() {
		super();
	}
	
	public boolean[][] ifDayInMonth(Calendar c) {
		
		Calendar month = (Calendar) c.clone();
		boolean[][] grid = new boolean[6][7];
		
		int actualDay = month.get(Calendar.DATE);
		int numberOfDaysInMonth = month.getActualMaximum(Calendar.DATE);
		int week = 0;
		
		// Set the day to the first of the month
		month.set(Calendar.DATE, 1);
		
		// Handle first week of the month structure
		if (month.get(Calendar.DAY_OF_WEEK) == 1) {
			
			// If the first day of the month is a Sunday
			// ... skip a week in the grid
			for (int i=0; i<7; i++) {
				grid[week][i] = false;
			}
			week++;
		} else {
			// If the first day of the month is not a Sunday
			// ... start the week
			for (int i=0; i<7; i++) {
				
				if (month.get(Calendar.DAY_OF_WEEK) > (i+1)) {
					grid[week][i] = false;
				} else {
					grid[week][i] = true;
					month.add(Calendar.DATE, 1);
				}
			}
			week++;
		}
		
		
		log.debug("date: " + month.get(Calendar.DATE));
		
		// Finish the month
		for (int i=week; i<6; i++) {
			for (int j=0; j<7; j++) {
				
				if (numberOfDaysInMonth >= actualDay) {
					grid[i][j] = true;
					actualDay++;
				} else {
					grid[i][j] = false;
				}
			}
		}
		
		return grid;
	}
	
	public String[][] getMonthlyStructure(Calendar c) {
		
		// initialize array
		String[][] grid = new String[6][7];

		Calendar month = (Calendar) c.clone();
		int numberOfDaysInMonth = month.getActualMaximum(Calendar.DATE);
		int week = 0;
		int day = 1;
		
		log.debug("month: " + month.get(Calendar.MONTH));
		log.debug("number of days in month: " + numberOfDaysInMonth);
		log.debug("first day of month: " + month.get(Calendar.DAY_OF_WEEK));
		
		// Set the day to the first of the month
		month.set(Calendar.DATE, 1);
		
		// Handle first week of the month structure
		if (month.get(Calendar.DAY_OF_WEEK) == 1) {
			
			// If the first day of the month is a Sunday
			// ... skip a week in the grid
			for (int i=0; i<7; i++) {
				grid[week][i] = "";
			}
			week++;
		} else {
			// If the first day of the month is not a Sunday
			// ... start the week
			for (int i=0; i<7; i++) {
				
				if (month.get(Calendar.DAY_OF_WEEK) > (i+1)) {
					grid[week][i] = "";
				} else {
					grid[week][i] = String.valueOf(day);
					month.add(Calendar.DATE, 1);
					day++;
				}
			}
			week++;
		}
		
		// Finish the month
		for (int i=week; i<6; i++) {
			for (int j=0; j<7; j++) {
				
				if (numberOfDaysInMonth >= day) {
					grid[i][j] = String.valueOf(day);
					day++;
				} else {
					grid[i][j] = "";
				}
			}
		}
		
		return grid;
	}
	
}


