package com.yonosbi.bo;

import java.util.Date;

public class DepositeBo {
	protected int accountNo;
	protected String accountHolderName;
	protected Date depositedDate;
	protected String accountType;
	protected double amount;

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

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
