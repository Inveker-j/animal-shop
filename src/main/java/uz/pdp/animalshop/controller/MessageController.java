package uz.pdp.animalshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.pdp.animalshop.dto.MessageDTO;
import uz.pdp.animalshop.entity.Message;
import uz.pdp.animalshop.entity.User;
import uz.pdp.animalshop.service.MessageService;
import uz.pdp.animalshop.service.UserService;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final UserService userService;
    private final SimpMessagingTemplate simpMessageTemplate;

    @PostMapping("/chat")
    public ResponseEntity<?> chats(@RequestBody MessageDTO messageDTO) {

        Authentication authentication = getAuthentication();

        if (authentication != null) {
            User from = userService.findByEmail(authentication.getName());

            Optional<User> availableUserById1 = userService.findAvailableUserById(messageDTO.getToId());
            if (availableUserById1.isPresent()) {
                User to = availableUserById1.get();

                Message message = new Message();
                message.setMessage(messageDTO.getMessage());
                message.setFrom(from);
                message.setTo(to);
                messageService.save(message);
                simpMessageTemplate.convertAndSend("/topic/" + to.getId(), message);
                simpMessageTemplate.convertAndSend("/topic/" + from.getId(), message);
                return ResponseEntity.ok(message);
            }
        }

        return ResponseEntity.badRequest().build();
    }

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/get-history")
    public ResponseEntity<?> getHistory(@RequestParam UUID toId) {
        Authentication authentication = getAuthentication();
        if (authentication != null) {
            User from = userService.findByEmail(authentication.getName());
            return ResponseEntity.ok(messageService.getHistory(from.getId(), toId));
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/update-message")
    public ResponseEntity<?> updateMessage(@RequestBody MessageDTO messageDTO) {

        Authentication authentication = getAuthentication();
        if (authentication != null) {
            User from = userService.findByEmail(authentication.getName());

            Optional<Message> optionalMessage = messageService.findById(messageDTO.getMessageId());
            if (optionalMessage.isPresent()) {
                Message message = optionalMessage.get();
                if (message.getFrom().equals(from)) {
                    message.setMessage(messageDTO.getMessage());
                    messageService.save(message);
                }
            }
        }

        return ResponseEntity.badRequest().build();
    }
//
//    @DeleteMapping("/delete-message")
//    public ResponseEntity<?> deleteMessage(@RequestParam UUID messageId) {
//        Authentication authentication = getAuthentication();
//        Optional<Message> optionalMessage = messageService.findById(messageId);
//
//        if (authentication != null && optionalMessage.isPresent()) {
//            User from = userService.findByEmail(authentication.getName());
//            me
//        }
//    }

    @DeleteMapping("/delete-chats")
    public ResponseEntity<?> deleteChats(@RequestParam UUID toId) {

        Optional<User> availableUserById = userService.findAvailableUserById(toId);
        if (availableUserById.isPresent()) {
            User user = availableUserById.get();
            messageService.deleteChat(toId);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
