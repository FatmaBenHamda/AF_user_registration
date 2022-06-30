package fr.af.userregistration.service.implementation;

import fr.af.userregistration.entity.User;
import fr.af.userregistration.exception.ResourceNotFoundException;
import fr.af.userregistration.repository.UserRepository;
import fr.af.userregistration.service.interfaces.IUserService;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
  private UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  private static final String USER_WITH_USERNAME = "User with user name ";

  private static final String NOT_FOUND = " not found";


  /**
   * this method allows the creation of a new user
    * @param user - should be valid
   * @return new created user
   * @throws Exception if error in saving user
   */
  @Override
  public User createUser(User user) throws Exception{
    try {
      User newUser = userRepository.save(user);
      return newUser;
    } catch (Exception e){
      throw e;
    }
  }

  /**
   * this method return user's detail by their username
   * @param userName - user's username
   * @return user's details
   * @throws ResourceNotFoundException - User with the corresponding username doesn't exist
   */
  @Override
  public User getUserByUserName(String userName) throws ResourceNotFoundException {

    Optional<User> user = userRepository.findUserByUserName(userName);
    if(user.isPresent()){
      return user.get();
    }
    throw new ResourceNotFoundException(USER_WITH_USERNAME + userName + NOT_FOUND);
  }
}
