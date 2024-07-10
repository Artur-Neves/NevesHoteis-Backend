package br.com.nevesHoteis.repository;

import br.com.nevesHoteis.domain.People;
import br.com.nevesHoteis.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String username);
}
