package com.kick_api.service;

import com.kick_api.entity.Conversation;
import com.kick_api.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ChatService {
    Conversation startConversation(Long customerId);
    Message sendMessage(Long conversationId, Long senderId, String content, List<String> attachmentUrls);
    void assignSalesManager(Long conversationId, Long salesManagerId);
    Page<Message> getMessageHistory(Long conversationId, Pageable pageable);
}
