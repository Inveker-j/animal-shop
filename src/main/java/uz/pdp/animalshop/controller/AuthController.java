package uz.pdp.animalshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import uz.pdp.animalshop.dto.LoginDTO;
import uz.pdp.animalshop.dto.TokenDTO;
import uz.pdp.animalshop.entity.User;
import uz.pdp.animalshop.security.JwtUtil;
import uz.pdp.animalshop.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping()
    public TokenDTO login(@RequestBody LoginDTO testDto) {
        var auth = new UsernamePasswordAuthenticationToken(testDto.getEmail(), testDto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(auth);
        UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
        return new TokenDTO(
                "Bearer " + jwtUtil.generateToken(userDetails),
                "Bearer " + jwtUtil.generateRefreshToken(userDetails)
        );
    }

    @GetMapping()
    public User getMe() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findByEmail(email);
    }
}
