package com.kick_api.service.impl;

import com.kick_api.entity.Conversation;
import com.kick_api.entity.Message;
import com.kick_api.entity.MessageAttachment;
import com.kick_api.entity.User;
import com.kick_api.constant.ConversationStatus;
import com.kick_api.constant.MessageType;
import com.kick_api.constant.ErrorCode;
import com.kick_api.exception.AppException;
import com.kick_api.repository.ConversationRepository;
import com.kick_api.repository.MessageAttachmentRepository;
import com.kick_api.repository.MessageRepository;
import com.kick_api.repository.UserRepository;
import com.kick_api.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final MessageAttachmentRepository messageAttachmentRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Conversation startConversation(Long customerId) {
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return conversationRepository.findActiveConversationByCustomerId(customerId)
                .orElseGet(() -> {
                    Conversation newConversation = Conversation.builder()
                            .customer(customer)
                            .status(ConversationStatus.OPEN)
                            .build();
                    return conversationRepository.save(newConversation);
                });
    }

    @Override
    @Transactional
    public Message sendMessage(Long conversationId, Long senderId, String content, List<String> attachmentUrls) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_FOUND));

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        MessageType type = MessageType.TEXT;
        if (attachmentUrls != null && !attachmentUrls.isEmpty()) {
            String firstUrl = attachmentUrls.get(0).toLowerCase();
            if (firstUrl.endsWith(".jpg") || firstUrl.endsWith(".png") || firstUrl.endsWith(".jpeg") || firstUrl.endsWith(".webp") || firstUrl.endsWith(".gif")) {
                type = MessageType.IMAGE;
            } else {
                type = MessageType.FILE;
            }
        }

        Message message = Message.builder()
                .conversation(conversation)
                .sender(sender)
                .content(content)
                .type(type)
                .sentAt(Instant.now())
                .build();

        Message savedMessage = messageRepository.save(message);

        if (attachmentUrls != null && !attachmentUrls.isEmpty()) {
            List<MessageAttachment> attachments = new ArrayList<>();
            for (String url : attachmentUrls) {
                MessageAttachment attachment = MessageAttachment.builder()
                        .message(savedMessage)
                        .fileUrl(url)
                        .build();
                attachments.add(attachment);
            }
            messageAttachmentRepository.saveAll(attachments);
            savedMessage.setAttachments(attachments);
        }

        return savedMessage;
    }

    @Override
    @Transactional
    public void assignSalesManager(Long conversationId, Long salesManagerId) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_FOUND));

        User salesManager = userRepository.findById(salesManagerId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        conversation.setSalesManager(salesManager);
        conversationRepository.save(conversation);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Message> getMessageHistory(Long conversationId, Pageable pageable) {
        if (!conversationRepository.existsById(conversationId)) {
            throw new AppException(ErrorCode.CONVERSATION_NOT_FOUND);
        }
        return messageRepository.findAllByConversationIdOrderBySentAtDesc(conversationId, pageable);
    }
}
