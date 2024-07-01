package br.com.nevesHoteis.repository;

import br.com.nevesHoteis.domain.User;
import br.com.nevesHoteis.domain.VerificationEmailToken;
import br.com.nevesHoteis.service.VerificationEmailTokenService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface VerificationEmailTokenRepository extends JpaRepository<VerificationEmailToken, Long> {

    VerificationEmailToken findByUserId(Long id);


    Optional<VerificationEmailToken> findByUserLogin(String login);
}
