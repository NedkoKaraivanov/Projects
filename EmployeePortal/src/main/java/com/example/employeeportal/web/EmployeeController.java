package com.example.employeeportal.web;

import com.example.employeeportal.model.dtos.CreateEmployeeDTO;
import com.example.employeeportal.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @ModelAttribute("createEmployeeDTO")
    public CreateEmployeeDTO initCreateEmployeeDTO() {
        return new CreateEmployeeDTO();
    }

    @GetMapping("/create")
    public String createEmployee() {

        return "add-employee";
    }

    @PostMapping("/create")
    public String createEmployee(@Valid CreateEmployeeDTO createEmployeeDTO,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors() || !employeeService.createEmployee(createEmployeeDTO)) {
            redirectAttributes.addFlashAttribute("createEmployeeDTO", createEmployeeDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.createEmployeeDTO", bindingResult);

            return "redirect:/employees/create";
        }

        return "redirect:/";
    }
}
