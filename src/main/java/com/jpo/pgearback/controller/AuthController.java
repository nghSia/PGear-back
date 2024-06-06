package com.jpo.pgearback.controller;

import com.jpo.pgearback.dto.AuthenticationRequest;
import com.jpo.pgearback.dto.SignupRequest;
import com.jpo.pgearback.dto.UserDTO;
import com.jpo.pgearback.service.auth.AuthServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AuthController {

    private final AuthServiceImpl v_authService;
    @Autowired
    public AuthController(AuthServiceImpl v_authService) {
        this.v_authService = v_authService;
    }

    @PostMapping("/login")
    public void createAuthenticationToken(@RequestBody AuthenticationRequest p_authentificationRequest,
                                          HttpServletResponse p_response) throws IOException, JSONException {
        v_authService.createAuthenticationToken(p_authentificationRequest, p_response);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signup(@RequestBody SignupRequest p_signupRequest) {
        if(v_authService.hasUserWithEmail(p_signupRequest.getEmail())){
            return new ResponseEntity<>("user already exist", HttpStatus.NOT_ACCEPTABLE);
        }

        UserDTO v_userDTO = v_authService.createUser(p_signupRequest);
        return new ResponseEntity<>(v_userDTO, HttpStatus.CREATED);
    }
}
