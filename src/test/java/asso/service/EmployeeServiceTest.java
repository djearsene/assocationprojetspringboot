package asso.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import asso.model.Employee;
import asso.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee testEmployee;

    @BeforeEach
    void setUp() {
        testEmployee = new Employee();
        testEmployee.setId(1L);
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setMail("john.doe@example.com");
        testEmployee.setPassword("password123");
    }

    @Test
    void getEmployee_shouldReturnEmployee_whenEmployeeExists() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(testEmployee));

        Optional<Employee> result = employeeService.getEmployee(1L);

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getFirstName());
        assertEquals("Doe", result.get().getLastName());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void getEmployee_shouldReturnEmpty_whenEmployeeDoesNotExist() {
        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<Employee> result = employeeService.getEmployee(999L);

        assertFalse(result.isPresent());
        verify(employeeRepository, times(1)).findById(999L);
    }

    @Test
    void getEmployees_shouldReturnAllEmployees() {
        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setFirstName("Jane");
        employee2.setLastName("Smith");

        when(employeeRepository.findAll()).thenReturn(Arrays.asList(testEmployee, employee2));

        Iterable<Employee> result = employeeService.getEmployees();

        assertNotNull(result);
        assertEquals(2, ((java.util.List<Employee>) result).size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void saveEmployee_shouldSaveAndReturnEmployee() {
        when(employeeRepository.save(testEmployee)).thenReturn(testEmployee);

        Employee result = employeeService.saveEmployee(testEmployee);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        verify(employeeRepository, times(1)).save(testEmployee);
    }

    @Test
    void deleteEmployee_shouldCallRepositoryDelete() {
        Long employeeId = 1L;
        doNothing().when(employeeRepository).deleteById(employeeId);

        employeeService.deleteEmployee(employeeId);

        verify(employeeRepository, times(1)).deleteById(employeeId);
    }
}
