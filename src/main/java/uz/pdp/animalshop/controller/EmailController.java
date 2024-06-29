package uz.pdp.animalshop.controller;


import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import uz.pdp.animalshop.dto.UserDtoImpl;
import uz.pdp.animalshop.entity.Role;
import uz.pdp.animalshop.entity.User;
import uz.pdp.animalshop.security.JwtUtil;
import uz.pdp.animalshop.service.EmailService;
import uz.pdp.animalshop.service.UserService;
import uz.pdp.animalshop.service.interfaces.RoleService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;
    //    private final Map<String, String> passwordMap = new HashMap<>();
    private final Map<String, Integer> loginAttemptsMap = new HashMap<>();
    private final Integer MAX_LOGIN_ATTEMPTS = 5;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final RoleService roleService;

    @Async
    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailRequest emailRequest) throws MessagingException {
        String email = emailRequest.getEmail();
        String sendPassword = emailService.sendPasswordToEmail(email);
//        passwordMap.put(email, sendPassword);
        loginAttemptsMap.put(email, 0);
//        passwordMap.forEach((key, value) -> System.out.println("key: " + key + " value: " + value));

        return sendPassword;
    }

    @Async
    @PostMapping("/check-password")
    public ResponseEntity<?> checkPassword(
            @RequestParam String code,
            @RequestHeader(name = "Authorization", required = false) String token) {

        if (token != null) {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            System.out.println("token token keldi brat= " + token);
            if (jwtUtil.isValid(token)) {
                String email = jwtUtil.getEmail(token);
                String randomCode = jwtUtil.getClaims(token).get("randomNumber", String.class);

//                    String storedPassword = passwordMap.get(email);
//                Integer attempts = loginAttemptsMap.get(email);
//                System.out.println("attempts = " + attempts);

//                if (attempts >= MAX_LOGIN_ATTEMPTS) {
////                        passwordMap.remove(email);
//                    return ResponseEntity.badRequest().body("Resend password");
//                }

//                    System.out.println("storedPassword = " + email + "  /check-password " + storedPassword);
                if (randomCode != null && randomCode.equals(code)) {
//                    loginAttemptsMap.remove(email);

                    String firstname = jwtUtil.getClaims(token).get("firstName", String.class);
                    String lastname = jwtUtil.getClaims(token).get("lastName", String.class);
                    String password = jwtUtil.getClaims(token).get("password", String.class);
                    String rolesString =  jwtUtil.getClaims(token).get("roles", String.class);

//                    List<Role> roles = Arrays.stream(rolesString.split(","))
//                            .map(Role::new)
//                            .collect(Collectors.toList());

                    List<Role> roles = Arrays.stream(rolesString.split(","))
                            .map(roleService::findRoleByName)
                            .toList();

                    System.out.println("roles = " + roles);


                    User user = User.builder()
                            .email(email)
                            .password(password)
                            .firstName(firstname)
                            .lastName(lastname)
                            .build();

                    userService.save(user);

                    UserDtoImpl dto = new UserDtoImpl();
                    dto.setEmail(email);
                    dto.setFirstName(user.getFirstName());
                    dto.setLastName(user.getLastName());

                    return ResponseEntity.ok().body(dto);
                }
//                attempts++;
//                loginAttemptsMap.put(email1, attempts);
                return ResponseEntity.internalServerError().body("Invalid password");


            } else {
                System.out.println("Not isValid()");
            }
        }
        return ResponseEntity.badRequest().body("Invalid token");

    }

//        if (token != null) {
//            String[] tokens = token.split(",");
//            for (String temp : tokens) {
//                if (temp.startsWith("Bearer ")) {
//                    temp = temp.substring(7);
//                }
//
//                if (!jwtUtil.isValid(temp)) {
//                    String email1 = jwtUtil.getEmail(token);
//                    String randomCode = jwtUtil.getClaims(temp).get("randomNumber", String.class);
//
////                    String storedPassword = passwordMap.get(email);
//                    Integer attempts = loginAttemptsMap.get(email1);
//                    System.out.println("attempts = " + attempts);
//
//                    if (attempts >= MAX_LOGIN_ATTEMPTS) {
////                        passwordMap.remove(email);
//                        return ResponseEntity.badRequest().body("Resend password");
//                    }
//
////                    System.out.println("storedPassword = " + email + "  /check-password " + storedPassword);
//                    if (randomCode != null && randomCode.equals(code)) {
//                        loginAttemptsMap.remove(email1);
//
//                        String firstname = jwtUtil.getClaims(temp).get("firstname", String.class);
//                        String lastname = jwtUtil.getClaims(temp).get("lastname", String.class);
//                        String password = jwtUtil.getClaims(temp).get("password", String.class);
//
//                        User user = User.builder()
//                                .email(email1)
//                                .password(password)
//                                .firstName(firstname)
//                                .lastName(lastname)
//                                .build();
//
//                        userService.save(user);
//
//                        return ResponseEntity.ok().build();
//                    }
//                        attempts++;
//                        loginAttemptsMap.put(email1, attempts);
//                        System.out.println("password = " + code);
//                        return ResponseEntity.internalServerError().body("Invalid password");
//
//
//
//                } else {
//                    System.out.println("Not isValid()");
//                }
//            }
//
//        }
//        String storedPassword = passwordMap.get(email);
//        Integer attempts = loginAttemptsMap.get(email);
//        System.out.println("attempts = " + attempts);
//
//        if (attempts >= MAX_LOGIN_ATTEMPTS) {
//            passwordMap.remove(email);
//            return ResponseEntity.badRequest().body("Resend password");
//        }
//
//        System.out.println("storedPassword = " + email + "  /check-password " + storedPassword);
//        if (storedPassword != null && storedPassword.equals(password)) {
//            passwordMap.remove(email);
//            loginAttemptsMap.remove(email);
//            return ResponseEntity.ok().body(storedPassword);
//        } else {
//            attempts++;
//            loginAttemptsMap.put(email, attempts);
//            System.out.println("password = " + password);
//            return ResponseEntity.internalServerError().body("Invalid password");
//        }

}
