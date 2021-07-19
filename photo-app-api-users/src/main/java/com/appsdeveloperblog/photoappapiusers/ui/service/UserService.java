package com.appsdeveloperblog.photoappapiusers.ui.service;

import com.appsdeveloperblog.photoappapiusers.ui.model.UserRequest;
import com.appsdeveloperblog.photoappapiusers.ui.model.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService {
    UserResponse createUser(UserRequest userRequest);
    UserResponse getUserResponseByEmail(String email);
}
