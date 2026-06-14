package com.expanse.expanse.manager.service;

import com.expanse.expanse.manager.dto.ByDateDto;
import com.expanse.expanse.manager.dto.ByTypeDto;
import com.expanse.expanse.manager.dto.ExpenseInputDto;
import com.expanse.expanse.manager.dto.IdDto;
import com.expanse.expanse.manager.entity.ExMg;
import com.expanse.expanse.manager.entity.User;
import com.expanse.expanse.manager.exception.DBException;
import com.expanse.expanse.manager.exception.ExpenseNotFoundException;
import com.expanse.expanse.manager.exception.UserNotFoundException;
import com.expanse.expanse.manager.exception.UserNotLoggedInException;
import com.expanse.expanse.manager.repository.ExMgRepo;
import com.expanse.expanse.manager.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;

@Service
@Slf4j
public class ExMgService {

    @Autowired
    ExMgRepo repo;
    @Autowired
    UserRepo userRepo;

//    public ResponseEntity<?> showExpenses(String userId)
//    {
//        User user=userRepo.findById(userId).orElse(null);
//        if(user==null)
//            throw new UserNotFoundException("User Not Found.");
//        if(!user.getStatus().equals("Logged in."))
//            throw new UserNotLoggedInException("User not logged in.");
//        ShowExpenseDto userExpenses=new ShowExpenseDto();
//        userExpenses.setRole(user.getRole());
//        userExpenses.setName(user.getName());
//        userExpenses.setEmailId(user.getEmailId());
//        userExpenses.setMobileNumber(user.getMobileNumber());
//        userExpenses.setStatus(user.getStatus());
//        List<ExMg> listOfExpenses=repo.findAllByEmailId(userId);
//        userExpenses.setExpenses(listOfExpenses);
//        return ResponseEntity.ok(userExpenses);
//    }

    public ResponseEntity<?> getAllExpenses(String userId)
    {
//        User user=userRepo.findById(userId).orElse(null);
//        if(user==null)
//            throw new UserNotFoundException("User Not Found.");
        return ResponseEntity.ok(repo.findAllByEmailId(userId));
    }

    public ResponseEntity<?> getByDate(ByDateDto date)
    {
        return ResponseEntity.ok(repo.findByDateAndEmailId(date.getDate(), date.getEmailId()));
    }

    public ResponseEntity<?> getByType(ByTypeDto expenseType)
    {
        return ResponseEntity.ok(repo.findByTypeAndEmailId(expenseType.getType(), expenseType.getEmailId()));
    }

    public ResponseEntity<?> addExpense(ExpenseInputDto ex)
    {
        User user=userRepo.findById(ex.getEmailId()).orElse(null);
        if(user==null)
            throw new UserNotFoundException("User Not Found.");
        ExMg exMg = new ExMg();
        exMg.setDate(LocalDate.now());
        exMg.setEmailId(ex.getEmailId());
        exMg.setCategory(ex.getCategory());
        exMg.setAmount(ex.getAmount());
        exMg.setTitle(ex.getTitle());
        exMg.setDescription(ex.getDescription());
        exMg.setType(ex.getType());
        try{
            repo.save(exMg);
            return ResponseEntity.ok("Expense added.");
        }
        catch(DBException e){
            throw new DBException("Database Exception");
        }
    }

    public ResponseEntity<?> updateExpense(ExpenseInputDto ex)
    {
        ExMg expense=new ExMg();
        expense.setEmailId(ex.getEmailId());
        expense.setId(ex.getId());
        expense.setDescription(ex.getDescription());
        expense.setTitle(ex.getTitle());
        expense.setCategory(ex.getCategory());
        expense.setAmount(ex.getAmount());
        expense.setType(ex.getType());
        ExMg targetExpense=repo.findById(expense.getId()).orElse(null);
        if(targetExpense==null)
            throw new ExpenseNotFoundException("Expense not found to update");
        expense.setDate(LocalDate.now());
        try{
            repo.save(expense);
            return ResponseEntity.ok("Expense updated.");
        }
        catch(Exception e){
            log.error(e.getMessage());
            throw new DBException("Database Exception");
        }
    }

    public ResponseEntity<?> deleteExpense(IdDto ex)
    {
        ExMg expense=new ExMg();
        expense.setId(ex.getId());
        ExMg targetExpense=repo.findById(expense.getId()).orElse(null);
        if(targetExpense==null)
            throw  new ExpenseNotFoundException("Expense not found to delete");
        repo.delete(expense);
        return ResponseEntity.ok("Expense deleted");
    }

//    public ResponseEntity<?> getByCategory(CategoryInputDto ex)
//    {
//        return ResponseEntity.ok(repo.findByCategory(ex.getCategory()));
//    }

//    public ResponseEntity<Double> getTotalAmount()
//    {
//        return  ResponseEntity.ok(repo.getTotalAmount());
//    }

//    public ResponseEntity<?> divide(Long id)  {
//        try{
//            long result = 100 / id;
//            return  ResponseEntity.ok("success");
//        }catch (ArithmeticException e){
//           throw new Abhi("testing excpeiton Abhi");
//        }
//    }

}
