package in.stackroute.userprofileservice.service;

import in.stackroute.userprofileservice.exceptions.UserExistsException;
import in.stackroute.userprofileservice.model.User;
import in.stackroute.userprofileservice.model.UserCredentials;

public interface UserService {

    User registeredUser(User newUser)  throws UserExistsException;

    boolean authenticateUser(UserCredentials credentials);
}
