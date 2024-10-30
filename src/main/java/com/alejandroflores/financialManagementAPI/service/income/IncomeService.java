    package com.alejandroflores.financialManagementAPI.service.income;

    import com.alejandroflores.financialManagementAPI.model.Income;
    import com.alejandroflores.financialManagementAPI.model.User;

    import java.util.List;

    public interface IncomeService {

        List<Income> findAll();
        Income findById(String id);
        Income save(Income income);
        Income update(String id, Income income);
        void deleteById(String id);
        List<Income> findByUser(User user);
        List<Income> findIncomesForAuthenticatedUser();
    }
