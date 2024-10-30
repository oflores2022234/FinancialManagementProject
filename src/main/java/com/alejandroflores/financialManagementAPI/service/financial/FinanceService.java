package com.alejandroflores.financialManagementAPI.service.financial;

import com.alejandroflores.financialManagementAPI.model.FinancialSummary;
import com.alejandroflores.financialManagementAPI.model.User;

import java.time.LocalDate;

public interface FinanceService {
    /*
        List<FinancialSummary> findAll();
        FinancialSummary findById(String id);*/
    FinancialSummary generateSummary(User user, LocalDate from, LocalDate to);

}