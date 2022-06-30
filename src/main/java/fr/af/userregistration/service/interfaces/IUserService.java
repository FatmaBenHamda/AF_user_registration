package fr.af.userregistration.service.interfaces;

import fr.af.userregistration.entity.User;
import fr.af.userregistration.exception.ResourceNotFoundException;

public interface IUserService {
  public User createUser(User user) throws Exception;

  public User getUserByUserName(String userName) throws ResourceNotFoundException;

}
