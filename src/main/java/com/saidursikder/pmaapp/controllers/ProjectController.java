package com.saidursikder.pmaapp.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saidursikder.pmaapp.dao.EmployeeRepository;
import com.saidursikder.pmaapp.dao.ProjectRepository;
import com.saidursikder.pmaapp.dto.TimeChartData;
import com.saidursikder.pmaapp.entities.Employee;
import com.saidursikder.pmaapp.entities.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    ProjectRepository proRepo;
    @Autowired
    EmployeeRepository empRepo;

    @GetMapping
    public String displayProjects(Model model) {
        List<Project> projects = proRepo.findAll();
        model.addAttribute("projects", projects);
        return "projects/list-projects";
    }

    @GetMapping("/new")
    public String displayProjectForm(Model model) {
        Project aProject = new Project();
        List<Employee> employees = empRepo.findAll();

        model.addAttribute("project", aProject);
        model.addAttribute("allEmployees", employees);

        return "projects/new-project";
    }

    @PostMapping("/save")
    public String createProject(Project project, Model model) {
        proRepo.save(project);
        // use a redirect to prevent duplicate submissions
        return "redirect:/projects/new";
    }

    @GetMapping("/update")
    public String displayProjectUpdateForm(@RequestParam("id") long theId, Model model) {
        List<Employee> employees = empRepo.findAll();
        Project theProject = proRepo.findByProjectId(theId);
        model.addAttribute("allEmployees", employees);
        model.addAttribute("project", theProject);

        return "projects/new-project";
    }

    @GetMapping("/delete")
    public String deleteProject(@RequestParam("id") long theId, Model model) {

        Project theProject = proRepo.findByProjectId(theId);
        proRepo.delete(theProject);

        return "redirect:/projects";
    }

    @GetMapping("/timelines")
    public String displayProjectTimelines(Model model) throws JsonProcessingException {

        List<TimeChartData> timelineData = proRepo.getTimeData();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonTimelineString = objectMapper.writeValueAsString(timelineData);

        System.out.println("------------------ project timelines ----------------------");
        System.out.println(jsonTimelineString);

        model.addAttribute("projectTimeList", jsonTimelineString);

        return "projects/project-timelines";
    }

}
