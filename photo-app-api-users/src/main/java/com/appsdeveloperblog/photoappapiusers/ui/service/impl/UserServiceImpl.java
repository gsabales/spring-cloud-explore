package com.appsdeveloperblog.photoappapiusers.ui.service.impl;

import com.appsdeveloperblog.photoappapiusers.ui.data.UserEntity;
import com.appsdeveloperblog.photoappapiusers.ui.model.UserRequest;
import com.appsdeveloperblog.photoappapiusers.ui.model.UserResponse;
import com.appsdeveloperblog.photoappapiusers.ui.repository.UserRepository;
import com.appsdeveloperblog.photoappapiusers.ui.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder= encoder;
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        UserEntity userEntity = mapToUserEntity(userRequest);
        userRepository.save(userEntity);
        return mapper().map(userEntity, UserResponse.class);
    }

    private UserEntity mapToUserEntity(UserRequest userRequest) {
        UserEntity userEntity = mapper().map(userRequest, UserEntity.class);
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setEncryptedPassword(encoder.encode(userRequest.getPassword()));

        return userEntity;
    }

    private ModelMapper mapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = getUserEntityByEmail(username);

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(),
                true, true, true, true,
                new ArrayList<>());
    }

    @Override
    public UserResponse getUserResponseByEmail(String email) {
        return mapper().map(getUserEntityByEmail(email), UserResponse.class);
    }

    private UserEntity getUserEntityByEmail(String email) {
        try {
            return userRepository.findByEmail(email);
        } catch (UsernameNotFoundException | NullPointerException e) {
            throw new UsernameNotFoundException(email + " not found");
        }
    }
}
