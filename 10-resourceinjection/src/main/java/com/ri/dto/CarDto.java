package com.ri.dto;
import javax.ws.rs.FormParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

public class CarDto {
	@PathParam("city")
	String city;
	@MatrixParam("location")
	String location;
	@FormParam("model")
	String model;
	@FormParam("manufacturer")
	String manufacturer;
	@FormParam("year")
	int year;
	@FormParam("state")
	String state;
	@FormParam("price")
	double price;
	@QueryParam("fuelType")
	String fuelType;
	@QueryParam("kmDrove")
	String kmDrove;
	@QueryParam("ownershipType")
	String ownershipType;
	@QueryParam("validInsurance")
	boolean hasInsuranceValid;

	@Override
	public String toString() {
		return "CarDto [city=" + city + ", location=" + location + ", model=" + model + ", manufacturer=" + manufacturer
				+ ", year=" + year + ", state=" + state + ", price=" + price + ", fuelType=" + fuelType + ", kmDrove="
				+ kmDrove + ", ownershipType=" + ownershipType + ", hasInsuranceValid=" + hasInsuranceValid + "]";
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public String getKmDrove() {
		return kmDrove;
	}

	public void setKmDrove(String kmDrove) {
		this.kmDrove = kmDrove;
	}

	public String getOwnershipType() {
		return ownershipType;
	}

	public void setOwnershipType(String ownershipType) {
		this.ownershipType = ownershipType;
	}

	public boolean getHasInsuranceValid() {
		return hasInsuranceValid;
	}

	public void setHasInsuranceValid(boolean hasInsuranceValid) {
		this.hasInsuranceValid = hasInsuranceValid;
	}
}
