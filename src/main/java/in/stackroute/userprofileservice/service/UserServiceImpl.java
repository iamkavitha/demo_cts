package in.stackroute.userprofileservice.service;

import in.stackroute.userprofileservice.exceptions.UserExistsException;
import in.stackroute.userprofileservice.model.User;
import in.stackroute.userprofileservice.model.UserCredentials;
import in.stackroute.userprofileservice.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserProfileRepository repository;

    @Override
    public User registeredUser(User newUser) throws UserExistsException {
        if(repository.existsByEmail(newUser.getEmail())){
            throw  new UserExistsException("User with the email already exists");
        }
        return repository.save(newUser);

    }

    @Override
    public boolean authenticateUser(UserCredentials credentials)  {
        Optional<User> userByEmail = repository.getUserByEmail(credentials.getEmail());
        if(userByEmail.isEmpty()){
            throw new RuntimeException("User not Found");
        }
        User user = userByEmail.get();
        if(user.getPassword().equals(credentials.getPassword())){
            return true;
        }else {
            throw new RuntimeException("Credentials Mismatch");

        }
    }

}
