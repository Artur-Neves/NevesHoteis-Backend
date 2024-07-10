package br.com.nevesHoteis.repository;

import br.com.nevesHoteis.domain.Admin;
import br.com.nevesHoteis.domain.People;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByUserLogin(String login);
}
