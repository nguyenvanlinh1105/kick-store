package com.kick_api.controller.staff;

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

@RestController
@RequestMapping("/api/v1/staff/chat")
@RequiredArgsConstructor
public class StaffChatController {

    private final ChatService chatService;

    @PostMapping("/assign")
    public ResponseEntity<ApiResponse<Void>> assignSalesManager(
            @RequestParam Long conversationId,
            @RequestParam Long staffId) {
        chatService.assignSalesManager(conversationId, staffId);
        return ResponseEntity.ok(ApiResponse.success("Đã tiếp nhận hỗ trợ cuộc hội thoại này!", null));
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

    @GetMapping("/my-conversations")
    public ResponseEntity<ApiResponse<Page<Conversation>>> getMyConversations(
            @RequestParam Long staffId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Conversation> conversations = chatService.getConversationsByStaff(staffId, pageable);
        return ResponseEntity.ok(ApiResponse.success(conversations));
    }

    @GetMapping("/unassigned")
    public ResponseEntity<ApiResponse<Page<Conversation>>> getUnassigned(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Conversation> conversations = chatService.getUnassignedConversations(pageable);
        return ResponseEntity.ok(ApiResponse.success(conversations));
    }
}
