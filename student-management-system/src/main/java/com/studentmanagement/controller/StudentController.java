package com.studentmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.studentmanagement.service.StudentService;
import com.studentmanagement.entity.*;

@Controller
public class StudentController {

		StudentService studentService;

		public StudentController(StudentService studentService) {
			super();
			this.studentService = studentService;
		}
		
		@GetMapping("/students")
		public String getAllStudent(Model model) {
			model.addAttribute("students", studentService.getAllStudent());
			return "students";
		}
		
		@GetMapping("students/new")
		public String createStudentForm(Model model) {
			Student student = new Student();
			model.addAttribute("student", student);
			
			return "create_student";
			
		}
		
		@PostMapping("/students")
		public String saveStudent(@ModelAttribute("student") Student student) {
			studentService.saveStudent(student);
			return "redirect:/students";
		}
		
		@GetMapping("/students/edit/{id}")
		public String editStudent(@PathVariable Long id, Model model) {
			model.addAttribute("student", studentService.getStudentById(id));
			return "edit_student";
		}
		
		@PostMapping("/students/{id}")
		public String updateStudent(@PathVariable Long id, @ModelAttribute("student") Student student) {
			
			//getting student id from the database
			Student existingStudent = studentService.getStudentById(id);
			existingStudent.setId(id);
			existingStudent.setFirstName(student.getFirstName());
			existingStudent.setLastName(student.getLastName());
			existingStudent.setEmail(student.getEmail());
			
			//saving updated student values
			studentService.updateStudent(existingStudent);
			
			return "redirect:/students";
			
		}
		
		@GetMapping("/students/{id}")
		public String deleteStudent(@PathVariable Long id) {
			studentService.deleteStudentById(id);
			return "redirect:/students";
		}
		
		
}
