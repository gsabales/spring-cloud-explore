package com.apps.developerblog.app.ws.mobileappws.utils;

/**
 * Custom runtime exception
 */
public class UserServiceException extends RuntimeException{

    private static final long serialVersionUID = -909289648693133225L;

    public UserServiceException(String message) {
        super(message);
    }
}
