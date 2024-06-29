package uz.pdp.animalshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.animalshop.dto.SaveUserDTO;
import uz.pdp.animalshop.dto.UserDto;
import uz.pdp.animalshop.dto.UserDtoImpl;
import uz.pdp.animalshop.entity.Attachment;
import uz.pdp.animalshop.entity.AttachmentContent;
import uz.pdp.animalshop.entity.Role;
import uz.pdp.animalshop.entity.User;
import uz.pdp.animalshop.entity.enums.RoleName;
import uz.pdp.animalshop.security.JwtUtil;
import uz.pdp.animalshop.service.AttachmentContentService;
import uz.pdp.animalshop.service.AttachmentService;
import uz.pdp.animalshop.service.EmailService;
import uz.pdp.animalshop.service.UserService;
import uz.pdp.animalshop.service.interfaces.RoleService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AttachmentService attachmentService;
    private final AttachmentContentService attachmentContentService;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;
    private final RoleService roleService;

    @GetMapping("/settings/get-users")
    public ResponseEntity<?> getUsers() {
        List<User> users = userService.availableUsers();

        List<UserDtoImpl> userDtoList = users.stream()
                .map(user -> {
                    if (user.getAttachment() == null || user.getAttachment().getId() == null) {
                        return null;
                    }

                    Optional<Attachment> optionalAttachment = attachmentService.findById(user.getAttachment().getId());
                    if (optionalAttachment.isPresent()) {
                        Attachment attachment = optionalAttachment.get();

                        Optional<AttachmentContent> optionalAttachmentContent = attachmentContentService.findAttachmentContentByAttachmentId(attachment.getId());
                        if (optionalAttachmentContent.isPresent()) {
                            AttachmentContent attachmentContent = optionalAttachmentContent.get();

                            if (attachmentContent.getContent() != null) {
                                UserDtoImpl userDto = new UserDtoImpl();
                                userDto.setEmail(user.getEmail());
                                userDto.setFirstName(user.getFirstName());
                                userDto.setLastName(user.getLastName());
//                                userDto.setImage(attachmentContent.getContent());
                                return userDto;
                            }
                        }
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return ResponseEntity.ok(userDtoList);
    }

    @PostMapping("/settings/register")
    public ResponseEntity<?> saveUser(@RequestBody SaveUserDTO userDto) {

        if (userDto.getPassword().equals(userDto.getRePassword())) {
            return ResponseEntity.ok().body("Bearer " + jwtUtil.generateRandomAccessToken(userDto, emailService.sendPasswordToEmail(userDto.getEmail())));
        }
        return ResponseEntity.status(IllegalArgumentException.class.getModifiers()).body("You entered invalid password");
    }


    @PutMapping("/settings/edit-user")
    public ResponseEntity<?> editUser(@RequestBody UserDtoImpl userDto
            , @RequestParam(name = "image", required = false) MultipartFile image) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return ResponseEntity.notFound().build();
        }

        String email = authentication.getName();
        User user = userService.findByEmail(email);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        if (image.isEmpty()) {
            user.setEmail(userDto.getEmail());
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
        }

        Attachment attachment = Attachment.builder()
                .size(image.getSize())
                .name(image.getOriginalFilename())
                .build();

        attachmentService.save(attachment);

        try {
            AttachmentContent attachmentContent = AttachmentContent.builder()
                    .attachment(attachment)
                    .content(image.getInputStream().readAllBytes())
                    .contentType(image.getContentType())
                    .build();
            attachmentContentService.save(attachmentContent);


        } catch (IOException e) {
            return ResponseEntity.status(RuntimeException.class.getModifiers()).build();
        }
        return ResponseEntity.ok().build();
    }
}
