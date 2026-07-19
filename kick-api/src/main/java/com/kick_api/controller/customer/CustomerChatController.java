package com.kick_api.controller.customer;

import com.kick_api.dto.ApiResponse;
import com.kick_api.entity.Conversation;
import com.kick_api.entity.Message;
import com.kick_api.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer/chat")
@RequiredArgsConstructor
public class CustomerChatController {

    private final ChatService chatService;

    @PostMapping("/conversation")
    public ResponseEntity<ApiResponse<Conversation>> startConversation(@RequestParam Long customerId) {
        Conversation conversation = chatService.startConversation(customerId);
        return ResponseEntity.ok(ApiResponse.success("Đã mở cuộc trò chuyện mới!", conversation));
    }

    @PostMapping("/message")
    public ResponseEntity<ApiResponse<Message>> sendMessage(
            @RequestParam Long conversationId,
            @RequestParam Long senderId,
            @RequestParam String content,
            @RequestParam(required = false) List<String> attachmentUrls) {
        
        Message message = chatService.sendMessage(conversationId, senderId, content, attachmentUrls);
        return ResponseEntity.ok(ApiResponse.success("Đã gửi tin nhắn!", message));
    }

    @GetMapping("/history/{conversationId}")
    public ResponseEntity<ApiResponse<Page<Message>>> getHistory(
            @PathVariable Long conversationId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("sentAt").descending());
        Page<Message> history = chatService.getMessageHistory(conversationId, pageable);
        return ResponseEntity.ok(ApiResponse.success(history));
    }
}
