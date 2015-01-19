package com.chrisreid.JAMProject.data;

import java.util.Date;
import java.sql.Timestamp;

/**
 * AppointmentBean class
 * 
 * Phase 1
 * 
 * The bean for appointments.
 * 
 * @author Christopher Reid 0934402
 */
public class AppointmentBean {

	private int id;
	private String title;
	private String location;
	private Timestamp startTime;
	private Timestamp endTime;
	private String details;
	private Boolean allDay;
	private Boolean alarmReminder;
	private Timestamp created;

	public AppointmentBean() {
		super();
		this.id = -1;
		this.title = "";
		this.location = "";
		this.startTime = null;
		this.endTime = null;
		this.details = "";
		this.allDay = false;
		this.alarmReminder = false;
		this.created = new Timestamp(new Date().getTime());

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Boolean isAllDay() {
		return allDay;
	}

	public void setAllDay(Boolean allDay) {
		this.allDay = allDay;
	}

	public Boolean isAlarmReminder() {
		return alarmReminder;
	}

	public void setAlarmReminder(Boolean alarmReminder) {
		this.alarmReminder = alarmReminder;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + (id);
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result
				+ ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((details == null) ? 0 : details.hashCode());
		result = prime * result + ((allDay == null) ? 0 : allDay.hashCode());
		result = prime * result
				+ ((alarmReminder == null) ? 0 : alarmReminder.hashCode());
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		AppointmentBean other = (AppointmentBean) obj;
		if (id != other.id) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		if (location == null) {
			if (other.location != null) {
				return false;
			}
		} else if (!location.equals(other.location)) {
			return false;
		}
		if (startTime == null) {
			if (other.startTime != null) {
				return false;
			}
		} else if (!startTime.equals(other.startTime)) {
			return false;
		}
		if (endTime == null) {
			if (other.endTime != null) {
				return false;
			}
		} else if (!endTime.equals(other.endTime)) {
			return false;
		}
		if (details == null) {
			if (other.details != null) {
				return false;
			}
		} else if (!details.equals(other.details)) {
			return false;
		}
		if (allDay == null) {
			if (other.allDay != null) {
				return false;
			}
		} else if (!allDay.equals(other.allDay)) {
			return false;
		}
		if (alarmReminder == null) {
			if (other.alarmReminder != null) {
				return false;
			}
		} else if (!alarmReminder.equals(other.alarmReminder)) {
			return false;
		}
		if (created == null) {
			if (other.created != null) {
				return false;
			}
		} else if (!created.equals(other.created)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "AppointmentBean \n" + "[id]=" + id + "\n[title]=" + title
				+ "\n-[START] \t" + startTime + "\n-[END] \t\t" + endTime
				+ "\n[location]=" + location + "\n[details]=" + details
				+ "\n[allDay]=" + allDay + "\n[alarmReminder]=" + alarmReminder
				+ "\n[created]=" + created;
	}

}
