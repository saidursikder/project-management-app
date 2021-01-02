package com.saidursikder.pmaapp.controllers;

import com.saidursikder.pmaapp.dao.EmployeeRepository;
import com.saidursikder.pmaapp.entities.Employee;
import com.saidursikder.pmaapp.entities.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    EmployeeRepository empRepo;

    @GetMapping
    public String displayEmployees(Model model) {
        List<Employee> employees = empRepo.findAll();
        model.addAttribute("employees", employees);
        return "employees/list-employees";
    }

    @GetMapping("/new")
    public String displayEmployeeForm(Model model) {
        Employee anEmployee = new Employee();
        model.addAttribute("employee", anEmployee);

        return "employees/new-employee";
    }

    @PostMapping("/save")
    public String createEmployee(Employee employee, @Valid Model model, Errors errors) {
        if(errors.hasErrors()) {
            return "employees/new-employee";
        }

        empRepo.save(employee);

        // use a redirect to prevent duplicate submissions
        return "redirect:/employees/new";
    }

    @GetMapping("/update")
    public String displayEmployeeUpdateForm(@RequestParam("id") long theId, Model model) {

        Employee theEmp = empRepo.findByEmployeeId(theId);
        model.addAttribute("employee", theEmp);

        return "employees/new-employee";
    }

    @GetMapping("/delete")
    public String deleteEmployee(@RequestParam("id") long theId, Model model) {

        Employee theEmp = empRepo.findByEmployeeId(theId);
        empRepo.delete(theEmp);

        return "redirect:/employees";
    }




}
