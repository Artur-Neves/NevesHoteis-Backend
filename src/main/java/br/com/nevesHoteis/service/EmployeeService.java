package br.com.nevesHoteis.service;

import br.com.nevesHoteis.domain.Dto.PeopleDto;
import br.com.nevesHoteis.domain.Dto.PeopleUpdateDto;
import br.com.nevesHoteis.domain.Employee;
import br.com.nevesHoteis.domain.Employee;
import br.com.nevesHoteis.domain.SimpleUser;
import br.com.nevesHoteis.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService implements PeopleService<Employee> {
    @Autowired
    private EmployeeRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Page<Employee> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Employee save(PeopleDto peopleDto){
        Employee employee= repository.save(new Employee(peopleDto));
        employee.passwordEncoder();
        return employee;
    }

    @Override
    public Employee update(long id, PeopleUpdateDto dto) {
        Employee employeeDto = new Employee(dto);
        Employee employee = findById(id);
        return (Employee) employee.merge(employeeDto);
    }

    @Override
    public Employee findById(Long id) {
        return repository.findById(id).orElseThrow(()->new EntityNotFoundException("Entity not found"));
    }

    @Override
    public void delete(Long id) {
        repository.delete(findById(id));
    }
}
