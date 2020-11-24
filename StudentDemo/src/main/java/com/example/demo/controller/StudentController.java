package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Student;
import com.example.demo.repo.StudentRepo;

@Controller
@RequestMapping("/students/")
public class StudentController {
	
	
	private final StudentRepo repo;

    @Autowired
    public StudentController(StudentRepo repo) {
        this.repo = repo;
        
    }

    @GetMapping("signup")
    public String showSignUpForm(Student student) {
        return "addstudent";
    }
    
    @GetMapping("list")
    public String showUpdateForm(Model model) {
        model.addAttribute("students", repo.findAll());
        return "index";
    }

    
    @PostMapping("add")
    public String addStudent(@Valid Student student, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-student";
        }

        repo.save(student);
        return "redirect:list";
    }

    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Student student = repo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        model.addAttribute("student", student);
        return "update-student";
    }

    @PostMapping("update/{id}")
    public String updateStudent(@PathVariable("id") long id, @Valid Student student, BindingResult result,
        Model model) {
        if (result.hasErrors()) {
            student.setId(id);
            return "update-student";
        }

        repo.save(student);
        model.addAttribute("students", repo.findAll());
        return "index";
    }

    @GetMapping("delete/{id}")
    public String deleteStudent(@PathVariable("id") long id, Model model) {
        Student student = repo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        repo.delete(student);
        model.addAttribute("students", repo.findAll());
        return "index";
    }
    
}
