package br.com.nevesHoteis.service;

import br.com.nevesHoteis.domain.People;
import br.com.nevesHoteis.service.validation.People.ValidatePeople;
import br.com.nevesHoteis.service.validation.User.ValidateUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface PeopleService <T extends People> {
    public Page<T> findAll(Pageable pageable);
    public T save(People people);
    @Transactional
    public T update(String idOrLogin, People people);
    public T findById(Long id);
    public void delete(Long id);
    public void validate(People people);
    public void setValidatePeople(List<ValidatePeople> b);
    public void setValidateUsers(List<ValidateUser> a);
    public T findByUserLogin(String login);
    default T findByIdOrLogin(String idOrLogin){
        try {
            return findById(Long.parseLong(idOrLogin));
        }
        catch (NumberFormatException e){
            return findByUserLogin(idOrLogin);
        }
    }

}
