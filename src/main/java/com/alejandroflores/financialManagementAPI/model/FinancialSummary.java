package com.alejandroflores.financialManagementAPI.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Map;


@Document(collection = "summary")
public class FinancialSummary {

    private BigDecimal totalExpenses;
    private BigDecimal totalIncome;
    private BigDecimal balance; //Incomes - expenses
    private Map<Category, BigDecimal> expensesByCategory;

 /*   public FinancialSummary() {

    }
*/
    public FinancialSummary(){

    }

    public FinancialSummary(BigDecimal totalExpenses, BigDecimal totalIncome, BigDecimal balance, Map<Category, BigDecimal> expensesByCategory) {
        this.totalExpenses = totalExpenses;
        this.totalIncome = totalIncome;
        this.balance = balance;
        this.expensesByCategory = expensesByCategory;
    }

    public BigDecimal getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(BigDecimal totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Map<Category, BigDecimal> getExpensesByCategory() {
        return expensesByCategory;
    }

    public void setExpensesByCategory(Map<Category, BigDecimal> expensesByCategory) {
        this.expensesByCategory = expensesByCategory;
    }
}

