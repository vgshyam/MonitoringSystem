package com.monitor.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.monitor.dto.TransactionDto;

/**
 * @author shyam
 */

public interface TransactionService {


	List<TransactionDto> getAccTransactionByRule(int ruleId) throws IOException, InterruptedException, ExecutionException;
}
