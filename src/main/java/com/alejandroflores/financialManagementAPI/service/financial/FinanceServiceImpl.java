package com.alejandroflores.financialManagementAPI.service.financial;

import com.alejandroflores.financialManagementAPI.model.*;
import com.alejandroflores.financialManagementAPI.model.*;
import com.alejandroflores.financialManagementAPI.repository.expense.ExpenseRepository;
import com.alejandroflores.financialManagementAPI.repository.financial.FinanceRepository;
import com.alejandroflores.financialManagementAPI.repository.income.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FinanceServiceImpl implements FinanceService {

    @Autowired
    private FinanceRepository financialRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private IncomeRepository incomeRepository;

    @Override
    public FinancialSummary generateSummary(User user, LocalDate from, LocalDate to) {
        List<Expense> expenses = expenseRepository.findByUserAndDateBetween(user, from, to);
        List<Income> incomes = incomeRepository.findByUserAndDateBetween(user, from, to);

        BigDecimal totalExpenses = expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalIncomes = incomes.stream()
                .map(Income::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<Category, BigDecimal> expensesByCategory = expenses.stream()
                .collect((Collectors.groupingBy(Expense::getCategory,
                        Collectors.reducing(BigDecimal.ZERO, Expense:: getAmount, BigDecimal::add))));


        BigDecimal balance = totalIncomes.subtract(totalExpenses);

        FinancialSummary summary = new FinancialSummary(totalExpenses, totalIncomes, balance, expensesByCategory);
        summary.setTotalExpenses(totalExpenses);
        summary.setTotalIncome(totalIncomes);
        summary.setBalance(balance);
        summary.setExpensesByCategory(expensesByCategory);


        return summary;
    }
}
