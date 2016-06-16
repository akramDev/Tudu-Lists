package tudu.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import tudu.domain.Todo;
import tudu.domain.TodoList;
import tudu.domain.User;
import tudu.service.UserAlreadyExistsException;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;
import static org.mockito.Mockito.argThat;
import static org.mockito.Mockito.verify;

public class Level3UserServiceImplMockitoTest {

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
    Vérifier que la liste todo a bien pour name Welcome!
    Méthode :  createNewTodoList
    */
    @Test
    public void when_todolist_created_then_the_name_is_welcome(){

        TodoList todoList = userService.createNewTodoList(user);
        assertEquals("Welcome!", todoList.getName());
    }

    /*
    Vérifier que la liste todo a bien pour name Welcome! - autre méthode
    Méthode :  createNewTodoList
    */
    @Test
    public void when_todolist_created_then_the_name_is_welcome_2(){

        userService.createNewTodoList(user);

        ArgumentCaptor<TodoList> todoListCaptor = ArgumentCaptor.forClass(TodoList.class);
        verify(entityManager).persist(todoListCaptor.capture());

        TodoList todoList =  todoListCaptor.getAllValues().get(0);
        assertEquals("Welcome!", todoList.getName());
    }

}
