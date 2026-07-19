package com.kick_api.repository;

import com.kick_api.entity.MessageAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageAttachmentRepository extends JpaRepository<MessageAttachment, Long> {
    
    List<MessageAttachment> findAllByMessageId(Long messageId);
}
