package com.apps.developerblog.app.ws.mobileappws.ui.services;

import com.apps.developerblog.app.ws.mobileappws.ui.model.UserDetails;
import org.springframework.stereotype.Service;

public interface UserService {
    UserDetails createUserDetails(UserDetails newUserDetails);
}
