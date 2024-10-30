package com.alejandroflores.financialManagementAPI.controller.income;


import com.alejandroflores.financialManagementAPI.model.Income;
import com.alejandroflores.financialManagementAPI.service.income.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/incomes")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @GetMapping
    public List<Income> findAll() {
        return incomeService.findAll();
    }

    @GetMapping("/{idIncome}")
    public Income findById(@PathVariable String idIncome)
    {
        return incomeService.findById(idIncome);
    }

    @PostMapping()
    public Income save(@RequestBody Income income){
        return incomeService.save(income);
    }

    @PutMapping("/{idIncome}")
    public Income update(@PathVariable String idIncome,@RequestBody Income income){
        return incomeService.update(idIncome, income);
    }

    @DeleteMapping("/{idIncome}")
    public void deleteById(@PathVariable String idIncome){
        incomeService.deleteById(idIncome);
    }

    @GetMapping("/my-incomes")
    public List<Income> findIncomesForAuthenticatedUser() {
        return incomeService.findIncomesForAuthenticatedUser();
    }
}
