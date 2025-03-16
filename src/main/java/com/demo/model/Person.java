package com.demo.model;

//import java.beans.Transient;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL) // ✅ Excludes null fields
public class Person {
	
	private int Age;
	
	private String Name;
	
	private String Occupation;

	private String Status;
	
	@JsonIgnore
	private Address address;
	
	public int getAge() {
		return Age;
	}

	public void setAge(int age) {
		Age = age;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getOccupation() {
		return Occupation;
	}

	public void setOccupation(String occupation) {
		Occupation = occupation;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}
	
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.Age, this.Name, this,Occupation, this.Status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		return Age == other.Age && Objects.equals(Name, other.Name) && Objects.equals(Occupation, other.Occupation)
				&& Objects.equals(Status, other.Status);
	}

	@Override
	public String toString() {
		return "Person [Age=" + Age + ", Name=" + Name + ", Occupation=" + Occupation + ", Status=" + Status
				+ ", address=" + address + "]";
	}

	
}
