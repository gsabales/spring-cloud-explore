package com.apps.developerblog.app.ws.mobileappws.ui.controllers;

import com.apps.developerblog.app.ws.mobileappws.ui.model.UpdatedUserDetails;
import com.apps.developerblog.app.ws.mobileappws.ui.model.UserDetails;
import com.apps.developerblog.app.ws.mobileappws.ui.services.UserService;
import com.apps.developerblog.app.ws.mobileappws.ui.services.UserServiceImpl;
import com.apps.developerblog.app.ws.mobileappws.utils.Response;
import com.apps.developerblog.app.ws.mobileappws.utils.UserServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    Map<String, UserDetails> users;

    @GetMapping()
    public ResponseEntity<?> getAllUsers(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false, defaultValue = "desc") String sort
    ) {

        if( page != 0) {
            return ResponseEntity.ok().body("Page: " + page + "\nLimit: " + limit + "\nSort: " + sort);
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.message("error", "Null value on parameter"));
        }
    }

    @GetMapping(path = "{userId}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getUser(@PathVariable String userId) {
        String message;

        if(true) throw new UserServiceException("This is my custom exception");

        try {
            if (users.containsKey(userId)) {
                return ResponseEntity.ok().body(users.get(userId));
            } else {
                message = "User " + userId + " is not found";
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            message = "User list is empty";
        }

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Response.message("error", message));
    }

    @PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserDetails> createUser(@Valid @RequestBody UserDetails userDetails) {

        return ResponseEntity.ok().body(userService.createUserDetails(userDetails));
    }

    @PutMapping(path = "{userId}", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateUser(
            @PathVariable String userId,
            @Valid @RequestBody UpdatedUserDetails updatedUserDetails
    ) {

        if(users == null) {
            return ResponseEntity.noContent().build();
        }

        if(users.containsKey(userId)) {
            UserDetails userDetails = users.get(userId);
            userDetails.setFirstName(updatedUserDetails.getFirstName());
            userDetails.setLastName(updatedUserDetails.getLastName());

            users.put(userId, userDetails);

            return ResponseEntity.ok().body(userDetails);
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Response.message("error", "User " + userId + " not found"));
        }
    }

    @DeleteMapping(path = "{userId}", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {

        if(users.containsKey(userId)) {
            users.remove(userId);
            return ResponseEntity.ok()
                    .body(Response.message("message", "User " + userId + " was deleted"));
        }

        return ResponseEntity.noContent().build();
    }
}
