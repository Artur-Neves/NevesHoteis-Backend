package br.com.nevesHoteis.service;

import br.com.nevesHoteis.controller.Dto.RedefinePasswordDto;
import br.com.nevesHoteis.controller.Dto.TokenEmailDto;
import br.com.nevesHoteis.domain.Employee;
import br.com.nevesHoteis.domain.People;
import br.com.nevesHoteis.domain.Role;
import br.com.nevesHoteis.domain.User;
import br.com.nevesHoteis.infra.exeption.ValidateUserException;
import br.com.nevesHoteis.repository.AdminRepository;
import br.com.nevesHoteis.repository.EmployeeRepository;
import br.com.nevesHoteis.repository.SimpleUserRepository;
import br.com.nevesHoteis.repository.UserRepository;
import br.com.nevesHoteis.service.validation.User.ValidatePasswordUser;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static br.com.nevesHoteis.domain.Role.*;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private SimpleUserService simpleUserRepository;
    @Autowired
    private EmployeeService employeeRepository;
    @Autowired
    private AdminService adminRepository;
    @Autowired
    private VerificationEmailTokenService verificationEmailTokenService;

    @Override
    public UserDetails loadUserByUsername(String username){
        return findByLogin(username);
    }
    public User findByLogin(String login){
        return repository.findByLogin(login)
                .orElseThrow(()->new EntityNotFoundException("Não existe um usuário cadastrado com este email!"));
    }
    private void validateRedefinePassword(RedefinePasswordDto dto) {
        ValidatePasswordUser validatePasswordUser = new ValidatePasswordUser();
        validatePasswordUser.validate( new User(null, dto.newPassword(), USER));
        if(!dto.newPassword().equals(dto.repeatPassword())){
            throw new ValidateUserException("Alteração de senha", "As senhas não conferem");
        }
        verificationEmailTokenService.verify(new TokenEmailDto(dto.email(), dto.token()));

    }
    @Transactional
    public void  redefinePassword(RedefinePasswordDto dto){
        validateRedefinePassword(dto);
        User user = findByLogin(dto.email());
        user.redefinePassword(dto.newPassword());
    }
    public People findPeopleByLogin(){
        Map<String, String > infos = getInfoByAuthentication();
        String login = infos.get("login");
        Role role = Role.valueOf(infos.get("role"));
        return switch (role) {
            case USER -> simpleUserRepository.findByUserLogin(login);
            case EMPLOYEE -> employeeRepository.findByUserLogin(login);
            case ADMIN -> adminRepository.findByUserLogin(login);
        };

    }

    public People updateMyAccount(People people) {
        Map<String, String > infos = getInfoByAuthentication();
        String login = infos.get("login");
        Role role = Role.valueOf(infos.get("role"));
        return switch (role) {
            case USER -> simpleUserRepository.update(login, people);
            case EMPLOYEE -> employeeRepository.update(login, people);
            case ADMIN -> adminRepository.update(login, people);
        };
    }
    protected Map<String, String> getInfoByAuthentication(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login= (String) authentication.getPrincipal();
        String role =  authentication.getAuthorities().stream().findFirst().orElseThrow(()->new ValidateUserException("Perfil", "Usuário não pode não ter um perfil")).toString();
        Map<String, String> map = new HashMap<>();
        map.put("role", role);
        map.put("login", login);
        return map;
    }
}
