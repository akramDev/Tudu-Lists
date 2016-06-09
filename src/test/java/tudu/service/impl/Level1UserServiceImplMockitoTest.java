package tudu.service.impl;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mock.*;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.orm.ObjectRetrievalFailureException;
import tudu.domain.User;
import tudu.service.UserAlreadyExistsException;

import javax.persistence.EntityManager;

import static org.mockito.Mockito.*;
import static junit.framework.Assert.*;

public class Level1UserServiceImplMockitoTest {


    User user = new User();
    @Mock
    EntityManager entityManager;
    @InjectMocks
    UserServiceImpl userService = new UserServiceImpl();

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        user.setLogin("test_user");
        user.setFirstName("First name");
        user.setLastName("Last name");
    }

    /*
    Type : Test état
    Vérifier que l utilisateur retourné par le service correspond bien à celui attendu.
    Méthode : findUser
    Aide : utilisation when pour mocker l appel a entityManager.find et retourner le user en variable de classe
    */
    @Test
    public void find_user_should_return_the_user() {
        //given
        when(entityManager.find(User.class, "test_user")).thenReturn(user);
        //when
        User newUser = userService.findUser("test_user");
        //then
        assertEquals(user, newUser);
    }

    @Test
    /*
    Type : Test Comportement
    Vérifier que l appel a l'entity manager a bien été effectué avec le bon user
    Méthode : updateUser
    Aide : Utilisation de verify
    */
    public void update_user_should_call_entityManager_merge() {
        //given

        //when
         userService.updateUser(user);
        //then
        verify(entityManager, times(1)).merge(user);
    }

    @Test
    /*
    Type : Test état
    Vérifier que l'appel a findUser("toto") entraine bien un appel a entityManager.find avec le meme login.
    Méthode : findUser
    */
    public void user_should_be_retrieved() {
        //given
        when(entityManager.find(User.class, "toto")).thenReturn(user);
        //when
        userService.findUser("toto");
        //then
        verify(entityManager, times(1)).find(User.class, "toto");
    }


    /*
    Vérifier qu'une exception de type ObjectRetrievalFailureException est bien levée si l entityManager find renvoie null
    Méthode : findUser
    */
    @Test
    public void error_should_be_thrown_when_a_user_is_not_found() {
        //given
        when(entityManager.find(User.class, "toto")).thenReturn(null);
        //when
        try{
            userService.findUser("toto");
        }catch (Exception e){
            //then
            assertTrue(e instanceof ObjectRetrievalFailureException);
        }

    }

    /*
    Vérifier qu'une exception de type UserAlreadyExistsException est bien levée si le login existe deja
    Méthode : createUser
    */

    @Test
    public void exception_should_be_thrown_when_creating_an_already_existed_user() throws UserAlreadyExistsException {
        //given
        when(entityManager.find(User.class, "test_user")).thenReturn(user);
        try {
            //when
            userService.createUser(user);
        }catch (Exception e) {
            //then
            assertTrue(e instanceof UserAlreadyExistsException);
        }
    }

    /*
    Type : Test Comportement
    Vérifier que l'utilisateur a bien été sauvegardé
    Méthode : createUser
    */
    @Test
    public void new_user_should_be_saved() throws UserAlreadyExistsException {
        //given

        //when
        userService.createUser(user);
        //then
        verify(entityManager, atLeastOnce()).persist(user);
    }


}
