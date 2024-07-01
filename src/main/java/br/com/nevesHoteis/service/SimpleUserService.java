package br.com.nevesHoteis.service;

import br.com.nevesHoteis.domain.People;
import br.com.nevesHoteis.domain.SimpleUser;
import br.com.nevesHoteis.service.validation.People.ValidatePeople;
import br.com.nevesHoteis.service.validation.User.ValidatePasswordUser;
import br.com.nevesHoteis.service.validation.User.ValidateUser;
import br.com.nevesHoteis.repository.SimpleUserRepository;
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
public class SimpleUserService implements PeopleService<SimpleUser> {
    @Autowired
    private SimpleUserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Setter
    @Autowired
    private List<ValidateUser> validateUsers;
    @Setter
    @Autowired
    private List<ValidatePeople> validatePeople;


    public Page<SimpleUser> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }
    @Transactional
    public SimpleUser save(People simpleUser) {
        validate(simpleUser);
        simpleUser.getUser().passwordEncoder();
        return repository.save((SimpleUser) simpleUser);
    }
    @Transactional
    public SimpleUser update(long id, People simpleUserDto) {
        validate(simpleUserDto);
        SimpleUser simpleUser = findById(id);
        return (SimpleUser) simpleUser.merge(simpleUserDto);
    }
    public SimpleUser findById(Long id){
        return repository.findById(id).orElseThrow(()->new EntityNotFoundException("Entity not found"));
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }

    public void validate(People people){
        validatePeople.forEach(b ->b.validate(people));
        validateUsers.forEach(b-> b.validate(people.getUser()));
    }


    public SimpleUser simpleSave(SimpleUser simpleUser) {
        validateUsers.forEach(validateUser -> validateUser.validate(simpleUser.getUser()));
        simpleUser.getUser().passwordEncoder();
        return repository.save(simpleUser);
    }
}
