package in.stackroute.userprofileservice.controller;

import in.stackroute.userprofileservice.exceptions.UserExistsException;
import in.stackroute.userprofileservice.model.User;
import in.stackroute.userprofileservice.model.UserCredentials;
import in.stackroute.userprofileservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
public class UserProfileController {

    @Autowired
    private UserService service;


    @PostMapping("/register")
      public ResponseEntity<User>  registerUser(@RequestBody User newUser) throws UserExistsException {
     return new ResponseEntity<>(service.registeredUser(newUser), HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<String> loginUser(@RequestBody UserCredentials credentials)
    {
        boolean valid = service.authenticateUser(credentials);
        System.out.println(valid);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
