package br.com.nevesHoteis.service;

import br.com.nevesHoteis.domain.Admin;
import br.com.nevesHoteis.domain.Dto.PeopleDto;
import br.com.nevesHoteis.domain.Dto.PeopleUpdateDto;
import br.com.nevesHoteis.domain.Admin;
import br.com.nevesHoteis.domain.SimpleUser;
import br.com.nevesHoteis.repository.AdminRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService implements PeopleService<Admin> {
    @Autowired
    private AdminRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Page<Admin> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Admin save(PeopleDto peopleDto) {
        Admin admin= repository.save(new Admin(peopleDto));
        admin.passwordEncoder();
        return admin;
    }

    @Override
    public Admin update(long id, PeopleUpdateDto dto) {
        Admin adminDto = new Admin(dto);
        Admin admin = findById(id);
        return (Admin) admin.merge(adminDto);
    }

    @Override
    public Admin findById(Long id) {
        return repository.findById(id).orElseThrow(()->new EntityNotFoundException("Entity not found"));
    }

    @Override
    public void delete(Long id) {
        repository.delete(findById(id));
    }
}
