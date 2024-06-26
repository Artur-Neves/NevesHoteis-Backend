package br.com.nevesHoteis.service;

import br.com.nevesHoteis.controller.Dto.RedefinePasswordDto;
import br.com.nevesHoteis.controller.Dto.TokenEmailDto;
import br.com.nevesHoteis.domain.Role;
import br.com.nevesHoteis.domain.User;
import br.com.nevesHoteis.infra.exeption.ValidateUserException;
import br.com.nevesHoteis.repository.UserRepository;
import br.com.nevesHoteis.service.validation.User.ValidatePasswordUser;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository repository;
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
        validatePasswordUser.validate( new User(null, dto.newPassword(), Role.USER));
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
}
