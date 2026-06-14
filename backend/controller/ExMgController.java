package com.expanse.expanse.manager.controller;


import com.expanse.expanse.manager.dto.*;
import com.expanse.expanse.manager.service.ExMgService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/expenseManager")
public class ExMgController {

    @Autowired
    ExMgService service;

//    @PostMapping("/showExpenses/{userId}")
//    public ResponseEntity<?> showExpenses(@PathVariable @NotBlank @Email(message = "must provide email") String userId)
//    {
//        return service.showExpenses(userId);
//    }

    @PostMapping("/get")
    public ResponseEntity<?> getAllExpenses()
    {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return service.getAllExpenses(userId);
    }

    @PostMapping("/byDate")
    public ResponseEntity<?> getByDate(@RequestBody @Valid ByDateDto date)
    {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        date.setEmailId(userId);
        return service.getByDate(date);
    }

    @PostMapping("/byType")
    public ResponseEntity<?> getByType(@RequestBody @Valid ByTypeDto expenseType)
    {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        expenseType.setEmailId(userId);
        return service.getByType(expenseType);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addExpense(@RequestBody @Valid ExpenseInputDto ex)
    {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        ex.setEmailId(userId);
        return service.addExpense(ex);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateExpense(@RequestBody @Valid ExpenseInputDto ex) throws Exception
    {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        ex.setEmailId(userId);
        return service.updateExpense(ex);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteExpense(@RequestBody IdDto ex) throws Exception
    {
        return service.deleteExpense(ex);
    }

//    @GetMapping("/getByCat")
//    public ResponseEntity<?> getByCategory(@RequestBody @Valid CategoryInputDto ex)
//    {
//        return service.getByCategory(ex);
//    }

//    @GetMapping("/getTotalAmt")
//    public ResponseEntity<Double> getTotalAmount()
//    {
//        return service.getTotalAmount();
//    }


//    @PostMapping("/divide/{number}")
//    public ResponseEntity<?> divide(@RequestParam("number") Long id) throws Exception {
////        try {
////            return service.divide(id);
////        } catch (Exception e){
////            return ResponseEntity.badRequest().body("Failed");
////        }
//
//        return service.divide(id);
//    }

}
