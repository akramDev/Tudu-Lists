package tudu.web.mvc;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import tudu.domain.Property;
import tudu.service.ConfigurationService;
import tudu.service.UserService;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class Level3AdministrationControllerTest {

    @Mock
    private ConfigurationService cfgService;

    @Mock
    private UserService userService;

    @InjectMocks
    private AdministrationController adminController = new AdministrationController();


    /*
    *  Vérifier La réponse par défaut du mock configService et faire un test avec la page configuration
    *  Méthode :  display
    *  Aide : configuaration du mock a l'aide de RETURNS_SMART_NULLS
    */
    @Test
    public void test_defaut_response_of_config_Service(){

        adminController.display("configuration");

    }

    /*
    * Vérifier que le configService.updateEmailProperties est bien appelé en ne vérifiant que les valeurs user et password .
    * Aide : Spy
   * Méthode :  update
    */
    @Test
    public void verify_update_email_properties_is_called(){

        AdministrationModel administrationModel = new AdministrationModel();
        AdministrationModel administrationModelSpy = spy(administrationModel);

        when(administrationModelSpy.getSmtpUser()).thenReturn("Akram");
        when(administrationModelSpy.getAction()).thenReturn("configuration");
        when(cfgService.getProperty(anyString())).thenReturn(property(anyString()));


        adminController.update(administrationModelSpy);

        verify(cfgService).updateEmailProperties(anyString(),anyString(),eq("Akram"),anyString(),anyString());


    }

    private Property property(String value) {
        Property property = new Property();
        property.setValue(value);
        return property;
    }

/*
* Reprendre sur quelques tests ayant des assertEquals, assertNull, assertNotNull avec le framefork fest assert
*/


/*
* Reprendre sur quelques tests ayant des when, verify, doThrow en utilisant la syntaxe bdd mockito
*/

}
