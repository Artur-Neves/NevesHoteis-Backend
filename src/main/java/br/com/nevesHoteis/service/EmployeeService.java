package br.com.nevesHoteis.service;

import br.com.nevesHoteis.domain.Dto.PeopleDto;
import br.com.nevesHoteis.domain.Dto.PeopleUpdateDto;
import br.com.nevesHoteis.domain.Employee;
import br.com.nevesHoteis.domain.Employee;
import br.com.nevesHoteis.domain.People;
import br.com.nevesHoteis.domain.SimpleUser;
import br.com.nevesHoteis.domain.validation.People.ValidatePeople;
import br.com.nevesHoteis.domain.validation.User.ValidateUser;
import br.com.nevesHoteis.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService implements PeopleService<Employee> {
    @Autowired
    private EmployeeRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    @Setter
    private List<ValidateUser> validateUsers;
    @Autowired
    @Setter
    private List<ValidatePeople> validatePeople;
    @Override
    public Page<Employee> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Employee save(People employeeDto){
        validate(employeeDto);
        employeeDto.passwordEncoder();
        return repository.save((Employee) employeeDto);
    }

    @Override
    @Transactional
    public Employee update(long id, People employeeDto) {
        validate(employeeDto);
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
    public void validate(People people){
        validatePeople.forEach(b ->b.validate(people));
        validateUsers.forEach(b-> b.validate(people.getUser()));
    }
}
