package br.com.nevesHoteis.service;

import br.com.nevesHoteis.domain.Dto.PeopleDto;
import br.com.nevesHoteis.domain.Dto.PeopleUpdateDto;
import br.com.nevesHoteis.domain.People;
import br.com.nevesHoteis.domain.SimpleUser;
import br.com.nevesHoteis.domain.User;
import br.com.nevesHoteis.repository.SimpleUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SimpleUserService implements PeopleService<SimpleUser> {
    @Autowired
    private SimpleUserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Page<SimpleUser> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }
    @Transactional
    public SimpleUser save(PeopleDto peopleDto) {
        SimpleUser simpleUser= repository.save(new SimpleUser(peopleDto));
        simpleUser.passwordEncoder();
        return simpleUser;
    }
    @Transactional
    public SimpleUser update(long id, PeopleUpdateDto peopleUpdateDto) {
        SimpleUser simpleUserDto = new SimpleUser(peopleUpdateDto);
        SimpleUser simpleUser = findById(id);
        return (SimpleUser) simpleUser.merge(simpleUserDto);
    }
    public SimpleUser findById(Long id){
        return repository.findById(id).orElseThrow(()->new EntityNotFoundException("Entity not found"));
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }
//    public SimpleUser constructorDto(PeopleDto peopleDto){
//        return new SimpleUser(peopleDto);
//    }
//    public SimpleUser constructorDto(PeopleUpdateDto peopleUpdateDto){
//        return new SimpleUser(peopleUpdateDto);
//    }
}
