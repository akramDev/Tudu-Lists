package tudu.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tudu.domain.User;
import tudu.service.UserAlreadyExistsException;

import javax.persistence.EntityManager;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class Level2UserServiceImplMockitoTest {

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
    * Vérifier que l on récupère bien uneRuntimeException en sortie de updateUser si la methode Merge de l entityManager leve une RuntimeException
    * Méthode : updateUser
    */
    @Test
    public void when_an_exception_is_thrown_by_entityManager_then_rethrow() {

        when(entityManager.merge(user)).thenThrow(new RuntimeException());

        try {
            userService.updateUser(user);
        }catch (Exception e){
            assertTrue(e instanceof RuntimeException);
        }
     }

    /*
    LES METHODES SUIVANTES SONT UNIQUEMENT LA POUR APPRENDRE LA SYNTAXE. CES TESTS SONT EXTREMEMENTS FRAGILES, A MANIPULER AVEC PRECAUTIONS !
     */
    /*
    Type : Test Comportement
    Vérifier que l appel a l'entity manager persist a bien été effectué 4 fois
    Méthode : createUser
    */
    @Test
    public void when_creation_of_a_new_user_then_4_calls_to_entityManager_persist() throws UserAlreadyExistsException {

        userService.createUser(user);

        verify(entityManager, times(4)).persist(Matchers.anyObject());

    }

    @Test
    /*
    Type : Test Comportement
    Vérifier que l appel a l'entity manager persist a bien été effectué au moins 2 fois et au plus 5 fois
    Méthode : createUser
    */

    public void when_creation_of_a_new_user_then_between_2_and_5_calls_to_entityManager_persist() throws UserAlreadyExistsException {

        userService.createUser(user);

        verify(entityManager, atLeast(2)).persist(Matchers.anyObject());
        verify(entityManager, atMost(5)).persist(Matchers.anyObject());

    }

    /*
    Type : Test Comportement
    Vérifier que si l'utilisateur existe, la methode persiste n'a jamais été appelée
    Méthode : createUser
    */
    @Test
    public void when_the_login_already_exist_then_persist_never_called_1() {

        when(entityManager.find(User.class, "test_user")).thenReturn(user);

        try {
            userService.createUser(user);
        }catch (UserAlreadyExistsException e){
            verify(entityManager, never()).persist(Matchers.anyObject());
        } catch (Exception e){
            fail();
        }


    }

    /*
    Type : Test Comportement
    Vérifier que si l'utilisateur existe, la methode persiste n'a jamais été appelée - 2eme alternative avec verifyNoMoreInteractions
    Méthode : createUser
    */

    @Test
    public void when_the_login_already_exist_then_persist_never_called_2() {

        when(entityManager.find(User.class, "test_user")).thenReturn(user);

        try {
            userService.createUser(user);
        }catch (UserAlreadyExistsException e){
            verify(entityManager).persist(Matchers.anyObject());
            verifyNoMoreInteractions(entityManager);
        } catch (Exception e){
            fail();
        }

    }

}