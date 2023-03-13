package com.example.employeeportal.web;

import com.example.employeeportal.model.dtos.CreateTaskDTO;
import com.example.employeeportal.model.dtos.viewDtos.ViewEmployeeDTO;
import com.example.employeeportal.model.dtos.viewDtos.ViewTaskDTO;
import com.example.employeeportal.model.entity.EmployeeEntity;
import com.example.employeeportal.model.entity.TaskEntity;
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
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @ModelAttribute("createTaskDTO")
    public CreateTaskDTO initCreateTaskDTO() {
        return new CreateTaskDTO();
    }

    @GetMapping("/create")
    public String createTask() {
        return "add-task";
    }

    @PostMapping("/create")
    public String createTask(@Valid CreateTaskDTO createTaskDTO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors() || !taskService.createTask(createTaskDTO)) {
            redirectAttributes.addFlashAttribute("createTaskDTO", createTaskDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.createTaskDTO", bindingResult);

            return "redirect:/tasks/create";
        }

        return "redirect:/";
    }

    @GetMapping("/view")
    public String viewTasks(Model model) {

        List<ViewTaskDTO> allTasks = taskService.findAllTasks();
        model.addAttribute("allTasks", allTasks);

        return "view-all-tasks";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Long id) {

        taskService.deleteTask(id);

        return "redirect:/tasks/view";
    }

    @PostMapping("/save")
    public String saveEmployee(TaskEntity task) {
        taskService.saveTask(task);

        return "redirect:/tasks/view";
    }
    @GetMapping("/update/{id}")
    public ModelAndView updateEmployee(@PathVariable("id") Long id) {

        ModelAndView updateView = new ModelAndView("update-tasks");
        TaskEntity task = taskService.findById(id);
        updateView.addObject("task", task);
        return updateView;
    }
}
