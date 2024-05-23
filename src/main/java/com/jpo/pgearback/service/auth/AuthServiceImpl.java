package com.jpo.pgearback.service.auth;

import com.jpo.pgearback.dto.AuthenticationRequest;
import com.jpo.pgearback.dto.SignupRequest;
import com.jpo.pgearback.dto.UserDTO;
import com.jpo.pgearback.entity.User;
import com.jpo.pgearback.enums.UserRole;
import com.jpo.pgearback.repository.UserRepository;
import com.jpo.pgearback.security.JwtUtil;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{

    private final UserRepository v_userRepository;
    private final BCryptPasswordEncoder v_encoder;
    private final AuthenticationManager v_authenticationManager;
    private final UserDetailsService v_userDetailsService;
    private final JwtUtil v_jwtUtil;

    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER_STRING = "Authorization";

    @Autowired
    public AuthServiceImpl(UserRepository p_userRepository, BCryptPasswordEncoder p_encoder,
                           AuthenticationManager p_authmanager,
                           UserDetailsService p_userDetailsService,
                           JwtUtil p_jwtUtil) {
        this.v_userRepository = p_userRepository;
        this.v_encoder = p_encoder;
        this.v_authenticationManager = p_authmanager;
        this.v_userDetailsService = p_userDetailsService;
        this.v_jwtUtil = p_jwtUtil;
    }

    @Override
    public UserDTO createUser(SignupRequest p_signupRequest){
        User user = new User();

        user.setEmail(p_signupRequest.getEmail());
        user.setUsername(p_signupRequest.getUsername());
        user.setPassword(v_encoder.encode(p_signupRequest.getPassword()));
        user.setRole(UserRole.CUSTOMER);
        user.setPoints(0);
        User createdUser = v_userRepository.save(user);

        UserDTO v_userDTO = new UserDTO();
        v_userDTO.setId(createdUser.getId());

        return v_userDTO;
    }

    @Override
    public Boolean hasUserWithEmail(String p_email){
        return v_userRepository.findFirstByEmail(p_email).isPresent();
    }

    //appel automatic apres le constructeur
    @PostConstruct
    public void createAdminAccount(){
        User v_adminUser = v_userRepository.findByRole(UserRole.ADMIN);
        if(v_adminUser == null){
            User v_newUserAdmin = new User();
            v_newUserAdmin.setEmail("admin@admin.com");
            v_newUserAdmin.setUsername("admin");
            v_newUserAdmin.setRole(UserRole.ADMIN);
            v_newUserAdmin.setPassword(v_encoder.encode("admin"));
            v_userRepository.save(v_newUserAdmin);
        }
    }

    public void createAuthenticationToken(AuthenticationRequest p_authentificationRequest ,
                                          HttpServletResponse p_response) throws IOException, JSONException {
        try{
            v_authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(p_authentificationRequest.getEmail(),
                    p_authentificationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        }

        final UserDetails v_userDetails = v_userDetailsService.loadUserByUsername(p_authentificationRequest.getEmail());
        Optional<User> v_optionalUser = v_userRepository.findFirstByEmail(v_userDetails.getUsername());
        final String v_jwt = v_jwtUtil.generateToken(v_userDetails.getUsername());

        if(v_optionalUser.isPresent()) {
            p_response.getWriter().write(new JSONObject()
                    .put("userid", v_optionalUser.get().getId())
                    .put("role", v_optionalUser.get().getRole())
                    .toString()
            );

            p_response.addHeader("Access-Control-Expose-Headers", "Authorization");
            p_response.addHeader("Access-Control-Allow-Headers", "Authorization, X-PINGOTHER, Origin," +
                    "X-Requested-With, Content-Type, Accept, X-Custom-header");
            p_response.addHeader(HEADER_STRING, TOKEN_PREFIX + v_jwt);
        }
    }
}
