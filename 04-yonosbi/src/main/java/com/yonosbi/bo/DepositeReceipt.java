package com.yonosbi.bo;

import java.util.Date;

public class DepositeReceipt {
	protected int accountNo;
	protected String accountHolderName;
	protected Date depositedDate;
	protected double balance;
	protected String referenceNo;
	protected String status;

	public int getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(int accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public Date getDepositedDate() {
		return depositedDate;
	}

	public void setDepositedDate(Date depositedDate) {
		this.depositedDate = depositedDate;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
