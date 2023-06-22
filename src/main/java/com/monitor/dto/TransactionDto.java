package com.monitor.dto;

import java.util.List;

import com.monitor.model.Transaction;

public class TransactionDto {

	String accountNbr;
	int ruleId;
	List<Transaction> triggeringTransaction;

	public TransactionDto() {
	}

	
	public TransactionDto(String accountNbr, int ruleId, List<Transaction> triggeringTransaction) {
		super();
		this.accountNbr = accountNbr;
		this.ruleId = ruleId;
		this.triggeringTransaction = triggeringTransaction;
	}


	public String getAccountNbr() {
		return accountNbr;
	}

	public void setAccountNbr(String accountNbr) {
		this.accountNbr = accountNbr;
	}

	public int getRuleId() {
		return ruleId;
	}

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}

	public List<Transaction> getTriggeringTransaction() {
		return triggeringTransaction;
	}

	public void setTriggeringTransaction(List<Transaction> triggeringTransaction) {
		this.triggeringTransaction = triggeringTransaction;
	}
}
