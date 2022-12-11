package com.ri.dto;
public class ReferenceNo {
	private String source;
	private String destination;
	private int day;
	private int month;
	private long uniqueNo;

	@Override
	public String toString() {
		return "ReferenceNo [source=" + source + ", destination=" + destination + ", day=" + day + ", month=" + month
				+ ", uniqueNo=" + uniqueNo + "]";
	}

	public static ReferenceNo fromString(String in) {
		ReferenceNo referenceNo = null;

		referenceNo = new ReferenceNo();
		referenceNo.source = in.substring(0, 3);
		referenceNo.day = Integer.parseInt(in.substring(3, 5));
		referenceNo.month = Integer.parseInt(in.substring(5, 7));
		referenceNo.destination = in.substring(7, 10);
		referenceNo.uniqueNo = Long.parseLong(in.substring(10, 16));

		return referenceNo;
	}
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public long getUniqueNo() {
		return uniqueNo;
	}
	public void setUniqueNo(long uniqueNo) {
		this.uniqueNo = uniqueNo;
	}
}
