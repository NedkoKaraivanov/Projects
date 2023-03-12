package com.example.employeeportal.web;

import com.example.employeeportal.model.dtos.CreateTaskDTO;
import com.example.employeeportal.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
}
