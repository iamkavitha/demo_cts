package in.stackroute.userprofileservice.service;



import in.stackroute.userprofileservice.exceptions.UserExistsException;
import in.stackroute.userprofileservice.model.User;
import in.stackroute.userprofileservice.model.UserCredentials;
import in.stackroute.userprofileservice.repository.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    User userOne;
    UserCredentials credentialsOne;
    @Mock //create a mock object
    private UserProfileRepository repository;

    @InjectMocks
    private UserServiceImpl service;

    @BeforeEach
    void setUp() {
         userOne = new User(20, "Anu", "anu@gmail.com", "1234");
         credentialsOne = new UserCredentials("anu@gmail.com", "1234");
    }

    @Test
    public void givenUserDetailsWhenUserDoesNotExistsThenReturnSavedUser() throws UserExistsException {

///               Configure the behaviour of Mock Object
        when(repository.existsByEmail("anu@gmail.com")).thenReturn(false);
        when(repository.save(any(User.class))).thenReturn(userOne);

        //Call to service method, which will invoke the mock object
        User user = service.registeredUser(userOne);

        assertAll(
                ()->{assertNotNull(user);},
                ()->{ assertTrue(user.getEmail().equals("anu@gmail.com"));},
                ()->{assertTrue(user.getName().equals("Anu"));}
        );

        //verify mock calls are made by service or not
        verify(repository,atLeastOnce()).existsByEmail(anyString());
        verify(repository,times(1)).save(any(User.class));
        verifyNoMoreInteractions(repository);

    }

    @Test
    public void givenUserDetailsWhenExistsThenThrowException() throws UserExistsException {

      when(repository.existsByEmail("anu@gmail.com")).thenReturn(true);

        assertThrows(UserExistsException.class,()->service.registeredUser(userOne));

        verify(repository).existsByEmail("anu@gmail.com");

    }

    @Test
    public void givenUserCredentialsWhenValidThenReturnTrue() throws RuntimeException {

        when(repository.getUserByEmail("anu@gmail.com")).thenReturn(Optional.of(userOne));

        boolean credentials = service.authenticateUser(credentialsOne);
        assertTrue(credentials);

        verify(repository,times(1)).getUserByEmail(anyString());


    }

    @Test
    public void givenUserCredentialsWhenInValidThenThrowException(){
        when(repository.getUserByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,()->service.authenticateUser(credentialsOne));

        verify(repository).getUserByEmail(anyString());

    }
}