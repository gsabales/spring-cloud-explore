package com.apps.developerblog.app.ws.mobileappws.ui.services;

import com.apps.developerblog.app.ws.mobileappws.ui.model.UserDetails;
import com.apps.developerblog.app.ws.mobileappws.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService{

    Map<String, UserDetails> users;
    private Utils utils;

    // Constructor-based dependency injection
    @Autowired
    public UserServiceImpl(Utils utils) {
        this.utils = utils;
    }

    @Override
    public UserDetails createUserDetails(UserDetails userDetails) {
        UserDetails newUserDetails = new UserDetails();
        newUserDetails.setFirstName(userDetails.getFirstName());
        newUserDetails.setLastName(userDetails.getLastName());
        newUserDetails.setEmail(userDetails.getEmail());
        newUserDetails.setPassword(userDetails.getPassword());

        // Generate a random id to be used as key for the map
        String userId = utils.generateUserId();
        newUserDetails.setUserId(userId);

        if (users == null) { users = new HashMap<>(); }

        // Save the user details in the map with the corresponding id. Saving in-memory map temporarily.
        users.put(userId, newUserDetails);

        return newUserDetails;
    }
}
