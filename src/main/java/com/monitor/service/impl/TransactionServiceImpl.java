package com.monitor.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monitor.dto.TransactionDto;
import com.monitor.model.Transaction;
import com.monitor.service.TransactionService;

/**
 * @author shyam 16-Jun-2023 9:37:30 am
 */

@Service
public class TransactionServiceImpl implements TransactionService {

	Callable<List<Transaction>> readTransations = () -> {

		List<Transaction> transactions = new ArrayList<>();

		File file = new File("src/main/resources/file/transactions.json");

		ObjectMapper mapper = new ObjectMapper();
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(file));
			String data;
			Transaction transaction;
			while ((data = reader.readLine()) != null) {

				transaction = mapper.readValue(data, Transaction.class);
				transaction.setTransactionDate(Instant.ofEpochMilli(transaction.getTimeStemp() * 1000)
						.atZone(ZoneId.systemDefault()).toLocalDateTime());
				transactions.add(transaction);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			reader.close();
		}
		return transactions;
	};

	public List<TransactionDto> applyRule(List<Transaction> transactions, int ruleId) {

		List<TransactionDto> transactionByRules = new ArrayList<>();

		LocalTime time = LocalTime.MIDNIGHT;
		LocalDate date = LocalDate.now();
		LocalDateTime today = LocalDateTime.of(date, time);

		Map<String, List<Transaction>> transactionByAccount = Collections.emptyMap();
		if (ruleId == 1) {

			LocalDateTime yesterday = today.minusDays(1);

			transactionByAccount = transactions.stream()
					.filter(transaction -> transaction.getTransactionDate().isBefore(today)
							&& transaction.getTransactionDate().isAfter(yesterday))
					.collect(Collectors.groupingBy(Transaction::getAccountNumber));

			TransactionDto dto;
			for (Entry<String, List<Transaction>> trans : transactionByAccount.entrySet()) {

				dto = new TransactionDto(trans.getKey(), ruleId, trans.getValue());
				transactionByRules.add(dto);
			}
		} else if (ruleId == 2) {

			LocalDateTime month = today.minusMonths(1);

			transactionByAccount = transactions.stream()
					.filter(transaction -> transaction.getDirection().equals("IN")
							&& transaction.getTransactionDate().isBefore(today)
							&& transaction.getTransactionDate().isAfter(month))
					.collect(Collectors.groupingBy(Transaction::getAccountNumber));

		}

		TransactionDto dto;
		for (Entry<String, List<Transaction>> trans : transactionByAccount.entrySet()) {

			dto = new TransactionDto(trans.getKey(), ruleId, trans.getValue());
			transactionByRules.add(dto);
		}

		return transactionByRules;
	}

	@Override
	public List<TransactionDto> getAccTransactionByRule(int ruleId)
			throws IOException, InterruptedException, ExecutionException {

		ExecutorService service = Executors.newFixedThreadPool(5);

		Future<List<Transaction>> transactions = service.submit(readTransations);

		Callable<List<TransactionDto>> applyRule = () -> {
			return this.applyRule(transactions.get(), ruleId);
		};

		Future<List<TransactionDto>> filteredTrans = service.submit(applyRule);

		return filteredTrans.get();

	}

}
