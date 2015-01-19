package com.chrisreid.JAMProject.data;

/**
 * ContactBean class
 * 
 * Phase 1
 * 
 * The bean for contacts.
 * 
 * @author Christopher Reid 0934402
 */
public class ContactBean {

	private int id;
	private String lastName;
	private String firstName;
	private String middleName;
	private String companyName;
	private String address1;
	private String address2;
	private String address3;
	private String city;
	private String province;
	private String postalCode;
	private String country;
	private String phoneNumber;
	private String cellNumber;
	private String faxNumber;
	private String email;

	public ContactBean() {
		super();
		this.id = -1;
		this.lastName = "";
		this.firstName = "";
		this.middleName = "";
		this.companyName = "";
		this.address1 = "";
		this.address2 = "";
		this.address3 = "";
		this.city = "";
		this.province = "";
		this.postalCode = "";
		this.country = "";
		this.phoneNumber = "";
		this.cellNumber = "";
		this.faxNumber = "";
		this.email = "";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCellNumber() {
		return cellNumber;
	}

	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "ContactBean [id=" + id + ", lastName=" + lastName
				+ ", firstName=" + firstName + ", middleName=" + middleName
				+ ", companyName=" + companyName + ", address1=" + address1
				+ ", address2=" + address2 + ", address3=" + address3
				+ ", city=" + city + ", province=" + province + ", postalCode="
				+ postalCode + ", country=" + country + ", phoneNumber="
				+ phoneNumber + ", cellNumber=" + cellNumber + ", faxNumber="
				+ faxNumber + ", email=" + email + "]";
	}

}
