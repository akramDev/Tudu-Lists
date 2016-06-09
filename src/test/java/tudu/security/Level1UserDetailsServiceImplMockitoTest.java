package tudu.security;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import tudu.domain.Role;
import tudu.domain.RolesEnum;
import tudu.domain.User;
import tudu.service.UserService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class Level1UserDetailsServiceImplMockitoTest {

    @Mock
    UserService userService;
    @InjectMocks
    UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl();

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);

    }

    /*
    * Type : Test état
    * Vérifier que la méthode loadByUsername renvoie le bon login/password/les bonnes autoritées correspondant a l User renvoyé par le mock de userService.findUser
    * Méhode : loadUserByUsername
   */
    @Test
    public void userDetails_should_correspond_to_the_user_found() {
        User user = new User();
        user.setLogin("akram");
        user.setPassword("pass");
        Role role = new Role();
        role.setRole(RolesEnum.ROLE_ADMIN.toString());
        Set<Role> roles = new HashSet<Role>(Arrays.asList(role));
        user.setRoles(roles);
        when(userService.findUser("akram")).thenReturn(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername("akram");

        assertEquals(user.getLogin(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertEquals(roles.size(), userDetails.getAuthorities().size());
        assertEquals(RolesEnum.ROLE_ADMIN.toString(), userDetails.getAuthorities().iterator().next().getAuthority());
    }
}
