package fr.af.userregistration.exception;

public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException() {
  }
  public ResourceNotFoundException(String message) {
    super(message);
  }
}
