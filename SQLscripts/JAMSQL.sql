USE D934402;

DROP TABLE IF EXISTS contacts;

CREATE TABLE contacts (
  ID int(11) zerofill NOT NULL auto_increment,
  LASTNAME varchar(35) NOT NULL default '',
  FIRSTNAME varchar(35) NOT NULL default '',
  MIDDLENAME varchar(35) NOT NULL default '',
  COMPANYNAME varchar(35) NOT NULL default '',
  ADDRESS1 varchar(35) NOT NULL default '',
  ADDRESS2 varchar(35) NOT NULL default '',
  ADDRESS3 varchar(35) NOT NULL default '',
  CITY varchar(30) NOT NULL default '',
  /* Use province/state abbreviations */
  PROVINCE varchar(5) NOT NULL default '',
  POSTALCODE varchar(9) NOT NULL default '',
  /* Spell out whole country name */
  COUNTRY varchar(48) NOT NULL default '',
  /* Use 3 character country code */
  /* COUNTRY char(3) NOT NULL default '', */
  PHONENUMBER varchar(15) NOT NULL default '',
  CELLNUMBER varchar(15) NOT NULL default '',
  FAXNUMBER varchar(15) NOT NULL default '',
  EMAIL varchar(50) NOT NULL default '',
  PRIMARY KEY  (ID)
) ENGINE=InnoDB;

DROP TABLE IF EXISTS appointments;

CREATE TABLE appointments (
  ID int(11) zerofill NOT NULL auto_increment,
  TITLE varchar(35) NOT NULL default '',
  LOCATION varchar(35) NOT NULL default '',
  STARTTIME datetime,
  ENDTIME datetime,
  DETAILS varchar(250) NOT NULL default '',
  ALLDAY boolean NOT NULL default false,
  ALARMREMINDER boolean NOT NULL default false,
  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY  (ID)
) ENGINE=InnoDB;
  

