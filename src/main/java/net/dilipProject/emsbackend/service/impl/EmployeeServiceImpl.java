package net.dilipProject.emsbackend.service.impl;

import lombok.AllArgsConstructor;
import net.dilipProject.emsbackend.dto.EmployeeDto;
import net.dilipProject.emsbackend.entity.Employee;
import net.dilipProject.emsbackend.exception.ResourceNotFoundException;
import net.dilipProject.emsbackend.mapper.EmployeeMapper;
import net.dilipProject.emsbackend.repository.EmployeeRepository;
import net.dilipProject.emsbackend.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;
    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee= EmployeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee=employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
        Employee employee=employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("emloee not found"+employeeId));
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
       List<Employee> employees= employeeRepository.findAll();

        return employees.stream().map((employee )-> EmployeeMapper.mapToEmployeeDto(employee))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {
        Employee employee=employeeRepository.findById(employeeId).orElseThrow(
                () ->new ResourceNotFoundException("Emloess is not exist"+employeeId)
        );
        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmail(updatedEmployee.getEmail());

        Employee updatedEmployeeObj=employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(updatedEmployeeObj);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
       Employee employee= employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("emloee not found"+employeeId));
       employeeRepository.deleteById(employeeId);
    }
}
