package br.com.nevesHoteis.service;

import br.com.nevesHoteis.domain.Admin;
import br.com.nevesHoteis.domain.People;
import br.com.nevesHoteis.domain.SimpleUser;
import br.com.nevesHoteis.service.validation.People.ValidatePeople;
import br.com.nevesHoteis.service.validation.User.ValidateUser;
import br.com.nevesHoteis.repository.AdminRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminService implements PeopleService<Admin> {
    @Autowired
    private AdminRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    @Setter
    private List<ValidateUser> validateUsers;
    @Autowired
    @Setter
    private List<ValidatePeople> validatePeople;
    @Override
    public Page<Admin> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Admin save(People adminDto) {
        validate(adminDto);
        adminDto.getUser().passwordEncoder();
        return repository.save((Admin) adminDto);
    }

    @Override
    @Transactional
    public Admin update(String id, People adminDto) {
        validatePeople.forEach(b -> b.validate(adminDto));
        Admin admin = findByIdOrLogin(id);
        return (Admin) admin.merge(adminDto);
    }

    @Override
    public Admin findById(Long id) {
        return repository.findById(id).orElseThrow(()->new EntityNotFoundException("Entidade Admin não encontrada com este identificador"));
    }
    @Override
    public Admin findByUserLogin(String login) {
        return repository.findByUserLogin(login).orElseThrow(()->new EntityNotFoundException("Entidade Admin não encontrada com este identificador"));
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
