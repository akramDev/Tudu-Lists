package tudu.security;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import tudu.domain.User;
import tudu.service.UserService;

import static org.mockito.Mockito.when;

//Niveau2
public class Level2UserDetailsServiceImplMockitoTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl();

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);

    }


    /*
    * Simuler une levée d'exceptions - tester que la methode lève bien une UsernameNotFoundException si la méthode findBy lève une ObjectRetrievalFailureException
    * Méhode : loadUserByUsername

    */
    @Test(expected = UsernameNotFoundException.class)
    public void loadByUsername_throw_UsernameNotFoundException() {

            String login = "akram";
            when(userService.findUser(login)).thenThrow(new ObjectRetrievalFailureException(User.class, login));
            UserDetails userDetails = userDetailsService.loadUserByUsername(login);

    }
}
