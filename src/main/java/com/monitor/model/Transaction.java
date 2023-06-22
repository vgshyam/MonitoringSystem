package com.monitor.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author shyam
 *
 */
public class Transaction {

	@JsonProperty(value = "tran_id")
	String id;
	@JsonProperty(value = "tran_direction")
	String direction;
	@JsonProperty(value = "tran_time")
	long timeStemp;
	@JsonProperty(value = "acc_nmbr")
	String accountNumber;
	@JsonProperty(value = "tran_amnt")
	Double amount;
	LocalDateTime transactionDate;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the direction
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}

	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public long getTimeStemp() {
		return timeStemp;
	}

	public void setTimeStemp(long timeStemp) {
		this.timeStemp = timeStemp;
	}

	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", direction=" + direction + ", timeStemp=" + timeStemp + ", accountNumber="
				+ accountNumber + ", amount=" + amount + ", transactionDate=" + transactionDate + "]";
	}

}
