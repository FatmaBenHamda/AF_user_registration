package fr.af.userregistration.service;

import static org.junit.Assert.assertEquals;

import fr.af.userregistration.controller.UserController;
import fr.af.userregistration.entity.User;
import fr.af.userregistration.enumeration.Gender;
import fr.af.userregistration.exception.ResourceNotFoundException;
import fr.af.userregistration.repository.UserRepository;
import fr.af.userregistration.service.implementation.UserServiceImpl;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {
  @Mock
  private UserRepository userRepository;
  @InjectMocks
  private UserServiceImpl userService;

  private User user;

  @Before
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    user = new User();
    user.setUserName("USER");
    Calendar c = Calendar.getInstance();
    c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 19);
    user.setBirthDate(c.getTime());
    user.setGender(Gender.F);
    user.setResidenceCountry("FR");
    userService = new UserServiceImpl(userRepository);
  }

  @Test
  public void createUser_OK(){
    Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
    try {
      User result = userService.createUser(user);
      assertEquals(user.getUserName(),result.getUserName());
    } catch (Exception e){
      assert false;
    }
  }


  @Test(expected = Exception.class)
  public void createUser_Exception(){
    Mockito.when(userRepository.save(Mockito.any())).thenThrow(new Exception("new exception"));
    try {
      userService.createUser(user);
      assert false;
    } catch (Exception e){
      assert true;
    }
  }

  @Test
  public void getUserByUserName_OK(){
    Mockito.when(userRepository.findUserByUserName(Mockito.any())).thenReturn(Optional.of(user));
    User result = userService.getUserByUserName("USER");
    assertEquals(user.getUserName(),result.getUserName());
  }

  @Test(expected = ResourceNotFoundException.class)
  public void getUserByUserName_NOT_FOUND(){
    Mockito.when(userRepository.findUserByUserName(Mockito.any())).thenReturn(Optional.empty());
    User result = userService.getUserByUserName("USER");
  }

}
