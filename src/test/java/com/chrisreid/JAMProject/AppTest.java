package com.chrisreid.JAMProject;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	ContactTests.class,
	AppointmentTests.class
	})
public class AppTest {

}