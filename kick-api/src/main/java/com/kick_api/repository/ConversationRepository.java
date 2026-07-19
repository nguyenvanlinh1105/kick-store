package com.kick_api.repository;

import com.kick_api.entity.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    
    Page<Conversation> findAllByCustomerId(Long customerId, Pageable pageable);

    Page<Conversation> findAllBySalesManagerId(Long salesManagerId, Pageable pageable);

    Page<Conversation> findAllBySalesManagerIdIsNull(Pageable pageable);

    @Query("SELECT c FROM Conversation c WHERE c.customer.id = :customerId AND c.status = 'OPEN'")
    Optional<Conversation> findActiveConversationByCustomerId(@Param("customerId") Long customerId);
}
