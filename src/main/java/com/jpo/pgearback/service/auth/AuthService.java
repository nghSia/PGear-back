package com.jpo.pgearback.service.auth;

import com.jpo.pgearback.dto.SignupRequest;
import com.jpo.pgearback.dto.UserDTO;

public interface AuthService {
    UserDTO createUser(SignupRequest p_signupRequest);

    Boolean hasUserWithEmail(String p_email);
}
