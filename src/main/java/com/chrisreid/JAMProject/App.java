package com.chrisreid.JAMProject;

import java.awt.EventQueue;

import com.chrisreid.JAMProject.presentation.MainFrame;

/**
 * App class 
 * 
 * Phase 3
 * 
 * This is the app class that runs the program.
 * 
 * @author Christopher Reid 0934402
 */
public class App 
{
    public static void main( String[] args )
    {
    	
    	EventQueue.invokeLater(new Runnable() {
    		public void run() {
    			try {
    				MainFrame frame = new MainFrame();
    				frame.setVisible(true);
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    		}
    	});
    }
}
