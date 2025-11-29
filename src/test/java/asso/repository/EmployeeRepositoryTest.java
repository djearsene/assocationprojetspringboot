package asso.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import asso.model.Employee;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void findById_shouldReturnEmployee_whenEmployeeExists() {
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setMail("john.doe@example.com");
        employee.setPassword("password123");

        Employee savedEmployee = entityManager.persistAndFlush(employee);

        Optional<Employee> found = employeeRepository.findById(savedEmployee.getId());

        assertTrue(found.isPresent());
        assertEquals("John", found.get().getFirstName());
        assertEquals("Doe", found.get().getLastName());
        assertEquals("john.doe@example.com", found.get().getMail());
    }

    @Test
    void findById_shouldReturnEmpty_whenEmployeeDoesNotExist() {
        Optional<Employee> found = employeeRepository.findById(999L);

        assertFalse(found.isPresent());
    }

    @Test
    void save_shouldPersistEmployee() {
        Employee employee = new Employee();
        employee.setFirstName("Jane");
        employee.setLastName("Smith");
        employee.setMail("jane.smith@example.com");
        employee.setPassword("password456");

        Employee saved = employeeRepository.save(employee);

        assertNotNull(saved.getId());
        assertEquals("Jane", saved.getFirstName());

        Employee found = entityManager.find(Employee.class, saved.getId());
        assertNotNull(found);
        assertEquals("Jane", found.getFirstName());
    }
}
