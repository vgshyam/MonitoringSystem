package com.monitor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.monitor.dto.RuleId;
import com.monitor.dto.TransactionDto;
import com.monitor.service.TransactionService;

/**
 * @author shyam
 */

@RestController
public class TransactionController {

	@Autowired
	TransactionService transactionService;

	@MessageMapping("/transaction-by-rule")
	@SendTo("/transaction/by-rule")
	public List<TransactionDto> greeting(@RequestBody RuleId id) throws Exception {

		return transactionService.getAccTransactionByRule(id.getId());
	}
}
