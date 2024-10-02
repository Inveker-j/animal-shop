package uz.pdp.animalshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.animalshop.security.CustomUserDetailsService;
import uz.pdp.animalshop.security.JwtUtil;

@RestController
@RequestMapping("/api/refresh")
@RequiredArgsConstructor
public class RefreshController {
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    @GetMapping
    public String refresh() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        return "Bearer "+jwtUtil.generateToken(userDetails);
    }
}
