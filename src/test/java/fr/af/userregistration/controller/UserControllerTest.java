package fr.af.userregistration.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.af.userregistration.dto.UserDTO;
import fr.af.userregistration.entity.User;
import fr.af.userregistration.enumeration.Gender;
import fr.af.userregistration.exception.ResourceNotFoundException;
import fr.af.userregistration.service.interfaces.IUserService;
import java.util.Calendar;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {


  private UserController userController;

  @Mock
  private IUserService userService;

  private MockMvc mockMvc;

  private ObjectMapper objectMapper = new ObjectMapper();

  @Before
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    userController = new UserController(userService);
    mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
  }

  @Test
  public void createUser_OK() throws Exception {
    Mockito.when(userService.createUser(any())).thenReturn(new User());
    UserDTO user = new UserDTO();
    user.setUserName("USER");
    Calendar c = Calendar.getInstance();
    c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 19);
    user.setBirthDate(c.getTime());
    user.setGender(Gender.F);
    user.setResidenceCountry("FR");
    MvcResult mvcResult = mockMvc
        .perform(MockMvcRequestBuilders.post("/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
        .andReturn();
    int status = mvcResult.getResponse().getStatus();
    assertEquals(200, status);
  }

  @Test
  public void createUser_BadRequest() throws Exception {
    Mockito.when(userService.createUser(any())).thenReturn(new User());
    UserDTO user = new UserDTO();

    Calendar c = Calendar.getInstance();
    c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 19);
    user.setBirthDate(c.getTime());
    user.setGender(Gender.F);
    user.setResidenceCountry("FR");
    // case 1: InValid user name

    MvcResult mvcResult = mockMvc
        .perform(MockMvcRequestBuilders.post("/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
        .andReturn();
    int status = mvcResult.getResponse().getStatus();
    assertEquals(400, status);
    user.setUserName("USER");
    // case 2: residencyCountry not FRANCE (FR)
    user.setResidenceCountry("GB");
     mvcResult = mockMvc
        .perform(MockMvcRequestBuilders.post("/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
        .andReturn();
     status = mvcResult.getResponse().getStatus();
    assertEquals(400, status);
    user.setResidenceCountry("FR");

    // case 3: Age below 18
    c.set(Calendar.YEAR, c.get(Calendar.YEAR) + 2);
    user.setBirthDate(c.getTime());
    mvcResult = mockMvc
        .perform(MockMvcRequestBuilders.post("/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
        .andReturn();
    status = mvcResult.getResponse().getStatus();
    assertEquals(400, status);
  }

  @Test
  public void createUser_Exception() throws Exception {
    Mockito.when(userService.createUser(any())).thenThrow(new Exception("new exception"));
    UserDTO user = new UserDTO();
    user.setUserName("USER");
    Calendar c = Calendar.getInstance();
    c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 19);
    user.setBirthDate(c.getTime());
    user.setGender(Gender.F);
    user.setResidenceCountry("FR");

    MvcResult mvcResult = mockMvc
        .perform(MockMvcRequestBuilders.post("/user")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user)))
        .andReturn();
    int status = mvcResult.getResponse().getStatus();
    assertEquals(500, status);
  }

  @Test
  public void getUserByUserName_Ok() throws Exception {
    User user = new User();
    user.setUserName("USER");
    Calendar c = Calendar.getInstance();
    c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 19);
    user.setBirthDate(c.getTime());
    user.setGender(Gender.F);
    user.setResidenceCountry("FR");
    Mockito.when(userService.getUserByUserName(any())).thenReturn(user);
    MvcResult mvcResult = mockMvc
        .perform(MockMvcRequestBuilders.get("/user/byUsername/USER"))
        .andReturn();
    int status = mvcResult.getResponse().getStatus();
    assertEquals(200, status);
  }

  @Test
  public void getUserByUserName_NotFound() throws Exception {
    User user = new User();
    user.setUserName("USER");
    Calendar c = Calendar.getInstance();
    c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 19);
    user.setBirthDate(c.getTime());
    user.setGender(Gender.F);
    user.setResidenceCountry("FR");
    Mockito.when(userService.getUserByUserName(any())).thenThrow(new ResourceNotFoundException("Resource not found"));
    MvcResult mvcResult = mockMvc
        .perform(MockMvcRequestBuilders.get("/user/byUsername/USER"))
        .andReturn();
    int status = mvcResult.getResponse().getStatus();
    assertEquals(404, status);
  }

}
