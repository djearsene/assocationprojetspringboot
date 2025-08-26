package asso.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import asso.model.Employee;
import asso.service.EmployeeService;

@RestController
public class HelloController {

    private final EmployeeService service;

    public HelloController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/")
    public Iterable<Employee> getAllEmployees() {
        return service.getEmployees();
    }
}
