package fr.af.userregistration.controller;

import fr.af.userregistration.dto.UserDTO;
import fr.af.userregistration.entity.User;
import fr.af.userregistration.exception.ResourceNotFoundException;
import fr.af.userregistration.service.interfaces.IUserService;
import fr.af.userregistration.util.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  private IUserService userService;

  public UserController(IUserService userService) {
    this.userService = userService;
  }

  /**
   * This method allows the display user details by their username
   *
   * @param username - Username of an existing user
   * @return user with its infos
   */
  @GetMapping("/byUsername/{username}")
  @ResponseStatus(HttpStatus.OK)
  public UserDTO getUserByUserName(@PathVariable("username") String username) {
    return ObjectMapper.map(userService.getUserByUserName(username), UserDTO.class);
  }

  /**
   * this method allows the registration of a user
   *
   * @param user - User to be registered with valid fields
   * @return Message to indicate that a user is created with success
   */
  @PostMapping()
  @ResponseStatus(HttpStatus.OK)
  public String createUser(@Valid @RequestBody UserDTO user) throws Exception {
    userService.createUser(ObjectMapper.map(user, User.class));
    return "User created with success";
  }

  /**
   * this method allows the interception of Validation Exception and displays non-valid fields with
   * their corresponding error message
   *
   * @param ex - Validation exception
   * @return error field with error message
   */

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return errors;
  }

  /**
   * this method allows the interception of Not Found Exception and displays their corresponding
   * error message
   *
   * @param ex - a resource not found exception
   * @return the message of the exception
   */

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(ResourceNotFoundException.class)
  public String handleNotFoundExceptions(
      ResourceNotFoundException ex) {
    return ex.getMessage();
  }

  /**
   * this method allows the interception of Unknowing Exceptions and displays their corresponding
   * error message
   *
   * @param ex - an exception in general
   * @return the message of the exceptio
   */
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public String handleExceptions(
      Exception ex) {
    return ex.getMessage();
  }
}
