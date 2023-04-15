package com.example.employeeportal.web;

import com.example.employeeportal.model.dtos.CreateEmployeeDTO;
import com.example.employeeportal.model.dtos.viewDtos.ViewEmployeeDTO;
import com.example.employeeportal.model.entity.DepartmentEntity;
import com.example.employeeportal.model.entity.EmployeeEntity;
import com.example.employeeportal.model.entity.TaskEntity;
import com.example.employeeportal.service.DepartmentService;
import com.example.employeeportal.service.EmployeeService;
import com.example.employeeportal.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    private final TaskService taskService;

    private final DepartmentService departmentService;

    public EmployeeController(EmployeeService employeeService, TaskService taskService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.taskService = taskService;
        this.departmentService = departmentService;
    }

    @ModelAttribute("createEmployeeDTO")
    public CreateEmployeeDTO initCreateEmployeeDTO() {
        return new CreateEmployeeDTO();
    }

    @ModelAttribute("allManagers")
    public List<EmployeeEntity> findAllManagers() {
        return employeeService.findAllManagers();
    }

    @ModelAttribute("allTasks")
    public List<TaskEntity> findAllTasks() {
        return taskService.findAllExistingTasks();
    }

    @ModelAttribute("departments")
    public List<DepartmentEntity> findAllDepartments() {
        return departmentService.findAllDepartments();
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

    @GetMapping("/view")
    public String viewEmployees(Model model) {

        List<ViewEmployeeDTO> allEmployees = employeeService.findAllEmployees();
        model.addAttribute("allEmployees", allEmployees);

        return "view-all-employees";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Long id) {

        employeeService.deleteEmployee(id);

        return "redirect:/employees/view";
    }


    @PostMapping("/saveEmployee")
    public String saveEmployee(@Valid EmployeeEntity employee,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors() || !employeeService.saveEmployee(employee)) {
            redirectAttributes.addFlashAttribute("employee", employee);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.employee", bindingResult);

            Long id = employee.getId();

            return "redirect:/employees/update/" + id;
        }

        return "redirect:/employees/view";
    }

    @GetMapping("/update/{id}")
    public String updateEmployee(@PathVariable("id") Long id,
                                 Model model) {

        EmployeeEntity employee = employeeService.findById(id);

        if (!model.containsAttribute("employee")) {
            model.addAttribute("employee", employee);
        }

        return "update-employee";
    }


}
