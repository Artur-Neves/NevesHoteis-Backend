package br.com.nevesHoteis.repository;

import br.com.nevesHoteis.domain.Hotel;
import br.com.nevesHoteis.repository.projections.HotelDatesCardProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
//    @Query("SELECT new br.com.nevesHoteis.repository.projections.HotelDatesCardProjection( " +
//            "u.name, u.inPromotion, u.dailyValue, u.photos, u.dailyValue, " +
//            "new br.com.nevesHoteis.repository.projections.AddressHotelCardProjection(u.address.city, u.address.state), " +
//            "new br.com.nevesHoteis.repository.projections.PromotionCardProjection(u.promotion.discount, u.promotion.percentageDiscount)) " +
//            "from Hotel u")
    @Query("SELECT new br.com.nevesHoteis.repository.projections.HotelDatesCardProjection( " +
            "u) " +
            "FROM Hotel u " +
            "WHERE ((UPPER(u.name) LIKE CONCAT('%', :name, '%'))) and (:inPromotion is null or (u.inPromotion=true) ) "
    )
    Page<HotelDatesCardProjection> findAllHotelForCard(Pageable pageable, @Param("name") String name,@Param("inPromotion") String inPromotion);
}
