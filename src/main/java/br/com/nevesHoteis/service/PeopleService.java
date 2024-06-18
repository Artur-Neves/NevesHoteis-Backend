package br.com.nevesHoteis.service;

import br.com.nevesHoteis.domain.Dto.PeopleDto;
import br.com.nevesHoteis.domain.Dto.PeopleUpdateDto;
import br.com.nevesHoteis.domain.People;
import br.com.nevesHoteis.domain.SimpleUser;
import br.com.nevesHoteis.domain.validation.People.ValidatePeople;
import br.com.nevesHoteis.domain.validation.User.ValidateUser;
import br.com.nevesHoteis.repository.SimpleUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface PeopleService <T extends People> {
    public Page<T> findAll(Pageable pageable);
    public T save(People people);
    @Transactional
    public T update(long id, People people);
    public T findById(Long id);
    public void delete(Long id);
    public void validate(People people);
    public void setValidatePeople(List<ValidatePeople> b);
    public void setValidateUsers(List<ValidateUser> a);

}
