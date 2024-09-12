package br.com.nevesHoteis.repository;

import br.com.nevesHoteis.domain.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    public List<Promotion> findByDeletedFalseAndEndDateBefore(LocalDateTime localDateTime);
}
